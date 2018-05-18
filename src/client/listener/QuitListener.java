package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.data.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;
import client.ui.ChessBoardCanvas;


public class QuitListener implements ActionListener{
	/**
	 * 监听退出操作
	 * */

	@Override
	public void actionPerformed(ActionEvent e) {

		if(Data.oppoId != 0){

			if(Data.started){

				int value = JOptionPane.showConfirmDialog(null,"游戏还没结束，逃跑就是认输，你确定要逃跑？" ,"逃跑",JOptionPane.YES_NO_OPTION);

				if(value == JOptionPane.YES_OPTION){

					JOptionPane.showMessageDialog(null, "你无耻地跑掉了！");

					IOManager.getInstance().getPs().println("OPER:" + "ESCA:" + Data.oppoId);

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

					MessageManager.getInstance().addMessage("你可以重新选择对手了！");

				}



			}

			else{
				IOManager.getInstance().getPs().println("OPER:" + "QUIT:" + Data.oppoId);

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

				MessageManager.getInstance().addMessage("你可以重新选择对手了！");
			}

		}
	}

}
