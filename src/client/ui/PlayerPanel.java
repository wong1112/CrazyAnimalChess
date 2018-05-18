package client.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.listener.ChallengeListener;
import client.listener.QuitListener;
import client.manager.ListManager;

public class PlayerPanel extends JPanel {
	/**
	 * 玩家列表区
	 */
	private static final long serialVersionUID = 1L;

	private JLabel opponentInfo = null;



	private JPanel playerBar;
	private JPanel playerBody;
	private JPanel playerButtom;

	private JScrollPane listPane;

	private JButton challenge;
	private JButton quit;



	PlayerPanel(){

		playerBar = new JPanel();
		playerBody = new JPanel();
		playerButtom = new JPanel(new BorderLayout());

		listPane = new JScrollPane();

		challenge = new JButton("挑战他");
		quit = new JButton("不玩了");

		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listPane.setViewportView(ListManager.getInstance().getPlayerList());

		playerBody.add(listPane);

		playerButtom.add(getOpponentInfo(),BorderLayout.NORTH);
		playerButtom.add(quit,BorderLayout.WEST);
		playerButtom.add(challenge,BorderLayout.EAST);

		quit.addActionListener(new QuitListener());
		challenge.addActionListener(new ChallengeListener());

		ListManager.getInstance().getPlayerList().setFixedCellWidth(200);


		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(new EtchedBorder(), "玩家列表" ,TitledBorder.CENTER ,TitledBorder.TOP ));

		this.add(playerBar,BorderLayout.NORTH);
		this.add(playerBody,BorderLayout.CENTER);
		this.add(playerButtom,BorderLayout.SOUTH);

	}


	public JLabel getOpponentInfo() {

		if (opponentInfo == null){

			opponentInfo = new JLabel("目前对手：无");
		}
		return opponentInfo;
	}

}
