package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;

import client.data.Data;
import client.manager.IOManager;
import client.manager.ListManager;
import client.manager.MessageManager;


public class ChallengeListener implements ActionListener{
	/**
	 *监听挑战按钮
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		int target;

		JList list = ListManager.getInstance().getPlayerList();

		if(Data.oppoId == 0){

			if(!list.isSelectionEmpty()){

				String s =(String) list.getSelectedValue();
				String [] ss = s.split("-");

				s = ss[0];

				target = Integer.parseInt(s);

				if(target != Data.myId){

					MessageManager.getInstance().addMessage("等待对方接受挑战！");

					IOManager.getInstance().getPs().println("OPER:" + "CHAL:" + target);
				}
				else{

					JOptionPane.showMessageDialog(null, "别闹。。。不能挑战自己。。。");
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
