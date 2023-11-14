package checkers;

import javax.swing.JPanel;
import javax.swing.JLabel;
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
		
		menuLabel = new JLabel("Menu");
		menuLabel.setBounds(193, 11, 46, 14);
		add(menuLabel);
		
		hostGameButton = new JButton("Host Game");
		hostGameButton.setBounds(152, 64, 112, 23);
		add(hostGameButton);
		
		joinGameButton = new JButton("Join Game");
		joinGameButton.setBounds(152, 98, 112, 23);
		add(joinGameButton);
		
		logOutButton = new JButton("Log Out");
		logOutButton.setBounds(152, 246, 112, 23);
		add(logOutButton);
		
		this.parent = clientGUI;
		client = parent.getChatClient();
		
		controller = new MenuController(this, this.parent, this.client);
		
	}
}
