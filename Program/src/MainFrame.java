import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class MainFrame extends CentredFrame {
	public static final int FRAME_WIDTH = 900;
	public static final int FRAME_HEIGHT = 600;
	public static Container container;
	public static CardLayout cardLayout;
	
	public static HomePanel homePanel;
	public static EditPanel editPanel;
	public static StatsPanel statsPanel;
	
	//Constructor
	public MainFrame() throws IOException {
		container = getContentPane();
		cardLayout = new CardLayout();
		container.setLayout(cardLayout);
		
		homePanel = new HomePanel();
		editPanel = new EditPanel();
		statsPanel = new StatsPanel();
		
		container.add("homePanel", homePanel);
		container.add("editPanel", editPanel);
		container.add("statsPanel", statsPanel);
		
		setTitle("TourneyMaker");
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
