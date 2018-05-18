package client.net;

import java.io.PrintStream;

import client.data.*;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;
import client.ui.ChessBoardCanvas;

public class PlayChess {
	/**
	 * 落棋
	 */

	public void play(int chessX, int chessY , int chess){

		PrintStream ps = IOManager.getInstance().getPs();

		int position;

		position = 15 * chessY + chessX;

		Data.last = position;		//标记最后一个棋子位置

		if(chess == Data.myChess){

			Data.chessBoard[chessX][ chessY] = Data.myChess;

			ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
			mapCanvas.paintMapImage();
			mapCanvas.repaint();

			Data.turn = Data.oppoChess;

			ps.println("PLAY:" + position);

			MessageManager.getInstance().addMessage("等待对方落棋。。");

		}
		if(chess == Data.oppoChess){

			Data.chessBoard[chessX][ chessY] = Data.oppoChess;

			ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
			mapCanvas.paintMapImage();
			mapCanvas.repaint();

			Data.turn = Data.myChess;

			MessageManager.getInstance().addMessage("请您落棋。。");

		}
	}


}
