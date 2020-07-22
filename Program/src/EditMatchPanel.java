import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EditMatchPanel extends PopupPanel implements ActionListener {
	private JLabel team1Label;
	private JLabel team2Label;
	private JLabel[] statLabel;
	private JTextField[] team1Stat;
	private JTextField[] team2Stat;
	private JButton cancelButton;
	private JButton saveButton;
	
	private String team1;
	private String team2;
	private String[] statLine1;
	private String[] statLine2;
	private String[] statNames;
	
	public EditMatchPanel(String team1, String team2) {
		this.team1 = team1;
		this.team2 = team2;
		
		team1Label = new JLabel(team1, SwingConstants.CENTER);
		team2Label = new JLabel(team2, SwingConstants.CENTER);
		cancelButton = new JButton("Cancel");
		saveButton = new JButton("Save");
		
		this.add(team1Label);
		this.add(team2Label);
		this.add(cancelButton);
		this.add(saveButton);
		
		
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		displayStats();
		initialize();
	}
	
	public void initialize() {
		team1Label.setFont(DEFAULT_FONT);
		team2Label.setFont(DEFAULT_FONT);
		
		team1Label.setBounds(STAT_LEFT_MARGIN_X, UPPER_BED_Y, LABEL_SIZE_X, LABEL_SIZE_Y);
		team2Label.setBounds(STAT_RIGHT_MARGIN_X, UPPER_BED_Y, LABEL_SIZE_X, LABEL_SIZE_Y);
		
		cancelButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		saveButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	}
	
	
	private void displayStats() {
		try {
			statLine1 = FileHandler.getStatLine(EditPanel.selTourney, team1, team2);
			statLine2 = FileHandler.getStatLine(EditPanel.selTourney, team2, team1);
			statNames = FileHandler.getStatNames(EditPanel.selTourney);
		} catch (Exception ex) {}
		

		team1Stat = new JTextField[statLine1.length];
		team2Stat = new JTextField[statLine2.length];
		statLabel = new JLabel[statNames.length];
		
		for (int i = 0; i < statLabel.length; i++) {
			team1Stat[i] = new JTextField(statLine1[i]);
			team2Stat[i] = new JTextField(statLine2[i]);
			statLabel[i] = new JLabel(statNames[i], SwingConstants.CENTER);
			
			this.add(team1Stat[i]);
			this.add(team2Stat[i]);
			this.add(statLabel[i]);
			statLabel[i].setFont(DEFAULT_FONT);
			
			team1Stat[i].setBounds(STAT_LEFT_MARGIN_X, UPPER_BED_Y+((i+1)*30), LABEL_SIZE_X, LABEL_SIZE_Y);
			team2Stat[i].setBounds(STAT_RIGHT_MARGIN_X, UPPER_BED_Y+((i+1)*30), LABEL_SIZE_X, LABEL_SIZE_Y);
			statLabel[i].setBounds(STAT_MIDDLE_MARGIN_X, UPPER_BED_Y+((i+1)*30), LABEL_SIZE_X, LABEL_SIZE_Y);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == cancelButton) {
			((Window) getRootPane().getParent()).dispose();
		}
		
		if (e.getSource() == saveButton) {
			for (int i = 0; i < statNames.length; i++) {
				statLine1[i] = team1Stat[i].getText();
				statLine2[i] = team2Stat[i].getText();
			}
			try {
				FileHandler.updateStats(EditPanel.selTourney, team1, team2, statLine1);
				FileHandler.updateStats(EditPanel.selTourney, team2, team1, statLine2);
				
				JOptionPane.showMessageDialog(null, "Successfully edited match.", 
						"Completed Successfully", 
						JOptionPane.INFORMATION_MESSAGE);
				((Window) getRootPane().getParent()).dispose();
				MainFrame.editPanel.initialize();
			} catch (Exception ex) {}
			
		}
	}
}






