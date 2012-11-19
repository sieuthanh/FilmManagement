/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.view;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import admin.controller.DiskController;

/**
 *
 * @author CodeBlue
 */
public class MyDiskTableModel extends AbstractTableModel {
    
    private String[] columnNames = {"Id","Movie", "Disk Per Movie", "Total"};
    protected Vector data = DiskController.loadDisk();

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.isEmpty() ? "null" : ((Vector) data.get(row)).get(col);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        if (getValueAt(0, c) == null) {
            return String.class;
        }
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }
}
