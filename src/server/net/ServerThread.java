package server.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;
import server.tool.Player;

public class ServerThread implements Runnable {
	/**
	 * 服务于每个客户端的线程
	 */

	Socket s;

	public ServerThread(Socket s) {

		this.s = s;

		Player player = new Player(this.hashCode(),s);

		HashMapManager.getInstance().addPlayer(this.hashCode(), player);
	}

	@Override
	public void run() {

		boolean connected = true;
		InputStream is;
		String readLine = null;
		MessageManager.getInstance().addMessage("玩家"+ this.hashCode()+ "上线了！");

		while(connected){
			try {
				is = s.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				readLine = br.readLine();

				new Resolve().resolve(this.hashCode(),s, readLine);

			} catch (IOException e) {
				connected = false;
				new EndDeal().clientOff(this.hashCode());
			}
		}
	}
}
