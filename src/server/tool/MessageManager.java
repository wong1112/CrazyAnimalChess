package server.tool;

import javax.swing.JTextArea;

public class MessageManager {

	/**
	 * 消息管理器
	 */
	private static MessageManager instance = null;

	private JTextArea messageArea = null;


	private MessageManager(){

	}
	public static MessageManager getInstance(){
		if(instance == null){
			instance = new MessageManager();
		}
		return instance;
	}

	public JTextArea getMessageArea(){
		if(messageArea == null){
			messageArea = new JTextArea("",10,33);
		}
		return messageArea;
	}

	//	添加消息
	public void addMessage(String s){
		messageArea.append(s +"\n");
	}
}

