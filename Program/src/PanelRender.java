import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class PanelRender extends DefaultListCellRenderer 
	implements ListCellRenderer<Object>{

	@Override
	public Component getListCellRendererComponent(JList list, 
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		MatchPane render = (MatchPane) value;
		
		if (isSelected) {
			render.setBackground(new Color(227, 166, 25));
		} else {
			render.setBackground(new Color(252, 186, 3));
		}
		
		setEnabled(true);
		return render;
	}
	
}
