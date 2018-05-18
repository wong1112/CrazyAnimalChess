package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.data.Data;
import client.manager.IOManager;
import client.manager.MessageManager;


public class BackListener implements ActionListener{
	/**
	 * 监听悔棋操作
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {

		if(Data.started){

			IOManager.getInstance().getPs().println("OPER:BACK:" + Data.myId);

			Data.wait = true;
			MessageManager.getInstance().addMessage("等待对方回复。。。");

		}

	}

}
