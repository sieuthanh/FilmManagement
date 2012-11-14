/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author CodeBlue
 */
public class MyTableRenderer {

    public static class IDRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setHorizontalAlignment(SwingConstants.CENTER);
            setForeground(new Color(153, 0, 0));
            super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            return this;
        }
    }

    // convert salary to currency format
    public static class CurrencyRenderer extends DefaultTableCellRenderer {

        public CurrencyRenderer() {
            super();
            setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        }

        @Override
        public void setValue(Object value) {
            if ((value != null) && (value instanceof Number)) {
                Number numberValue = (Number) value;
                NumberFormat formater = NumberFormat.getCurrencyInstance();
                value = formater.format(numberValue.doubleValue());
            }
            super.setValue(value);
        }
    }

    // center column
    public static class ExtraRenderer extends DefaultTableCellRenderer {

        public ExtraRenderer() {
            super();
            setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
    }
    // custom reward column

    public static class RewardRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel pn = new JPanel(new BorderLayout());
            JLabel lb = new JXLabel();
            pn.add(lb, BorderLayout.CENTER);
            lb.setHorizontalAlignment(SwingConstants.CENTER);
            lb.setText(value.toString());
            lb.setForeground(new Color(0, 0, 102));
            if (value.toString().equals("Highest Earn")) {
                pn.setBackground(new Color(204, 0, 0));

            } else {
                pn.setBackground(new Color(102, 204, 0));
            }
            super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            return pn;
        }
    }
    
    // turn the column foreground in blue
    public static class ColorRenderer extends DefaultTableCellRenderer {

        public ColorRenderer() {
            super();
            setForeground(Color.blue);
            //setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
    }
}
