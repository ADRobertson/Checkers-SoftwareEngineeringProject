package checkers;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JButton;

public class MenuPanel extends JPanel{
	private JLabel menuLabel;
	private JButton hostGameButton;
	private JButton joinGameButton;
	private JButton logOutButton;
	private MenuController controller;
	private ClientGUI parent;
	private CheckersClient client;
	
	public JLabel getMenuLabel() {
		return menuLabel;
	}
	
	public JButton getHostGameButton() {
		return hostGameButton;
	}
	
	public JButton getJoinGameButton() {
		return joinGameButton;
	}
	
	public JButton getLogOutButton() {
		return logOutButton;
	}
	
	public MenuPanel(ClientGUI clientGUI) {
		setLayout(null);
		this.setBackground(new Color(200,170,130));
		
		menuLabel = new JLabel("Menu");
		menuLabel.setBounds(193, 11, 46, 14);
		add(menuLabel);
		
		hostGameButton = new JButton("Host Game");
		hostGameButton.setForeground(new Color(1,50,32));
		hostGameButton.setBackground(new Color(215,185,145));
		hostGameButton.setBounds(152, 64, 112, 23);
		add(hostGameButton);
		
		joinGameButton = new JButton("Join Game");
		joinGameButton.setForeground(new Color(1,50,32));
		joinGameButton.setBackground(new Color(215,185,145));
		joinGameButton.setBounds(152, 98, 112, 23);
		add(joinGameButton);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setForeground(new Color(1,50,32));
		logOutButton.setBackground(new Color(215,185,145));
		logOutButton.setBounds(152, 246, 112, 23);
		add(logOutButton);
		
		this.parent = clientGUI;
		client = parent.getChatClient();
		
		controller = new MenuController(this, this.parent, this.client);
		
	}
}
