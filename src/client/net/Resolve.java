package client.net;

import java.lang.reflect.InvocationTargetException;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import client.data.Data;
import client.manager.IOManager;
import client.manager.ListManager;
import client.manager.MessageManager;
import client.ui.GameFrame;
import client.ui.ChessBoardCanvas;



public class Resolve {
	/**
	 * 解析收到的数据
	 */

	String readLine;

	//	登陆初始化
	public void init(String _readLine) {

		readLine = _readLine;

		String s[] = readLine.split("-");
		String name = s[1];

		int id = Integer.parseInt(s[0]);

		Data.myId = id;
		Data.myName = name;

//		更新列表
		IOManager.getInstance().getPs().println("LIST:");
	}


	//	游戏开始命令
	public void start(String _readLine){

		readLine = _readLine;

		if(readLine.substring(0,5).equals("BLACK")){

			Data.myChess = Data.BLACK;
			Data.oppoChess = Data.WHITE;
			Data.turn = Data.BLACK;
			Data.started = true;

		}
		else{
			if(readLine.substring(0,5).equals("WHITE")){

				Data.myChess = Data.WHITE;
				Data.oppoChess = Data.BLACK;
				Data.turn = Data.BLACK;
				Data.started = true;

			}
		}
	}

	//	游戏开始消息
	public void startMessage(String _readLine) {

		readLine = _readLine;

		MessageManager.getInstance().addMessage(readLine);
	}


	//	落子
	public void play(String _readLine){

		readLine = _readLine;

		int position = Integer.parseInt(readLine);
		int chessY = position/15;
		int chessX = position - chessY * 15;

		new PlayChess().play(chessX, chessY, Data.oppoChess);

	}

	//	聊天
	public void chat(String _readLine){

		readLine = _readLine;

		String s[] = readLine.split("&");
		String message = s[0];
		String who = s[1];

		MessageManager.getInstance().addMessage(who + "说：" + message);
	}

	//	操作
	public void operation(String _readLine){

		readLine = _readLine;

//		悔棋操作
		if(readLine.substring(0,5).equals("BACK:")){

			Data.wait = false;

			if(readLine.substring(5).substring(0,4).equals("ONE:")){

				int position = Integer.parseInt(readLine.substring(9));

				int y = position / 15;
				int x = position - 15 * y;

//				更改棋权
				if(Data.turn == Data.myChess){

					Data.turn = Data.oppoChess;
					MessageManager.getInstance().addMessage("等待对方落棋。。");

				}
				else{
					if(Data.turn == Data.oppoChess){

						Data.turn = Data.myChess;

						MessageManager.getInstance().addMessage("请您落棋。。");
					}
				}

//				更新棋盘
				Data.chessBoard[x][y] = 0;

				ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
				mapCanvas.paintMapImage();
				mapCanvas.repaint();
			}
			else{
				String str = readLine.substring(9);
				String [] s = str.split("&");

				int position1 = Integer.parseInt(s[0]);
				int position2 = Integer.parseInt(s[1]);

				int y1 = position1 / 15;
				int y2 = position2 / 15;
				int x1 = position1 - y1 * 15;
				int x2 = position2 - y2 * 15;

				Data.chessBoard[x1][y1] = 0;
				Data.chessBoard[x2][y2] = 0;

				ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
				mapCanvas.paintMapImage();
				mapCanvas.repaint();
			}
		}

//		对方逃跑
		if(readLine.substring(0,5).equals("ESCA:")){
			Data.oppoId = 0;
			Data.started = false;
			Data.ready = false;
			Data.chessBoard = new int[15][15];
			Data.last = -1;
			Data.myChess = 0;
			Data.oppoChess = 0;

			ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
			mapCanvas.paintMapImage();
			mapCanvas.repaint();

			JOptionPane.showMessageDialog(null, "对方逃跑了。。。。");
		}

//		对方离开
		if(readLine.substring(0,5).equals("QUIT:")){
			Data.oppoId = 0;
			Data.started = false;
			Data.ready = false;
			Data.chessBoard = new int[15][15];
			Data.last = -1;
			Data.myChess = 0;
			Data.oppoChess = 0;

			ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
			mapCanvas.paintMapImage();
			mapCanvas.repaint();

			MessageManager.getInstance().addMessage("对方离开了,你可以重新选择对手！");
		}

//		收到挑战
		if(readLine.substring(0, 5).equals("CHAL:")){

			readLine = readLine.substring(5);

			String [] s = readLine.split("-");
			int from = Integer.parseInt(s[0]);

			int value = JOptionPane.showConfirmDialog(null,"玩家“" + readLine +
					"”挑逗你，是否接招？" ,"收到挑战",JOptionPane.YES_NO_OPTION);

//			接受挑战
			if(value == JOptionPane.YES_OPTION){

				IOManager.getInstance().getPs().println("REPL:CHAL:" + from + "&YES");

				Data.oppoId = from;

				GameFrame.getInstance().getFunctionPanel().getPlayerPanel().getOpponentInfo().setText("目前对手：" + readLine);

				JOptionPane.showMessageDialog(null, "您接受了对方的挑战，请按“开始”按钮开始游戏！");

				MessageManager.getInstance().addMessage("您接受了对方的挑战，请按“开始”按钮开始游戏。");

			}

//			拒绝挑战
			else{
				IOManager.getInstance().getPs().println("REPL:CHAL:" + from + "&NO");

				JOptionPane.showMessageDialog(null, "你真怂！");

				MessageManager.getInstance().addMessage("怂B。。。");
			}
		}

	}


