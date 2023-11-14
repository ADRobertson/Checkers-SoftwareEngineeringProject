package checkers;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class CheckersClient extends AbstractClient{


	private LoginPanel loginView;
	private CreateAccountPanel createAccountView;
	private MenuPanel menuView;
	private ClientGUI parent;

	public void setLoginView(LoginPanel loginView) {
		this.loginView = loginView;
	}
	public void setCreateAccountView(CreateAccountPanel createAccountView) {
		this.createAccountView = createAccountView;
	}
	
	public void setMenuView(MenuPanel menuView) {
		this.menuView = menuView;
	}
	public CheckersClient(ClientGUI parent) {
		super("localhost", 8300);
		this.parent = parent;
	}
	
	
	public void handleMessageFromServer(Object arg0) {
		System.out.println("Server Message Sent to Client: " + arg0);
		String test = arg0.toString();
		System.out.println(test.contains("username"));
		if(test.contains("HOSTED")) {
			parent.changeToGameView();
		}
		if(test.contains("JOINED")) {
			parent.changeToGameView();
		}
		if(test.contains("username")) {
			System.out.println("Found Username");
			String[] temp = arg0.toString().split(":");
			String id = temp[1];
			id = id.trim();
			System.out.println("Client ID ="  + id);
			
		}
		
		if(test.equals("LOGIN:TRUE")) {
			//parent.changeToContactView();
			parent.changeToMenuView();
		}
		if (test.equals("LOGIN:FALSE")) {
			loginView.loginError();
		}
		
		if (test.equals("NEW:TRUE")) {
			//parent.changeToContactView();
			//createAccountView.loggedIntoNewAccount();
			//this is where menu will go
			parent.changeToMenuView();
		}
		if (test.equals("NEW:FALSE")) {
			createAccountView.userNameError("Username Already In Use");
		}
	}
	
	public void connectionException(Throwable exception) {
		System.out.println("Connected Exception Occurred:");
		System.out.println(exception.getMessage());
		exception.printStackTrace();
	}
	
	public void connectionEstablished() {
		
	}
	
	public void sendLoginData(LoginData loginData) {
		try {
			Object testing = loginData.toString();
			sendToServer("LOGIN:" + testing);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendHostGame() {
		try {
			sendToServer("HOST");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendJoinGame() {
		try {
			sendToServer("JOIN");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNewLogin(LoginData loginData) {
		try {
			Object testing = loginData.toString();
			sendToServer("NEW:"+testing);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
