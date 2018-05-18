package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.data.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;

public class NameListener implements ActionListener{
	/**
	 * 监听修改姓名操作
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		if( Data.connected){

			GameFrame.getInstance().getGamePanel();

			String newName = GameFrame.getInstance().getGamePanel().getNameIn().getText();

			if(newName != null){

				if(newName.equals(Data.myName)){

					MessageManager.getInstance().addMessage("已经是这个昵称啦！");
				}
				else{

					IOManager.getInstance().getPs().println("NAME:" + newName);

				}
			}
			else{
				MessageManager.getInstance().addMessage("请输入一个昵称！");
			}
		}
		else{
			MessageManager.getInstance().addMessage("请先登陆服务器！");
		}
	}

}
