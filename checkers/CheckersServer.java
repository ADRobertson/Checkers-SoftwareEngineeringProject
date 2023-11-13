package checkers;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CheckersServer extends AbstractServer {
	private JLabel status;
	private JTextArea serverLog;
	private DatabaseFile databaseFile;
	private Database database;
	
	public void setDatabase(Database data) {
		database = data;
	}
	public CheckersServer(JLabel status, JTextArea log) {
		// super class constructor with port number as param
		super(12345);
		this.setTimeout(500);
		this.status = status;
		this.serverLog = log;
		databaseFile = new DatabaseFile();
	}
	
	public void clientConnected(ConnectionToClient client) {
		try {
			client.sendToClient("username:"+client.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverLog.append("Client " + client.getId() + " Connected\n" );
	}
	
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		/*
		try {
			arg1.sendToClient("hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error on send");
		}
		
		
		serverLog.append("Client " + arg1.getId() + ": " + arg0.toString() + "\n");
		try {
			arg1.sendToClient(arg0.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error on send");
		}
		*/
		String message = arg0.toString();
		System.out.println(message);
		if (message.contains("NEW")) {
			String[] splitString = message.split(":");
			String[] splitUserNameAndPassword = splitString[1].split(",");
			LoginData newLogin = new LoginData(splitUserNameAndPassword[0],splitUserNameAndPassword[1]);
			
			if (database.checkValidUserName(newLogin.getUserName())) {
				database.addNewUser(newLogin);
				try {
					arg1.sendToClient("NEW:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("NEW:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//User userToAdd = new User(1,newLogin.getUserName(), newLogin.getPassword());
			//boolean added = databaseFile.addNewUser(userToAdd);
			
			/*
			if (added) {
				try {
					arg1.sendToClient("NEW:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("NEW:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
		}
		if (message.contains("LOGIN")) {
			String[] splitString = message.split(":");
			String[] splitUserNameAndPassword = splitString[1].split(",");
			LoginData userToVerify = new LoginData(splitUserNameAndPassword[0],splitUserNameAndPassword[1]);
			
			if (database.checkValidLoginData(userToVerify)) {
				try {
					arg1.sendToClient("LOGIN:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					arg1.sendToClient("LOGIN:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/*
			if(databaseFile.verifyLoginInformation(userToVerify)) {
				try {
					arg1.sendToClient("LOGIN:TRUE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
			}
			else {
				try {
					arg1.sendToClient("LOGIN:FALSE");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			*/
		}
		
		
	}
	
	public void listeningException(Throwable exception) {

		this.status.setText("Exception Occurred when Listening");
		this.status.setForeground(Color.red);
		this.serverLog.append(exception.getMessage() + "\n");
	}
	
	public void serverStarted() {
		this.status.setText("Listening");
		this.status.setForeground(Color.green);
		this.serverLog.append("Server Started\n");
	}
	
	public void serverStopped() {
		this.status.setText("Stopped");
		this.status.setForeground(Color.red);
		this.serverLog.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
	}
	
	public void serverClosed() {
		this.status.setText("Closed");
		this.status.setForeground(Color.red);
		this.serverLog.append("Sever and all current clients are closed - Press Listen to Restart");
	}

}
