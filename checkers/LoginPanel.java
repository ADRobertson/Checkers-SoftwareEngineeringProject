package checkers;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	private JLabel titleLabel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JButton submitButton;
	private JButton cancelButton;
	private JLabel incorrectLoginLabel;
	private LoginController controller;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private ClientGUI parent;
	private CheckersClient client;
	
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public JLabel getIncorrectLoginLabel() {
		return incorrectLoginLabel;
	}
	
	public void loginError() {
		//this.incorrectLoginLabel.setText("Username and Pasword are Incorrect");
		this.incorrectLoginLabel.setVisible(true);
	}
	
	public JTextField getUserNameTextField() {
		return userNameTextField;
	}
	
	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}
	

	public LoginPanel(ClientGUI clientGUI) {
		setLayout(null);
		//setLayout();
		
		titleLabel = new JLabel("Enter Your Username and Password to Log In");
		titleLabel.setBounds(74, 50, 292, 14);
		add(titleLabel);
		
		userNameLabel = new JLabel("Username:");
		userNameLabel.setBounds(111, 78, 98, 14);
		add(userNameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(111, 106, 98, 14);
		add(passwordLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(219, 75, 86, 20);
		add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(219, 103, 86, 20);
		add(passwordTextField);
		passwordTextField.setColumns(10);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(132, 131, 77, 23);
		add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(219, 131, 86, 23);
		add(cancelButton);
		
		incorrectLoginLabel = new JLabel("Username and Password are Incorrect");
		incorrectLoginLabel.setForeground(Color.RED);
		incorrectLoginLabel.setBounds(74, 35, 292, 14);
		add(incorrectLoginLabel);
		incorrectLoginLabel.setVisible(false);
		 
		this.parent = clientGUI;
		this.client = parent.getChatClient();
		//this.controller = new LoginController(submitButton, cancelButton, incorrectLoginLabel, userNameTextField, passwordTextField, parent);
		this.controller = new LoginController(this, parent,this.client);
		
		
	}
}
