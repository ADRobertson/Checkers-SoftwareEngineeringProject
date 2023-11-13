package checkers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class InitialPanel extends JPanel{
	private JLabel titleLabel;
	private JButton loginButton;
	private JButton createButton;
	private InitialController controller;
	private ClientGUI parent;
	
	public JButton getLoginButton() {
		return loginButton;
	}
	
	public JButton getCreateButton() {
		return createButton;
	}
	
	
	
	public InitialPanel(ClientGUI clientGUI) {
		//setLayout(null);
		this.parent = clientGUI;
		titleLabel = new JLabel("Account Information");
		titleLabel.setBounds(152, 22, 105, 40);
		add(titleLabel);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(162, 73, 89, 23);
		add(loginButton);
		
		createButton= new JButton("Create");
		createButton.setBounds(172, 107, 89, 23);
		add(createButton);
		
		controller = new InitialController(this, parent);
	}
}
