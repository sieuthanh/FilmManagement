package admin.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import admin.controller.FilmController;


public class AdminControl extends JPanel {

    private JXCollapsiblePane collapsiblePane; //swingX
    private JButton collapsingButton; // for toogle panels
    
    public AdminControl() {
            creat();
            bind();
    }

    // create
    private void creat() {
        setLayout(new BorderLayout());

        collapsiblePane = new JXCollapsiblePane();
        collapsiblePane.setAnimated(true);
        collapsiblePane.setCollapsed(true);
        // add collapsible panel to the top
        add(collapsiblePane, BorderLayout.NORTH);
        collapsiblePane.setLayout(new AbsoluteLayout());
        AdminMovieList2 admin = new AdminMovieList2();
        CompanyAndDisk cad = new CompanyAndDisk();
        // fill collapsible panel with CompanyAndDisk panel
        collapsiblePane.add(cad, new AbsoluteConstraints(0, 0, 760, 410));
        add(admin, BorderLayout.CENTER);
        //admin.updatePaging();
        // create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        // add it to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
        collapsingButton = new JButton();
        collapsingButton.setText("» MovieList/CompanyAndDisk Table «");
        collapsingButton.setForeground(Color.BLUE);
        collapsingButton.setContentAreaFilled(false);
        collapsingButton.setFocusable(false);
        collapsingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(collapsingButton);
    }
  
    // set event on click the collapse button (to toogle the collapsible panel)
    private void bind() {
    	collapsingButton.addActionListener(collapsiblePane.getActionMap().get(
                JXCollapsiblePane.TOGGLE_ACTION));
    }
}