package checkers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

public class LeaderboardPanel extends JPanel {
	private ClientGUI parent;
	private CheckersClient client;
	private LeaderboardController controller;
	private JLabel winLoseLabel;
	private JLabel titleLabel;
	private JTextArea leaderboard;
	private JButton logout;
	private JButton exit;
	
	public void setLeaderboard (String leaderboardInfo) {
		leaderboard.setText("");
		String[] info = leaderboardInfo.split("[,]");
		System.out.print(info);
		
		for (int i = 0; i < leaderboardInfo.length(); i++) {
			leaderboard.setText(leaderboard.toString() + info);
		}
	}
	
	public String getLeaderboard() {
		return leaderboard.getText();
	}
	
	public void setWinLoseLabel (String result) {
		winLoseLabel.setText(result);
	}
	public String getWinLoseLabel() {
		return winLoseLabel.getText();
	}
	
	public JButton getLogoutButton() {
		return logout;
	}
	public JButton getExitButton() {
		return exit;
	}
	
	
	public LeaderboardPanel(ClientGUI parent) {
		setLayout(null);
		
		this.setBackground(new Color(200,170,130));
	
		winLoseLabel = new JLabel("");
		winLoseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winLoseLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		winLoseLabel.setBounds(170, 45, 250, 15);
		add(winLoseLabel);
		
		titleLabel = new JLabel("Leaderboard - Top 5");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		titleLabel.setBounds(170, 95, 250, 15);
		add(titleLabel);
		
		leaderboard = new JTextArea("");
		leaderboard.setEditable(false);
		leaderboard.setFont(new Font("Tahoma", Font.PLAIN, 12));
		leaderboard.setBounds(145, 130, 300, 120);
		add(leaderboard);
		
		logout = new JButton("Logout");
		logout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		logout.setBounds(170, 295, 100, 25);
		add(logout);
		
		exit = new JButton("Exit");
		exit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		exit.setBounds(320, 295, 100, 25);
		add(exit);
		
		this.parent = parent;
		this.client = parent.getChatClient();
		this.controller = new LeaderboardController(this, parent);
	}
}
