package client.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import client.data.Data;
import client.manager.IOManager;
import client.manager.ListManager;
import client.manager.MessageManager;

public class ListListener extends MouseAdapter{

	/**
	 * 监听列表双击操作
	 * */

	@Override
	public void mouseClicked(MouseEvent e) {

		if(Data.oppoId == 0){
			if(!ListManager.getInstance().getPlayerList().isSelectionEmpty()){
				if(e.getClickCount() == 2){
					JList list = ListManager.getInstance().getPlayerList();
					int index = list.locationToIndex(e.getPoint());

					String s =(String) list.getModel().getElementAt(index);
					String [] ss = s.split("-");

					s = ss[0];

					int target = Integer.parseInt(s);

					if(target != Data.myId){

						MessageManager.getInstance().addMessage("等待对方接受挑战！");

						IOManager.getInstance().getPs().println("OPER:" + "CHAL:" + target);
					}
					else{

						JOptionPane.showMessageDialog(null, "别闹。。。不能挑战自己。。。");
					}

				}
			}
			else{
				JOptionPane.showMessageDialog(null, "未选中任何对手！");
			}
		}
		else{
			MessageManager.getInstance().addMessage("已经有对手了！");
		}
	}

}
