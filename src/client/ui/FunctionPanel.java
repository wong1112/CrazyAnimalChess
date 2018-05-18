package client.ui;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class FunctionPanel extends JPanel{
	/**
	 * 功能区
	 */
	private static final long serialVersionUID = 1L;

	PlayerPanel playerPanel = null;
	MessagePanel  messagePanel = null;
	OperationPanel operationPanel = null;


	FunctionPanel(){

		this.setLayout(new BorderLayout());
		this.add(getPlayerPanel(),BorderLayout.NORTH);
		this.add(getMessagePanel(),BorderLayout.CENTER);
		this.add(getOperationPanel(),BorderLayout.SOUTH);

	}


	public PlayerPanel getPlayerPanel() {

		if (playerPanel == null){

			playerPanel = new PlayerPanel();
		}
		return playerPanel;
	}


	public MessagePanel getMessagePanel() {

		if (messagePanel == null){

			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}


	public OperationPanel getOperationPanel() {

		if (operationPanel == null){

			operationPanel = new OperationPanel();
		}
		return operationPanel;
	}

}
