package server.ui;

import javax.swing.JPanel;

import server.tool.MessageManager;

public class MessagePanel extends JPanel {
	/**
	 * 消息显示区
	 */
	private static final long serialVersionUID = 1L;

	public MessagePanel(){
		this.add(MessageManager.getInstance().getMessageArea());
	}
}
