import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MatchPanel extends SubPanel implements ActionListener, ListSelectionListener{
	private JList<MatchPane> matchList;
	private JScrollPane matchScroll;
	private JLabel matchLabel;
	private JButton editButton;
	private String tourneyType;
	
	public MatchPanel() {
		matchList = new JList<MatchPane>();
		matchScroll = new JScrollPane();
		matchLabel = new JLabel("Total Matches: 0");
		editButton = new JButton("Edit Match");
		
		this.add(matchList);
		this.add(matchScroll);
		this.add(matchLabel);
		this.add(editButton);
		
		editButton.addActionListener(this);
		
		initialize();
	}
	
	public void initialize() {
		
		matchList.setBounds(LEFT_MARGIN_X, 20, 560, 320);
		matchList.setSelectedIndex(0);
		matchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		matchScroll.setBounds(LEFT_MARGIN_X, 20, 560, 320);
		matchScroll.getViewport().add(matchList);
		matchScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		matchScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tourneyType = getTourneyType();
		MatchPane[] listData;
		if (tourneyType.equals("robin")) listData = getListDataRobin();
		else {
			listData = getListDataPlayoff();
			setMatchNames(listData);
		}
		matchList.setListData(listData);
	    matchList.setCellRenderer(new PanelRender());
	    matchLabel.setFont(DEFAULT_FONT);
	    matchLabel.setText("Total Matches: "+ listData.length);
	    matchLabel.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, LABEL_SIZE_X*2, LABEL_SIZE_Y);
	    
	    editButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	   
	}
	

	private MatchPane[] getListDataRobin() {
		ArrayList<MatchPane> matchList = new ArrayList<MatchPane>();
		try {
			String selTourney = EditPanel.selTourney;
			ArrayList<String> teamList = FileHandler.getTeamList(selTourney);
			for (int i = 0; i < teamList.size(); i++) {
				for (int j = i; j < teamList.size(); j++) {
					if (i != j) {
						matchList.add(new MatchPane(teamList.get(i), teamList.get(j)));
					}
				}
			}
			
		} catch (Exception ex) { }
		MatchPane[] matchArray = matchList.toArray(new MatchPane[matchList.size()]);
		return matchArray;
	}
	
	
	private MatchPane[] getListDataPlayoff() {
		ArrayList<MatchPane> matchList = new ArrayList<MatchPane>();
		try {
			String selTourney = EditPanel.selTourney;
			ArrayList<String> teamList = FileHandler.getTeamList(selTourney);
			for (int i = 1; i < teamList.size(); i+=2) {
				matchList.add(new MatchPane(teamList.get(i-1), teamList.get(i)));
			}
			
			matchList = advanceMatches(matchList);
			
		} catch (Exception ex) { }
		MatchPane[] matchArray = matchList.toArray(new MatchPane[matchList.size()]);
		return matchArray;
	}
	
	
	private ArrayList<MatchPane> advanceMatches(ArrayList<MatchPane> matchList) {
		ArrayList<MatchPane> newMatchList = new ArrayList<MatchPane>();
		boolean newMatch = false;
		for (int i = 1; i < matchList.size(); i+=2) {
			if (!matchList.get(i-1).getWinner().equals("-1") &&
					!matchList.get(i).getWinner().equals("-1")) {
				newMatchList.add(new MatchPane(matchList.get(i-1).getWinner(), 
						matchList.get(i).getWinner()));
				newMatch = true;
			}
		}
		if (newMatch) {
			matchList.addAll(advanceMatches(newMatchList));
			return matchList;
		} else return matchList;
	}
	
	
	private void setMatchNames(MatchPane[] matchList) {
		int size = 0;
		try {
			 size = Integer.parseInt(FileHandler.getTeamNumber(EditPanel.selTourney).charAt(2)+"");
		} catch (Exception ex) {}
		if (size == 4) {
			for (int i = 0; i < matchList.length; i++) {
				if (i <= 2) matchList[i].setMatchName("Final");
				if (i <= 1) matchList[i].setMatchName("Semifinal");
			}
		} else if (size == 8) {
			for (int i = 0; i < matchList.length; i++) {
				if (i <= 6) matchList[i].setMatchName("Final");
				if (i <= 5) matchList[i].setMatchName("Semifinal");
				if (i <= 3) matchList[i].setMatchName("Quarterfinal");
			}
		}
	}
	
	
	private String getTourneyType() {
		try {
			String selTourney = EditPanel.selTourney;
			if (FileHandler.getTourneyType(selTourney).equals("robin")) {
				return "robin";
			} else return "playoff";
		} catch (Exception ex) {}
		return "";
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == editButton) {
			if (matchList.getSelectedValue() != null) {
				matchList.getSelectedValue().showEditPanel();
			}
		}
		
	}
	
	public void valueChanged(ListSelectionEvent e) {
	}
}
