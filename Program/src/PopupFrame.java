import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class PopupFrame extends CentredFrame {
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 400;
	public static Container container;
	public static CardLayout cardLayout;
	
	public static CreatePanel createPanel;
	public static AddTeamPanel addTeamPanel;
	public static EditMatchPanel editMatchPanel;
	
	//Constructor
	public PopupFrame() throws IOException {
		container = getContentPane();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		
		createPanel = new CreatePanel();
		addTeamPanel = new AddTeamPanel();
		
		container.add("createPanel", createPanel);
		container.add("addTeamPanel", addTeamPanel);
		
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public PopupFrame(String team1, String team2) throws IOException {
		container = getContentPane();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		
		editMatchPanel = new EditMatchPanel(team1, team2);

		container.add("editMatchPanel", editMatchPanel);
		
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
