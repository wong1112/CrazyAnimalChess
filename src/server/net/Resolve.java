package server.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;



public class Resolve {
	/**
	 *解析收到的数据
	 */

	int uid;
	Socket s;
	OutputStream os;
	String readLine;

	public void resolve(int _uid,Socket _s ,String _readLine) {
		this.s = _s;
		this.uid = _uid;
		this.readLine = _readLine;
		try {
			os = s.getOutputStream();
			PrintStream ps = new PrintStream(os);
//			更新列表
			if(readLine.substring(0, 5).equals("LIST:")){
				new Action().getList(s);
			}
//			回复
			if(readLine.substring(0, 5).equals("REPL:")){

				String str = readLine.substring(5);

//				回复挑战
				if(str.substring(0, 5).equals("CHAL:")){

					new Action().replyChallenge(uid ,str.substring(5));

				}
//				回复悔棋请求
				if(str.substring(0, 5).equals("BACK:")){

					new Action().replyBack(uid ,str.substring(5));

				}
			}
//			落子
			if(readLine.substring(0, 5).equals("PLAY:")){
				String str = readLine.substring(5);
				int position = Integer.parseInt(str);
				new Action().playChess(uid,position);
			}
//			聊天
			if(readLine.substring(0, 5).equals("CHAT:")){
				new Action().sendMessage(uid ,readLine);
			}
//			操作
			if(readLine.substring(0, 5).equals("OPER:")){
				String str = readLine.substring(5);
//				挑战玩家
				if(str.substring(0, 5).equals("CHAL:")){
					str = str.substring(5);
					int target = Integer.parseInt(str);
					new Action().sendChallenge(uid,target);
					HashMapManager.getInstance().getMatching().put(uid, target);
				}
//				开始准备
				if(str.substring(0, 5).equals("STAR:")){
					new Action().ready(uid,str.substring(5));
				}
//				重新开始
				if(str.substring(0, 5).equals("REST:")){
					new Action().restart(uid);
				}
//				退出配对
				if(str.substring(0, 5).equals("QUIT:")){
					int oppoId = Integer.parseInt(str.substring(5));
					new Action().quit(uid,oppoId);
				}
//				悔棋
				if(str.substring(0, 5).equals("BACK:")){
					int from = Integer.parseInt(str.substring(5));
					new Action().askForBack(uid,from);
				}
//				对方逃跑
				if(str.substring(0, 5).equals("ESCA:")){
					int oppoId = Integer.parseInt(str.substring(5));

					new Action().esca(uid,oppoId);
				}
			}
//			初始化信息
			if(readLine.substring(0, 5).equals("INIT:")){
				HashMapManager.getInstance().getPlayer(uid).setName(readLine.substring(5));
				new Action().newClient(uid);
				ps.println("INIT:" + uid + "-" + readLine.substring(5));
			}
//			修改昵称
			if(readLine.substring(0, 5).equals("NAME:")){
				new Action().changeName(ps,uid,readLine);
			}
		} catch (IOException e) {
			MessageManager.getInstance().addMessage("解析时获取输出流出错！");
			e.printStackTrace();
		}

	}

}