	//	回复
	public void reply(String _readLine){

		readLine = _readLine;

//		悔棋回复

		if(readLine.substring(0,5).equals("BACK:")){

			MessageManager.getInstance().addMessage("对方拒绝悔棋！");

			Data.wait = false;
		}
//		挑战回复
		if(readLine.substring(0, 5).equals("CHAL:")){

			readLine = readLine.substring(5);

			String s [] = readLine.split("&");
			String challenged = s[0];
			String result = s[1];

//			对方接受挑战
			if(result.equals("YES")){

				String s2 [] = challenged.split("-");
				int uid = Integer.parseInt(s2[0]);

				JOptionPane.showMessageDialog(null, "玩家“" + challenged + "”接受了您的挑战，请按“开始”按钮开始游戏！");


//				设置对手id
				Data.oppoId = uid;

//				设置对手状态栏信息
				GameFrame.getInstance().getFunctionPanel().getPlayerPanel().getOpponentInfo().setText("目前对手：" + challenged);

				MessageManager.getInstance().addMessage("对方接受了您的挑战，请按“开始”按钮开始游戏。");
			}

//			对方拒绝挑战
			if(result.equals("NO")){

				JOptionPane.showMessageDialog(null, "玩家“" + challenged + "”怂了。。。不敢接受挑战。。。");
			}
		}

	}

	//	添加玩家
	public void addList(String _readLine) {

		readLine = _readLine;

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					ListManager.getInstance().addPlayer(readLine);
				}
			});
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		}
	}

	//	删除玩家
	public void delList(String _readLine) {

		readLine = _readLine;

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					ListManager.getInstance().removePlayer(readLine);
				}
			});
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		}
	}

	//	更新列表
	public void updateList(String _readLine) {

		readLine = _readLine;

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					ListManager.getInstance().clearList();
					String [] s = readLine.split("&");
					for(int i = 0 ; i < s.length; i ++){

						ListManager.getInstance().addPlayer(s[i]);
					}
				}
			});
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		}
	}

	//	修改自己昵称
	public void changeName(String _readLine) {

		readLine = _readLine;

		String[] str = readLine.split("-");
		int myId = Integer.parseInt(str[0]);

		Data.myId = myId;
		Data.myName = str[1];

		MessageManager.getInstance().addMessage("昵称修改成功！");

	}

	//	更新别人昵称
	public void updateName(String _readLine) {

		readLine = _readLine;

		DefaultListModel model = (DefaultListModel) ListManager.getInstance().getListModel();

		String s1;
		String s2;
		String[] str1;
		String[] str2;

		s2 = readLine;
		str2 = s2.split("-");

		for(int i = 0; i < model.size(); i++){

			s1 = (String) model.elementAt(i);
			str1 = s1.split("-");

			if (str1[0].equals(str2[0])){

				model.removeElementAt(i);
				ListManager.getInstance().addPlayer(readLine);

			}
		}
	}

	//	胜利
	public void win() {
		JOptionPane.showMessageDialog(null, "牛逼啊，你赢啦！");
		Data.turn = 0;
		Data.started = false;
	}

	//	失败
	public void lose() {
		JOptionPane.showMessageDialog(null, "傻逼了吧！输了吧！");
		Data.turn = 0;
		Data.started = false;
	}



	//	收到悔棋请求
	public void askFor(String _readLine) {

		readLine = _readLine;

		if(readLine.substring(0, 5).equals("BACK:")){

			int value = JOptionPane.showConfirmDialog(null, "对方请求悔棋，是否同意？","对手请求悔棋" ,JOptionPane.YES_NO_OPTION );

			if(value == JOptionPane.YES_OPTION){
				IOManager.getInstance().getPs().println("REPL:BACK:YES");

			}
			else{
				IOManager.getInstance().getPs().println("REPL:BACK:NO");

			}
		}

	}


}
