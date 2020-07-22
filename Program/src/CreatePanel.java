import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreatePanel extends PopupPanel implements ActionListener {
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JLabel sizeLabel;
	private JLabel statsLabel;
	private JTextField nameField;
	private JTextArea statsField;
	private JScrollPane statsScroll;
	private JRadioButton robinButton;
	private JRadioButton playoffButton;
	private JRadioButton size4Button;
	private JRadioButton size8Button;
	private ButtonGroup typeGroup;
	private ButtonGroup sizeGroup;
	private JButton cancelButton;
	private JButton createButton;
	private String name;
	private boolean isRobin;
	private boolean isSize4;
	private String[] stats;
	
	public CreatePanel() {
		nameLabel = new JLabel("Name");
		typeLabel = new JLabel("Type");
		sizeLabel = new JLabel("Size");
		statsLabel = new JLabel("Stats");
		nameField = new JTextField();
		statsField = new JTextArea("Score");
		statsScroll = new JScrollPane();
		robinButton = new JRadioButton("Round Robin", true);
		playoffButton = new JRadioButton("Playoff");
		size4Button = new JRadioButton("4", true);
		size8Button = new JRadioButton("8");
		typeGroup = new ButtonGroup();
		sizeGroup = new ButtonGroup();
		cancelButton = new JButton("Cancel");
		createButton = new JButton("Create");
		isRobin = true;
		isSize4 = true;
		
		this.add(nameLabel);
		this.add(typeLabel);
		this.add(statsLabel);
		this.add(nameField);
		this.add(statsField);
		this.add(statsScroll);
		this.add(robinButton);
		this.add(playoffButton);
		this.add(cancelButton);
		this.add(createButton);
		
		robinButton.addActionListener(this);
		playoffButton.addActionListener(this);
		size4Button.addActionListener(this);
		size8Button.addActionListener(this);
		cancelButton.addActionListener(this);
		createButton.addActionListener(this);
		
		initialize();
	}
	
	public void initialize() {
		nameLabel.setFont(DEFAULT_FONT);
		typeLabel.setFont(DEFAULT_FONT);
		sizeLabel.setFont(DEFAULT_FONT);
		statsLabel.setFont(DEFAULT_FONT);
		
		nameLabel.setBounds(LEFT_MARGIN_X, 50, LABEL_SIZE_X, LABEL_SIZE_Y);
		typeLabel.setBounds(LEFT_MARGIN_X, 100, LABEL_SIZE_X, LABEL_SIZE_Y);
		sizeLabel.setBounds(LEFT_MARGIN_X, 150, LABEL_SIZE_X, LABEL_SIZE_Y);
		statsLabel.setBounds(LEFT_MARGIN_X, 200, LABEL_SIZE_X, LABEL_SIZE_Y);
		
		nameField.setBounds(TEXT_FIELD_MARGIN_X, 50, 300, 30);
		nameField.setBorder(new LineBorder(Color.BLACK, 1));
		statsField.setBounds(TEXT_FIELD_MARGIN_X, 200, 300, 100);
		statsField.setBorder(new LineBorder(Color.BLACK, 1));
		statsScroll.setBounds(TEXT_FIELD_MARGIN_X, 200, 300, 100);
		statsScroll.getViewport().add(statsField);
		statsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		statsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		robinButton.setBounds(100, 100, 100, 30);
		playoffButton.setBounds(200, 100, 100, 30);
		size4Button.setBounds(100, 150, 100, 30);
		size8Button.setBounds(200, 150, 100, 30);
		robinButton.setBackground(ORANGE);
		playoffButton.setBackground(ORANGE);
		size4Button.setBackground(ORANGE);
		size8Button.setBackground(ORANGE);
		typeGroup.add(robinButton);
		typeGroup.add(playoffButton);
		sizeGroup.add(size4Button);
		sizeGroup.add(size8Button);
		
		cancelButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		createButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == robinButton) {
			isRobin = true;
			this.remove(sizeLabel);
			this.remove(size4Button);
			this.remove(size8Button);
			repaint();
		}
		
		if (e.getSource() == playoffButton) {
			isRobin = false;
			this.add(sizeLabel);
			this.add(size4Button);
			this.add(size8Button);
			repaint();
		}
		
		if (e.getSource() == size4Button) {
			isSize4 = true;
		}
		
		if (e.getSource() == size8Button) {
			isSize4 = false;
		}
		
		if (e.getSource() == cancelButton) {
			((Window) getRootPane().getParent()).dispose();
		}
		
		if (e.getSource() == createButton) {
			try {
				name = nameField.getText();
				stats = statsField.getText().split("\\n");
				if (FileHandler.addTourney("tourneyList.txt", name, stats)) {
					FileHandler.addTourneyFile(name, isRobin, isSize4, stats);
					JOptionPane.showMessageDialog(null, "Successfully created new tournment.", 
							"Completed Successfully", 
							JOptionPane.INFORMATION_MESSAGE);
					((Window) getRootPane().getParent()).dispose();
					MainFrame.cardLayout.show(MainFrame.container, "editPanel");
					MainFrame.editPanel.initialize();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Could not find file.", "File not found", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
