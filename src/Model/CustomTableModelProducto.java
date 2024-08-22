package Model;

import javax.swing.table.DefaultTableModel;

public class CustomTableModelProducto extends DefaultTableModel {
    public CustomTableModelProducto(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: // ID column
                return Integer.class;
            case 2: // Cantidad column
                return Double.class;
            case 3: // Precio Unitario column
                return Double.class;
            default:
                return Object.class;
        }
    }
}
