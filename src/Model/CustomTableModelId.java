package Model;

import javax.swing.table.DefaultTableModel;

public class CustomTableModelId extends DefaultTableModel {

    public CustomTableModelId(Object[][] data, String[] columnNames) {super(data, columnNames);}

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: // ID column
                return Integer.class;
            default:
                return Object.class;
        }
    }


}
