import javax.swing.*; 
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EditPanel extends MainPanel implements ActionListener, ListSelectionListener {
	private JLabel listLabel;
	private JLabel tourneyLabel;
	private JLabel numberLabel;
	private JList<String> tourneyList;
	private JScrollPane tourneyScroll;
	private String[] listData;
	private JButton backButton;
	private JButton addButton;
	private MatchPanel matchPanel;
	
	public static String selTourney;
	public static int selIndex = 0;

	
	public EditPanel() {
		listLabel = new JLabel("Tournament List");
		tourneyLabel = new JLabel("Tournament");
		numberLabel = new JLabel("Number of Teams: 0");
		tourneyList = new JList<>();
		tourneyScroll = new JScrollPane();
		backButton = new JButton("Back");
		addButton = new JButton("Add Team");
		matchPanel = new MatchPanel();
	
		this.add(listLabel);
		this.add(tourneyLabel);
		this.add(numberLabel);
		this.add(tourneyList);
		this.add(tourneyScroll);
		this.add(backButton);
		this.add(addButton);
		this.add(matchPanel);
		
		tourneyList.addListSelectionListener(this);
		backButton.addActionListener(this);
		addButton.addActionListener(this);
		initialize();
	}
	
	public void initialize() {
		try {
			listData = FileHandler.getTourneyList("tourneyList.txt");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not find file.", "File not found", 
					JOptionPane.ERROR_MESSAGE);
		}
		int index = selIndex;
		listLabel.setFont(DEFAULT_FONT);
		tourneyLabel.setFont(DEFAULT_FONT);
		numberLabel.setFont(DEFAULT_FONT);
		
		listLabel.setBounds(LEFT_MARGIN_X, 40, 200, 40);
		tourneyLabel.setBounds(240, 40, 200, 40);
		numberLabel.setBounds(600, 40, 300, 40);
		
		tourneyList.setListData(listData);
		tourneyList.setSelectedIndex(index);
		tourneyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tourneyList.setBounds(LEFT_MARGIN_X, 80, 200, 400);
		tourneyScroll.setBounds(LEFT_MARGIN_X, 80, 200, 400);
		tourneyScroll.getViewport().add(tourneyList);
		tourneyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tourneyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		backButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		addButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		
		matchPanel.setBounds(240, 80, 600, 400);
		matchPanel.initialize();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			MainFrame.cardLayout.show(MainFrame.container, "homePanel"); 
		}
		
		if (e.getSource() == addButton) {
			try {
				PopupFrame f =  new PopupFrame();
				f.centreWindow();
				f.setTitle("Add a New Team");
				f.setVisible(true);
				PopupFrame.cardLayout.show(PopupFrame.container, "addTeamPanel");  
			} catch (Exception ex) {}

		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		try {
			selTourney = tourneyList.getSelectedValue();
			selIndex = tourneyList.getSelectedIndex();
			String teamNumber = FileHandler.getTeamNumber(selTourney);
			tourneyLabel.setText(selTourney);
			numberLabel.setText("Number of Teams: "+teamNumber);
			repaint();
			matchPanel.initialize();
		} catch (Exception ex) {}
	}
		
}
