import javax.swing.*;
import java.awt.*;

public class PopupPanel extends JPanel {
	public static final int BUTTON_SIZE_X = 100;
	public static final int BUTTON_SIZE_Y = 30;
	
	public static final int LABEL_SIZE_X = 100;
	public static final int LABEL_SIZE_Y = 30;
	
	public static final int UPPER_BED_Y = 20;
	public static final int LOWER_BED_Y = 320;
	
	public static final int BUTTON_SLOT_1_X = 40;
	public static final int BUTTON_SLOT_2_X = 180;
	public static final int BUTTON_SLOT_3_X = 320;
	public static final int BUTTON_SLOT_4_X = 460;
	
	public static final int LEFT_MARGIN_X = 40;
	public static final int TEXT_FIELD_MARGIN_X = 100;
	
	public static final int STAT_LEFT_MARGIN_X = 140;
	public static final int STAT_MIDDLE_MARGIN_X = 250;
	public static final int STAT_RIGHT_MARGIN_X = 360;
	
	public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 20);
	public static final Color ORANGE = new Color(255, 153, 51);
	
	public PopupPanel() {
		this.setLayout(null);
	    this.setBackground(ORANGE);
	}
}
