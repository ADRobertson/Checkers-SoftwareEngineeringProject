package checkers;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientGUI extends JFrame{
	
	private InitialPanel initialView;
	private LoginPanel loginView;
	private CreateAccountPanel createAccountView;
	private CheckersClient client;
	private GamePanel gamePlayView;
	private GameSidePanel gameInfoView;
	private JPanel gameWrapper;
	private MenuPanel menuView;
	//private CardLayout cardLayout = new CardLayout();
	//private JPanel mainWrapperPanel = new JPanel(cardLayout);
	
	
	public void changeToMenuView() {
		this.setContentPane(menuView);
		this.invalidate();
		this.validate();
	}
	public void changeToGameView() {
		this.setSize(750,600);
		this.setContentPane(gameWrapper);
		this.invalidate();
		this.validate();
	}
	public void changeToLoginView() {
		this.setContentPane(loginView);
		this.invalidate();
		this.validate();
	}
	
	public void changeToInitialView() {
		this.setContentPane(initialView);
		this.invalidate();
		this.validate();
	}
	
	public void changeToCreateAccountView() {
		this.setContentPane(createAccountView);
		this.invalidate();
		this.validate();
	}
	

	
	public CheckersClient getChatClient() {
		return client;
	}
	
	public ClientGUI() {
		this.setSize(600,600);
		client= new CheckersClient(this);
		initialView = new InitialPanel(this);
		loginView = new LoginPanel(this);
		createAccountView = new CreateAccountPanel(this);
		this.menuView = new MenuPanel(this);
		this.gameInfoView = new GameSidePanel(this);
		this.gamePlayView = new GamePanel(this, gameInfoView);
		
		
		this.gameWrapper = new JPanel(new BorderLayout());
		gameWrapper.add(gamePlayView, BorderLayout.EAST);
		gameWrapper.add(gameInfoView, BorderLayout.CENTER);

		
		this.add(gameWrapper);
		client.setLoginView(loginView);
		client.setCreateAccountView(createAccountView);
		
		
		try {
			this.client.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setTitle("Checkers GUI");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		changeToInitialView();
		
		this.setVisible(true);
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    		try {
					client.closeConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    });
		
	}
	
	public static void main(String args[]) {
		ClientGUI gui = new ClientGUI();
	}
}
