import javax.swing.*;
import javax.swing.table.*; 
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StatsPanel extends MainPanel implements ActionListener, ListSelectionListener { 
	private JLabel listLabel;
	private JLabel tourneyLabel;
	private JList<String> tourneyList;
	private JScrollPane tourneyScroll;
	private String[] listData;
	private JTable statTable;
	private JScrollPane statScroll;
	private JButton backButton;
	private JButton sortButton;
	
	public static String selTourney;
	public static int selIndex = 0;
	
	private String[] columnNames;
	private String[][] statData;
	
	public StatsPanel() {
		listLabel = new JLabel("Tournament List");
		tourneyLabel = new JLabel("Tournament");
		tourneyList = new JList<>();
		tourneyScroll = new JScrollPane();
		backButton = new JButton("Back");
		sortButton = new JButton("Sort");
		
		this.add(listLabel);
		this.add(tourneyLabel);
		this.add(tourneyList);
		this.add(tourneyScroll);
		this.add(backButton);
		this.add(sortButton);
		
		tourneyList.addListSelectionListener(this);
		backButton.addActionListener(this);
		sortButton.addActionListener(this);
		
		initialize();
	}
	
	public void initialize() {
		listLabel.setFont(DEFAULT_FONT);
		tourneyLabel.setFont(DEFAULT_FONT);
		
		listLabel.setBounds(LEFT_MARGIN_X, 40, 200, 40);
		tourneyLabel.setBounds(240, 40, 200, 40);
		
		makeList(); 
		tourneyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tourneyList.setBounds(LEFT_MARGIN_X, 80, 200, 400);
		tourneyScroll.setBounds(LEFT_MARGIN_X, 80, 200, 400);
		tourneyScroll.getViewport().add(tourneyList);
		tourneyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tourneyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		backButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		sortButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	}
	
	public void makeTable() {
		if (getTeamNumber() > 1) {
			columnNames = getColumnNames();
			statData = getData();
		} else emptyTable();
		statTable = new StatTable(statData, columnNames);
		statTable.setRowHeight(30);
		statTable.setRowSelectionAllowed(false);
		statTable.setColumnSelectionAllowed(true);
		statTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statScroll = new JScrollPane(statTable);
		this.add(statScroll);
		statScroll.setBounds(240, 80, 600, 400);
	}
	
	public void emptyTable() {
		columnNames = new String[1];
		statData = new String[1][1];
		columnNames[0] = "Team";
		statData[0][0] = " ";
	}
	
	private void sortTable() {
		System.out.println("sropt");
		mergeSort(statData, new String[statData.length][statData[0].length], 
				0, statData.length - 1); 
		
		statTable = new StatTable(statData, columnNames);
		statTable.setRowHeight(30);
		statTable.setRowSelectionAllowed(false);
		statTable.setColumnSelectionAllowed(true);
		statTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statScroll = new JScrollPane(statTable);
		this.add(statScroll);
		statScroll.setBounds(240, 80, 600, 400);
	}
	
	private void mergeSort(String[][] array, String[][] temp, int leftStart, int rightEnd) {
		if (leftStart >= rightEnd) {
			return;
		}
		int middle = (leftStart + rightEnd) / 2;
		mergeSort(array, temp, leftStart, middle);
		mergeSort(array, temp, middle+1, rightEnd);
		mergeHalves(array, temp, leftStart, rightEnd);
	}
	
	private void mergeHalves(String[][] array, String[][] temp, int leftStart, int rightEnd) {
		int leftEnd = (rightEnd + leftStart) / 2;
		int rightStart = leftEnd + 1;
		int size = rightEnd - leftStart + 1;
		
		int left = leftStart;
		int right = rightStart;
		int index = leftStart;
		int sortIndex = statTable.getSelectedColumn();
		
		while (left <= leftEnd && right <= rightEnd) {
			int leftValue = Integer.parseInt(array[left][sortIndex]);
			int rightValue = Integer.parseInt(array[right][sortIndex]);
			
			if (leftValue >= rightValue) {
				temp[index] = array[left];
				left++;
			} else {
				temp[index] = array[right];
				right++;
			}
			index++;
		}
		System.arraycopy(array, left, temp, index, leftEnd-left+1);
		System.arraycopy(array, right, temp, index, rightEnd-right+1);
		System.arraycopy(temp, leftStart, array, leftStart, size);	
	}
	
	private String[] getColumnNames() {
		ArrayList<String> statNames = new ArrayList<String>();
		try {
			Collections.addAll(statNames, FileHandler.getStatNames(selTourney));
		} catch(Exception ex) { System.out.println("ere");}
		statNames.add(0, "Team");
		return statNames.toArray(new String[statNames.size()]);
	}
	
	private String[][] getData() {
		String[][] tableData = new String[0][0];
		ArrayList<String> teamList = new ArrayList<String>();
		try {
			teamList = FileHandler.getTeamList(selTourney);
			ArrayList<String> nameList = new ArrayList<String>();
			for (String name : teamList) {
				nameList.add(name.split(" ")[0]);
			}
			int numStats = FileHandler.getStatNames(selTourney).length;
			tableData = new String[teamList.size()][numStats];
			
			for (int i = 0; i < nameList.size(); i++) {
				int[] totalStat = new int[numStats];
				for (int j = 0; j < nameList.size(); j++) {
					if (i != j) {
						String[] statLine = FileHandler.getStatLine(selTourney, 
								nameList.get(i), nameList.get(j));
						for (int k = 0; k < statLine.length; k++) {
							totalStat[k] += Integer.parseInt((statLine[k])); 
						}
					}
				}
				String statLineString = Arrays.toString(totalStat);
				tableData[i] = statLineString.substring(1,
						statLineString.length()-1).split(", ");
				ArrayList<String> statLineArray =  new ArrayList<>(Arrays.asList(tableData[i]));
				statLineArray.add(0, nameList.get(i));
				tableData[i] = statLineArray.toArray(new String[statLineArray.size()]);
			}
			
		} catch (Exception ex) {ex.printStackTrace();}
		return tableData;
	}
	
	private int getTeamNumber() {
		int teamNumber = 0;
		try {
			teamNumber = Integer.parseInt(FileHandler.getTeamNumber(selTourney).charAt(0)+"");
		} catch (Exception ex) {}
		return teamNumber;
	}
	
	public void makeList() {
		try {
			listData = FileHandler.getTourneyList("tourneyList.txt");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not find file.", "File not found", 
					JOptionPane.ERROR_MESSAGE);
		}
		int index = selIndex;
		tourneyList.setListData(listData);
		tourneyList.setSelectedIndex(index);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			this.remove(statScroll);
			//repaint();
			MainFrame.cardLayout.show(MainFrame.container, "homePanel"); 
		}
		
		if (e.getSource() == sortButton) {
			if (statTable.getSelectedColumn() < 1) {
				JOptionPane.showMessageDialog(null, "Please select"
						+ " a statistic column to be sorted.", 
						"Invalid Column", 
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				this.remove(statScroll);
				sortTable();
				repaint();
			}
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		try {
			selTourney = tourneyList.getSelectedValue();
			selIndex = tourneyList.getSelectedIndex();
			tourneyLabel.setText(selTourney);
			this.remove(statScroll);
			makeTable();
			repaint();
		} catch (Exception ex) {}
	}
		
}




