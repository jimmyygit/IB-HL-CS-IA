import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class MatchPane extends JPanel {
	public static final int LABEL_SIZE_X = 150;
	public static final int LABEL_SIZE_Y = 30;
	
	public static final int LEFT_MARGIN_X = 190;
	public static final int RIGHT_MARGIN_X = 300;
	public static final int UPPER_BED_Y = 20;
	
	public static final Font VS_FONT = new Font("Arial", Font.PLAIN, 30);
	public static final Font TEAM_FONT = new Font("Arial", Font.PLAIN, 15);
	
	private String team1Name;
	private String team2Name;
	private String team1Image;
	private String team2Image;
	
	private JLabel nameLabel;
	private JLabel vsLabel;
	private JLabel team1Label;
	private JLabel team2Label;
	private JLabel team1Icon;
	private JLabel team2Icon;
	private JLabel team1Stat;
	private JLabel team2Stat;
	
	public MatchPane(String team1, String team2) {
		this.setLayout(null);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(new Dimension(540, 80));
		
		this.team1Name = team1.split(" ")[0];
		this.team2Name = team2.split(" ")[0];
		this.team1Image = team1.split(" ")[1];
		this.team2Image = team2.split(" ")[1];
	    
		nameLabel = new JLabel("Season", SwingConstants.CENTER);
		vsLabel = new JLabel("VS", SwingConstants.CENTER);
		team1Label = new JLabel(team1Name, SwingConstants.RIGHT);
		team2Label = new JLabel(team2Name, SwingConstants.LEFT);
		team1Icon = new JLabel(new ImageIcon(team1Image));
		team2Icon = new JLabel(new ImageIcon(team2Image));
		
		this.add(nameLabel);
		this.add(vsLabel);
		this.add(team1Label);
		this.add(team2Label);
		this.add(team1Icon);
		this.add(team2Icon);
	   
		displayWinner();
	    initialize();
	}
	
	public void initialize() {
		nameLabel.setFont(TEAM_FONT);
		vsLabel.setFont(VS_FONT);
		team1Label.setFont(TEAM_FONT);
		team2Label.setFont(TEAM_FONT);
		
		team1Label.setBorder(BorderFactory.createRaisedBevelBorder());
		team2Label.setBorder(BorderFactory.createRaisedBevelBorder());
		
		nameLabel.setBounds(195, UPPER_BED_Y-20, LABEL_SIZE_X, LABEL_SIZE_Y-10);
		vsLabel.setBounds(245, UPPER_BED_Y, 50, 50);
		team1Label.setBounds(30, UPPER_BED_Y+10, LABEL_SIZE_X, LABEL_SIZE_Y);
		team2Label.setBounds(360, UPPER_BED_Y+10, LABEL_SIZE_X, LABEL_SIZE_Y);
		team1Icon.setBounds(LEFT_MARGIN_X, UPPER_BED_Y, 50, 50);
		team2Icon.setBounds(RIGHT_MARGIN_X, UPPER_BED_Y, 50, 50);
		
	}
	
	private void displayWinner() {
		try {
			int team1FirstStat = Integer.parseInt(FileHandler.getStatLine(EditPanel.selTourney, team1Name, team2Name)[0]);
			int team2FirstStat = Integer.parseInt(FileHandler.getStatLine(EditPanel.selTourney, team2Name, team1Name)[0]);
			
			team1Stat = new JLabel(team1FirstStat+"", SwingConstants.RIGHT);
			team2Stat = new JLabel(team2FirstStat+"", SwingConstants.LEFT);
			
			if (team1FirstStat > team2FirstStat) {
				team1Label.setBackground(Color.GREEN);
				team2Label.setBackground(Color.RED);
			} else if (team2FirstStat > team1FirstStat) {
				team1Label.setBackground(Color.RED);
				team2Label.setBackground(Color.GREEN);
			}
			
		} catch (Exception ex) {ex.printStackTrace();}
		this.add(team1Stat);
		this.add(team2Stat);
		
		team1Stat.setFont(TEAM_FONT);
		team2Stat.setFont(TEAM_FONT);
		
		team1Stat.setBounds(30, UPPER_BED_Y+35, LABEL_SIZE_X, LABEL_SIZE_Y);
		team2Stat.setBounds(360, UPPER_BED_Y+35, LABEL_SIZE_X, LABEL_SIZE_Y);
		
	}
	
	public void setMatchName(String name) {
		nameLabel.setText(name);
	}
	
	public String getWinner() {
		try {
			int team1FirstStat = Integer.parseInt(FileHandler.getStatLine(EditPanel.selTourney, team1Name, team2Name)[0]);
			int team2FirstStat = Integer.parseInt(FileHandler.getStatLine(EditPanel.selTourney, team2Name, team1Name)[0]);
			
			if (team1FirstStat > team2FirstStat) {
				return team1Name+" "+team1Image;
			} else if (team2FirstStat > team1FirstStat) {
				return team2Name+" "+team2Image;
			}
		} catch (Exception ex) {}
		return "-1";
	}
	
	
	public void showEditPanel() {
		try {
			PopupFrame f =  new PopupFrame(team1Name, team2Name);
			f.centreWindow();
			f.setTitle("Edit Match");
			f.setVisible(true);
			PopupFrame.cardLayout.show(PopupFrame.container, "editMatchPanel");  
		} catch (Exception ex) {}
	}

}
