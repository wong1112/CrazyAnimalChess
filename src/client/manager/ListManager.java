package client.manager;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import client.listener.ListListener;

public class ListManager {
	/**
	 * 管理玩家列表
	 */
	private static ListManager instance = null;

	private JList playerList = null;
	private DefaultListModel listModel = null;

	private ListManager(){

	}
	public static ListManager getInstance(){

		if(instance == null){

			instance = new ListManager();
		}
		return instance;

	}
	public JList getPlayerList() {

		if(playerList == null){

			playerList = new JList(getListModel());
			playerList.addMouseListener(new ListListener());
		}
		return playerList;


	}

	public DefaultListModel getListModel() {

		if(listModel == null){

			listModel = new DefaultListModel();
		}
		return listModel;
	}
	public void clearList(){

		this.getListModel().clear();
		this.getPlayerList().repaint();

	}
	public void addPlayer(String name){

		this.getListModel().addElement(name);
		this.getPlayerList().repaint();

	}
	public void removePlayer(String name){

		this.getListModel().removeElement(name);
		this.getPlayerList().repaint();

	}
}
