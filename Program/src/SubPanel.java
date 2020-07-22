import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class SubPanel extends JPanel {
	public static final int BUTTON_SIZE_X = 100;
	public static final int BUTTON_SIZE_Y = 30;
	
	public static final int LABEL_SIZE_X = 100;
	public static final int LABEL_SIZE_Y = 30;
	
	public static final int BUTTON_SLOT_1_X = 40;
	public static final int BUTTON_SLOT_4_X = 460;
	
	public static final int LOWER_BED_Y = 350;
	
	public static final int LEFT_MARGIN_X = 20;
	
	public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 15);
	public static final Color ORANGE = new Color(247, 193, 99);
	
	public SubPanel() {
		this.setLayout(null);
	    this.setBackground(ORANGE);
	    this.setBorder(new LineBorder(Color.BLACK, 1));
	}
}
