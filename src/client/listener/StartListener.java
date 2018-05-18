package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import client.data.Data;
import client.manager.IOManager;
import client.manager.MessageManager;

public class StartListener implements ActionListener {

	/**
	 * 监听开始按钮
	 */
	@Override
	public void actionPerformed(ActionEvent e) {


		if(Data.connected){
			if(Data.oppoId != 0){
				if(Data.ready == false){
					IOManager.getInstance().getPs().println("OPER:STAR:");

					MessageManager.getInstance().addMessage("请稍等。。。");

					Data.ready = true;
				}
				else{
					MessageManager.getInstance().addMessage("已经准备过了！");
				}

			}
			else{

				JOptionPane.showMessageDialog(null, "先选择一个对手吧！");
			}
		}
		else{
			MessageManager.getInstance().addMessage("先登陆吧！");
		}


	}
}
