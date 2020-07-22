import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AddTeamPanel extends PopupPanel implements ActionListener{
	private JLabel nameLabel;
	private JLabel imageLabel;
	private JTextField nameField;
	private JFileChooser imageChooser;
	private String imagePath;
	private JLabel imageIcon;
	private JButton imageButton;
	private JButton cancelButton;
	private JButton addButton;
	
	public AddTeamPanel() {
		nameLabel = new JLabel("Name");
		imageLabel = new JLabel("Image");
		nameField = new JTextField();
		imageChooser = new JFileChooser();
		imageIcon = new JLabel(new ImageIcon("defaultTeam.png"));
		imageButton = new JButton("Browse");
		cancelButton = new JButton("Cancel");
		addButton = new JButton("Add");
		
		this.add(nameLabel);
		this.add(imageLabel);
		this.add(nameField);
		this.add(imageChooser);
		this.add(imageIcon);
		this.add(imageButton);
		this.add(cancelButton);
		this.add(addButton);
		
		imageButton.addActionListener(this);
		cancelButton.addActionListener(this);
		addButton.addActionListener(this);
		
		initialize();
	}
	
	public void initialize() {
		nameLabel.setFont(DEFAULT_FONT);
		imageLabel.setFont(DEFAULT_FONT);
		nameLabel.setBounds(LEFT_MARGIN_X, 50, LABEL_SIZE_X, LABEL_SIZE_Y);
		imageLabel.setBounds(LEFT_MARGIN_X, 100, LABEL_SIZE_X, LABEL_SIZE_Y);
		nameField.setBounds(TEXT_FIELD_MARGIN_X, 50, 300, 30);
		nameField.setBorder(new LineBorder(Color.BLACK, 1));
	
		imageIcon.setBounds(100, 150, 50, 50);
		
		imageButton.setBounds(100, 100, 100, 30);
		cancelButton.setBounds(BUTTON_SLOT_1_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
		addButton.setBounds(BUTTON_SLOT_4_X, LOWER_BED_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
	}
	
	
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == imageButton) {
			int returnVal = imageChooser.showOpenDialog(imageChooser);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				imagePath = imageChooser.getSelectedFile().getPath();
			}
			ImageIcon icon = new ImageIcon(imagePath);
			Image smallIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			imageIcon.setIcon(new ImageIcon(smallIcon));
				
		}
		
		if (e.getSource() == cancelButton) {
			((Window) getRootPane().getParent()).dispose();
		}
		
		if (e.getSource() == addButton) {
			try {
				String teamName = nameField.getText();
				if (FileHandler.addTeam(EditPanel.selTourney, teamName, imagePath)) {
					JOptionPane.showMessageDialog(null, "Successfully added team.", 
							"Completed Successfully", 
							JOptionPane.INFORMATION_MESSAGE);
					FileHandler.updateMatchList(EditPanel.selTourney);
					((Window) getRootPane().getParent()).dispose();
					MainFrame.editPanel.initialize();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Could not find file.", "File not found", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
