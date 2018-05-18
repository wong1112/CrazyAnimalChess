package client.ui;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import javax.swing.border.EtchedBorder;
//import javax.swing.border.TitledBorder;
import client.listener.MessageListener;
import client.manager.MessageManager;

public class MessagePanel extends JPanel {
	/**
	 * 消息区
	 */
	private static final long serialVersionUID = 1L;

	private JPanel message = new JPanel(new BorderLayout());

	private JTextField messageIn = null;

	public JTextField getMessageIn() {
		if(messageIn == null){
			messageIn = new JTextField();
		}
		return messageIn;
	}

	MessagePanel(){

		this.setLayout(new BorderLayout());
		message.add(MessageManager.getInstance().getMessageArea(),BorderLayout.CENTER);
		getMessageIn().addActionListener(new MessageListener());
		message.add(getMessageIn(),BorderLayout.SOUTH);
		this.add(message);
//		this.setBorder(new TitledBorder(new EtchedBorder() ));
	}
}
