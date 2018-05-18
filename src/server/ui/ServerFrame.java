package server.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.net.ServerThread;
import server.tool.MessageManager;

public class ServerFrame extends JFrame {

	/**
	 * 服务器主窗口
	 */
	private static final long serialVersionUID = 1L;

	private static ServerFrame instance = null;

	ServerSocket ss;
	private ClientPanel clientPanel = null;
	private MatchsPanel matchsPanel = null;
	private MessagePanel messagePanel = null;

	public ServerFrame() {

		this.setLayout(new BorderLayout());

		this.setVisible(true);
		this.setLocation(300, 100);

		this.add(getMatchsPanel(), BorderLayout.EAST);
		this.add(getClientPanel(), BorderLayout.WEST);
		this.add(getMessagePanel(), BorderLayout.SOUTH);

		this.pack();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void launchFrame() {

		// 启动服务器端口
		try {
			ss = new ServerSocket(9990);
			MessageManager.getInstance().addMessage("服务器已经启动!");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "端口被占用！请检查端口");
			System.exit(0);
		}
		while (true) {
			try {

				// 接受客户端连接
				Socket s = ss.accept();

				Thread t = new Thread(new ServerThread(s));
				t.start();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public ClientPanel getClientPanel() {
		if (clientPanel == null) {
			clientPanel = new ClientPanel();
		}
		return clientPanel;
	}

	public MatchsPanel getMatchsPanel() {
		if (matchsPanel == null) {
			matchsPanel = new MatchsPanel();
		}
		return matchsPanel;
	}

	public MessagePanel getMessagePanel() {
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}

	public static ServerFrame getInstance() {
		if (instance == null) {
			instance = new ServerFrame();
		}
		return instance;
	}

}
