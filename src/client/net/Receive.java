package client.net;

import java.io.BufferedReader;
import java.io.IOException;

import client.data.Data;
import client.manager.IOManager;
import client.manager.ListManager;
import client.manager.MessageManager;

public class Receive implements Runnable{

	/**
	 * 接受数据线程
	 */

	@Override
	public void run() {

		BufferedReader br = IOManager.getInstance().getBr();

		String readLine;

		while(Data.connected){
			try {

				readLine = br.readLine();

//				初始化ID信息
				if(readLine.substring(0, 5).equals("INIT:")){
					new Resolve().init(readLine.substring(5));
				}

//				游戏开始消息
				if(readLine.substring(0, 5).equals("SYST:")){
					new Resolve().startMessage(readLine.substring(5));
				}

//				修改昵称
				if(readLine.substring(0, 5).equals("NAME:")){
					new Resolve().changeName(readLine.substring(5));
				}

//				更新姓名
				if(readLine.substring(0, 5).equals("UPNA:")){
					new Resolve().updateName(readLine.substring(5));
				}

//				更新列表
				if(readLine.substring(0, 5).equals("LIST:")){

					new Resolve().updateList(readLine.substring(5));

				}

//				添加玩家
				if(readLine.substring(0, 5).equals("ADDL:")){
					new Resolve().addList(readLine.substring(5));

				}

//				删除玩家
				if(readLine.substring(0, 5).equals("DELE:")){
					new Resolve().delList(readLine.substring(5));
				}


//				开始
				if(readLine.substring(0, 5).equals("STAR:")){
					new Resolve().start(readLine.substring(5));
				}

//				落子
				if(readLine.substring(0, 5).equalsIgnoreCase("PLAY:")){
					new Resolve().play(readLine.substring(5));
				}

//				聊天
				if(readLine.substring(0, 5).equals("CHAT:")){
					new Resolve().chat(readLine.substring(5));
				}

//				操作
				if(readLine.substring(0, 5).equals("OPER:")){
					new Resolve().operation(readLine.substring(5));
				}

//				回执
				if(readLine.substring(0, 5).equals("REPL:")){
					new Resolve().reply(readLine.substring(5));
				}

//				胜利
				if(readLine.substring(0, 5).equals("UWIN:")){
					new Resolve().win();
				}
//				失败
				if(readLine.substring(0, 5).equals("LOSE:")){
					new Resolve().lose();
				}
//				请求
				if(readLine.substring(0, 5).equals("ASK4:")){
					new Resolve().askFor(readLine.substring(5));
				}
			} catch (IOException e) {

				Data.connected = false;
				Data.chessBoard = new int[15][15];
				Data.last = -1;
				Data.myChess = 0;
				Data.myId = 0;
				Data.myName = null;
				Data.oppoChess = 0;
				Data.oppoId = 0;
				Data.ready = false;
				Data.started = false;
				Data.turn = -1;

				ListManager.getInstance().clearList();
				MessageManager.getInstance().addMessage("与服务器连接中断！");

			}
		}
	}

}
