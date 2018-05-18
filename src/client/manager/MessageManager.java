package client.manager;

import java.awt.TextArea;

public class MessageManager {
	/**
	 * 管理消息
	 */
	private static MessageManager instance = null;
	private TextArea messageArea = null;

	private MessageManager(){

	}

	public static MessageManager getInstance(){

		if(instance == null){

			instance = new MessageManager();
		}
		return instance;
	}


	public TextArea getMessageArea(){

		if(messageArea == null){

			messageArea = new TextArea("",16,28,TextArea.SCROLLBARS_VERTICAL_ONLY);
		}
		return messageArea;
	}


	public void addMessage(String message){

		getMessageArea().append(message + "\n\n" );
	}

}
