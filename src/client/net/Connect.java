package client.net;

import java.io.*;
import java.net.*;

import client.data.*;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;

public class Connect {
	/**
	 * 登陆服务器
	 */
	public void connect(){

		try {

			String portStr = GameFrame.getInstance().getGamePanel().getPort().getText();
			String ipStr = GameFrame.getInstance().getGamePanel().getIp().getText();

			int portValue = Integer.parseInt(portStr);

			Socket socket = new Socket(ipStr,portValue);

			Data.connected = true;

			MessageManager.getInstance().addMessage("服务器已连接！");
			GameFrame.getInstance().getGamePanel().getConnectInfo().setText("服务器状态：已登录！");

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			InputStreamReader isr = new InputStreamReader(is);

			IOManager.getInstance().setBr(isr);
			IOManager.getInstance().setPs(os);

		} catch (UnknownHostException e) {

			MessageManager.getInstance().addMessage("找不到服务器！");

		} catch (IOException e) {

			MessageManager.getInstance().addMessage("服务器连接出错！");
		}

	}
}
