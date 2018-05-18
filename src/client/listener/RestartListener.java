package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.data.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;
import client.ui.ChessBoardCanvas;

public class RestartListener implements ActionListener{
	/**
	 * 监听重来按钮
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(Data.connected){
			if(!Data.started){
				Data.chessBoard = new int [15][15];
				Data.turn = 0;
				Data.last = -1;


//				交换棋子
				Data.myChess = 0;
				Data.oppoChess = 0;

				ChessBoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getMapCanvas();
				mapCanvas.paintMapImage();
				mapCanvas.repaint();

				IOManager.getInstance().getPs().println("OPER:REST:");
			}
			else{
				MessageManager.getInstance().addMessage("游戏还没结束！");
			}
		}
		else{
			MessageManager.getInstance().addMessage("先登陆吧！");
		}
	}

}
