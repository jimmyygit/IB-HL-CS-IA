import javax.swing.JTable; 
 
public class StatTable extends JTable {

	StatTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
	
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}