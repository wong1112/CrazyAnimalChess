package client.listener;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.data.Data;
import client.manager.MessageManager;
import client.net.PlayChess;
import client.ui.ChessBoardCanvas;


public class MapListener extends MouseAdapter {
	/**
	 * 监听棋盘
	 */

	@Override
	public void mousePressed(MouseEvent e) {

		ChessBoardCanvas canvas = (ChessBoardCanvas)e.getSource();

//		判断是否登陆
		if(Data.connected){
//			判断是否选择了对手
			if(Data.oppoId != 0){
				if(Data.ready == true){
//					判断是否已经开始
					if(Data.started){
						if(!Data.wait){
//							判断是否轮到自己落棋
							if(Data.turn == Data.myChess){
//								判断是否在棋盘范围内
								if(e.getX() < canvas.getMapWidth() - 6 && e.getY() < canvas.getHeight() - 7 ){

									int chessX;
									int chessY;

									chessX = e.getX() / 35;
									chessY = e.getY()/ 35;

//									判断此处是否已有棋子
									if(Data.chessBoard[chessX][chessY] == 0){

										new PlayChess().play(chessX, chessY , Data.myChess);

									}
									else{

										MessageManager.getInstance().addMessage("不能下在这里。。");
									}
								}


							}
							else{
//								若为对方落子
								if(Data.turn == Data.oppoChess){

									MessageManager.getInstance().addMessage("没轮到你下。。。");
								}
								else{
//									若游戏已经结束
									if(Data.turn == 0){
										MessageManager.getInstance().addMessage("已经结束啦！");
									}
								}

							}
						}
						else{
							MessageManager.getInstance().addMessage("等待对方回复。。。");
						}
					}
					else{
						MessageManager.getInstance().addMessage("等待对方准备。。。");
					}
				}
				else{
					MessageManager.getInstance().addMessage("先点击“开始”吧！");
				}
			}

			else{
				MessageManager.getInstance().addMessage("选个对手吧！");
			}
		}


		else{

			MessageManager.getInstance().addMessage("请先登陆！");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e3) {

		ChessBoardCanvas canvas = (ChessBoardCanvas)e3.getSource();

		if(!Data.wait){

			canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else{
			canvas.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		}


	}

	@Override
	public void mouseExited(MouseEvent e4) {
		ChessBoardCanvas canvas = (ChessBoardCanvas)e4.getSource();
		canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}


}
