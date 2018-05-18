package server.tool;

import java.io.IOException;
import java.io.PrintStream;

import server.tool.check.Check;

public class FightManager {

	/**
	 * 存储对战信息
	 * 收发两个客户端的对战消息
	 * 两个客户端共享一个FightManager
	 *
	 */
	boolean restartA = false;
	boolean restartB = false;

	int turn = 0;

	int BLACK = 1;
	int WHITE = -1;

	int playerA;			//黑棋
	int playerB;			//白棋

	PrintStream psA = null;
	PrintStream psB = null;

	int lastWhite = -1;		//处理悔棋
	int lastBlack = -1;

	int [][] chessBoard = new int[15][15];

	//	获取playerA的打印流
	public PrintStream getPsA() {

		if(psA == null){

			try {

				psA = new PrintStream(HashMapManager.getInstance().getPlayer(playerA).socket.getOutputStream());

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return psA;
	}

	//	获取playerB的打印流
	public PrintStream getPsB() {

		if(psB == null){

			try {

				psB = new PrintStream(HashMapManager.getInstance().getPlayer(playerB).socket.getOutputStream());

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return psB;
	}

	//	设置玩家A的Id
	public void setPlayerA(int playerA) {

		this.playerA = playerA;
	}

	//	设置玩家B的Id
	public void setPlayerB(int playerB) {

		this.playerB = playerB;
	}

	//	发送开始信息
	public void sendStartMessage() {

		this.getPsA().println("SYST:游戏开始，请落子。。。");
		this.getPsB().println("SYST:游戏开始，等待对方落子。。。");

		turn = BLACK;
	}

	//	开始对弈
	public void startPlay(int playerA,int playerB) {

		this.setPlayerA(playerA);

		this.setPlayerB(playerB);

		this.getPsA().println("STAR:BLACK");
		this.getPsB().println("STAR:WHITE");
	}

	//	发送落子信息
	public void sendPlay(int from,int position) {

		if(from == playerA){

			int y = position / 15;
			int x = position - 15 * y;

			chessBoard [x][y] = BLACK;

			this.getPsB().println("PLAY:" + position);

			lastBlack = position;

			turn = WHITE;

//			检查是否胜利
			if(this.checkWin(x, y, BLACK)){

				this.getPsA().println("UWIN:");
				this.getPsB().println("LOSE:");
			}

		}
		else{
			if(from == playerB){

				int y = position / 15;
				int x = position - 15 * y;

				chessBoard [x][y] = WHITE;

				this.getPsA().println("PLAY:" + position);

				lastWhite = position;

				turn = BLACK;

//				检查是否胜利
				if(this.checkWin(x, y, WHITE)){

					this.getPsB().println("UWIN:");
					this.getPsA().println("LOSE:");
				}

			}

			else{

				MessageManager.getInstance().addMessage("发送落子信息出错！"+ "PLAY:" + position);

				System.out.println("来源id：" + from);
				System.out.println("playerAid:" + playerA);
				System.out.println("playerBid:" + playerB);
			}
		}
	}

	//	检查是否胜利
	public boolean checkWin(int x ,int y ,int id){

		boolean isWin = new Check().check(x,y,id,chessBoard);
		return isWin;

	}
	//	重新开始
	public void restart(int uid) {

		if(uid == playerA){

			if(restartA == false){

				restartA = true;
			}
		}
		else{
			if(restartB == false){
				restartB = true;
			}
		}

		if(restartA && restartB){

			psA = null;
			psB = null;

			chessBoard = new int[15][15];

			lastBlack = -1;
			lastWhite = -1;

			this.startPlay(playerB,playerA);
			this.sendStartMessage();

			restartA = false;
			restartB = false;
		}
	}

	//	悔棋
	public void back(int uid){

//		获取对手id
		int from = (uid == playerA)? playerB:playerA;

//		判断谁发起的请求
		if(from == playerA){

			if(turn == BLACK){

				this.getPsA().println("OPER:BACK:TWO:" + lastBlack + "&" + lastWhite);
				this.getPsB().println("OPER:BACK:TWO:" + lastBlack + "&" + lastWhite);

				int y1 = lastBlack/15;
				int x1 = lastBlack - y1*15;
				this.chessBoard[x1][y1] = 0;

				int y2 = lastWhite/15;
				int x2 = lastWhite - y2*15;
				this.chessBoard[x2][y2] = 0;

			}
			else{

				this.getPsA().println("OPER:BACK:ONE:" + lastBlack);
				this.getPsB().println("OPER:BACK:ONE:" + lastBlack);

				int y = lastBlack/15;
				int x = lastBlack - y*15;
				this.chessBoard[x][y] = 0;

				turn = BLACK;
			}
		}
		else{
			if(turn == BLACK){

				this.getPsA().println("OPER:BACK:ONE:" + lastWhite);
				this.getPsB().println("OPER:BACK:ONE:" + lastWhite);

				int y = lastBlack/15;
				int x = lastBlack - y*15;
				this.chessBoard[x][y] = 0;

				turn = WHITE;
			}
			else{

				this.getPsB().println("OPER:BACK:TWO:" + lastBlack + "&" + lastWhite);
				this.getPsA().println("OPER:BACK:TWO:" + lastBlack + "&" + lastWhite);

				int y1 = lastBlack/15;
				int x1 = lastBlack - y1*15;
				this.chessBoard[x1][y1] = 0;

				int y2 = lastWhite/15;
				int x2 = lastWhite - y2*15;
				this.chessBoard[x2][y2] = 0;

			}
		}
	}

	public void askForBack(int from) {
		if(from == playerA){
			this.getPsB().println("ASK4:BACK:");
		}
		else{
			this.getPsA().println("ASK4:BACK:");
		}

	}

	public void refuseBack(int from) {

		if(from == playerA){

			this.getPsB().println("REPL:BACK:NO");

		}
		else{
			this.getPsA().println("REPL:BACK:NO");
		}

	}
}