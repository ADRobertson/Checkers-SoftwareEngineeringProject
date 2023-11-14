package checkers;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountPanel extends JPanel{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel[] titleLabels =  new JLabel[2];
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JLabel verifyPasswordLabel;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JPasswordField verifyPasswordTextField;
	private JButton submitButton;
	private JButton cancelButton;
	private CreateAccountController controller;
	private ClientGUI parent;
	private CheckersClient client;
	private JLabel statusMessageLabel;
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public JTextField getUserNameTextField() {
		return userNameTextField;
	}
	
	public JPasswordField getPasswordTextField() {
		return passwordTextField;
	}
	
	public JPasswordField getVerifyPasswordTextField() {
		return verifyPasswordTextField;
	}
	
	public void userNameError(String messageToSet) {
		//display error related to new thingy
		statusMessageLabel.setText(messageToSet);
	}
	
	
	public CreateAccountPanel(ClientGUI clientGUI) {
		setLayout(null);
		this.parent = clientGUI;
		
		titleLabels[0] = new JLabel("Enter a Username and Password to Create an Account.");
		titleLabels[0].setBounds(89, 32, 300, 14);
		add(titleLabels[0]);
		
		titleLabels[1] = new JLabel("Your Password Must Be At Least 6 Characters");
		titleLabels[1].setBounds(110, 57, 300, 14);
		add(titleLabels[1]);
		
		userNameLabel = new JLabel("Username:");
		userNameLabel.setBounds(89, 82, 99, 14);
		add(userNameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(89, 107, 70, 14);
		add(passwordLabel);
		
		verifyPasswordLabel= new JLabel("Verify Password:");
		verifyPasswordLabel.setBounds(70, 132, 108, 14);
		add(verifyPasswordLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(188, 82, 86, 20);
		add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(188, 107, 86, 20);
		add(passwordTextField);
		passwordTextField.setColumns(10);
		
		verifyPasswordTextField = new JPasswordField();
		verifyPasswordTextField.setBounds(188, 132, 86, 20);
		add(verifyPasswordTextField);
		verifyPasswordTextField.setColumns(10);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(110, 157, 89, 23);
		add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(209, 157, 89, 23);
		add(cancelButton);
		
		statusMessageLabel = new JLabel("");
		statusMessageLabel.setBounds(132, 49, 166, 14);
		add(statusMessageLabel);
		this.client = parent.getChatClient();
		
		controller = new CreateAccountController(this, parent, client);
		
		
	}
}
