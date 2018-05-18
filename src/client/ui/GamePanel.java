package client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.listener.ConnectListener;
import client.listener.NameListener;

public class GamePanel extends JPanel {
	/**
	 * 左边游戏区
	 */
	private static final long serialVersionUID = 1L;

	private JPanel gameBar;
	private JPanel gameBody;
	private JPanel nameArea;

	private JButton name;
	private JButton connectBtn;

	private JLabel symbol;
	private JLabel connectInfo;

	private JTextField ip;
	private JTextField port = null;

	private ChessBoardCanvas mapCanvas = null;;

	private JTextField nameIn;

	GamePanel() {
		nameIn = new JTextField("棋坛新手", 6);

		connectBtn = new JButton("登录");
		symbol = new JLabel(":");

		name = new JButton("修改昵称");
		name.addActionListener(new NameListener());

		connectBtn.addActionListener(new ConnectListener());

		nameArea = new JPanel();
		nameArea.setLayout(new BorderLayout());
		nameArea.add(getNameIn(), BorderLayout.CENTER);
		nameArea.add(name, BorderLayout.EAST);

		gameBar = new JPanel();
		gameBar.setLayout(new FlowLayout());
		gameBar.add(this.getConnectInfo());
		gameBar.add(getIp());
		gameBar.add(symbol);
		gameBar.add(getPort());
		gameBar.add(connectBtn);
		gameBar.add(nameArea);
		// gameBar.setBorder(new TitledBorder(new EtchedBorder()));

		gameBody = new JPanel();
		gameBody.add(getMapCanvas());
		// gameBody.setBorder(new TitledBorder(new EtchedBorder()));

		this.setLayout(new BorderLayout());
		this.add(gameBar, BorderLayout.NORTH);
		this.add(gameBody, BorderLayout.CENTER);

	}

	public JLabel getConnectInfo() {
		if (connectInfo == null) {
			connectInfo = new JLabel("服务器状态：未登录！  ");
		}
		return connectInfo;
	}

	public ChessBoardCanvas getMapCanvas() {

		if (mapCanvas == null) {

			mapCanvas = new ChessBoardCanvas();
		}
		return mapCanvas;
	}

	public JTextField getPort() {

		if (port == null) {

			port = new JTextField("9990");
		}
		return port;
	}

	public JTextField getIp() {

		if (ip == null) {

			ip = new JTextField("127.0.0.1", 9);
		}
		return ip;
	}

	public JTextField getNameIn() {

		if (nameIn == null) {

			nameIn = new JTextField("棋坛新手", 6);
			nameIn.setEditable(true);
		}
		return nameIn;
	}

}
