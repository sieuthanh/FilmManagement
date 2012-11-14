/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.view;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 *
 * @author CodeBlue
 */
// this class is for purpose set the row just inserted visible in table view port
public class MyTableScroller extends ComponentAdapter {

    JTable table;

    public MyTableScroller(JTable table) {
        this.table = table;
    }

    @Override
    public void componentResized(ComponentEvent event) {
        int lastRow = table.getRowCount() - 1;
        // get the coordiate y of last cell of first column
        int cellTop = table.getCellRect(lastRow, 0, true).y;
        // get table scrollpane
        JScrollPane jsp = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, table);
        JViewport jvp = jsp.getViewport();
        // get the height of view port
        int portHeight = jvp.getSize().height;
        int position = cellTop - (portHeight - table.getRowHeight() - table.getRowMargin());
        if (position >= 0) {
            // set the given point to the upper left hand corner of viewport
            // so the last row which has just been inserted appears at the bottom of viewport
            jvp.setViewPosition(new Point(0, position));
        }
    }
}
