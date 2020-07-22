import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePanel extends MainPanel implements ActionListener {
	private JLabel homeLabel;
	private JLabel homeIcon;
	private JButton createButton;
	private JButton editButton;
	private JButton statsButton;
	private JButton quitButton;
	
	public HomePanel() {
		homeLabel = new JLabel("Welcome to TourneyMaker");
		homeIcon = new JLabel(new ImageIcon("trophy.gif"));
		createButton = new JButton("Create");
		editButton = new JButton("Edit");
		statsButton = new JButton("Statistics");
		quitButton = new JButton("Quit");	
		
		this.add(homeLabel);	
	    this.add(homeIcon);
	    this.add(createButton);
	    this.add(editButton);
	    this.add(statsButton);
	    this.add(quitButton);
	    
	    createButton.addActionListener(this);
		editButton.addActionListener(this);
		statsButton.addActionListener(this);
		quitButton.addActionListener(this);
	    
	    initialize();
	}
	
	private void initialize() {
		homeLabel.setFont(homeLabel.getFont().deriveFont(50.0f));
		homeLabel.setBounds(140, 10, 800, 60);
		homeIcon.setBounds(250, 80, 400, 400);
		createButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		editButton.setBounds(BUTTON_SLOT_2_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		statsButton.setBounds(BUTTON_SLOT_3_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		quitButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createButton) {
			try {
				PopupFrame f =  new PopupFrame();
				f.centreWindow();
				f.setTitle("Create a New Tournament");
				f.setVisible(true);
				PopupFrame.cardLayout.show(PopupFrame.container, "createPanel");  
			} catch(Exception ex) {}
		}
		
		if (e.getSource() == editButton) {
			MainFrame.cardLayout.show(MainFrame.container, "editPanel");
			MainFrame.editPanel.initialize();
			
		}
		
		if (e.getSource() == statsButton) {
			MainFrame.cardLayout.show(MainFrame.container, "statsPanel");  
			MainFrame.statsPanel.makeTable();
			MainFrame.statsPanel.makeList();
		}
		
		if (e.getSource() == quitButton) {
			System.exit(0);	
		}
	}
}
