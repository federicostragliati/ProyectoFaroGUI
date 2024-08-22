package Model;

import javax.swing.table.DefaultTableModel;

public class CustomTableModelCliente extends DefaultTableModel {

    public CustomTableModelCliente (Object[][] data, String[] columnNames) {super(data, columnNames);}

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: // ID column
                return Integer.class;
            default:
                return Object.class;
        }
    }


}
