import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
	public static final int BUTTON_SIZE_X = 200;
	public static final int BUTTON_SIZE_Y = 50;
	
	public static final int LOWER_BED_Y = 500;
	
	public static final int BUTTON_SLOT_1_X = 20;
	public static final int BUTTON_SLOT_2_X = 240;
	public static final int BUTTON_SLOT_3_X = 460;
	public static final int BUTTON_SLOT_4_X = 680;
	
	public static final int LEFT_MARGIN_X = 20;
	
	public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 25);
	public static final Color ORANGE = new Color(255, 153, 51);
	
	public MainPanel() {
		this.setLayout(null);
	    this.setBackground(ORANGE);
	}
}
