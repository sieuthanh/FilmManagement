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

import statistics.StatisticsPanel;

import admin.controller.FilmController;


public class StatisticsControl extends JPanel {

    private JXCollapsiblePane collapsiblePane; //swingX
    private JButton collapsingButton; // for toogle panels
    
    public StatisticsControl() {
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
        StatisticsPanel sp = new StatisticsPanel();
        add(sp, BorderLayout.CENTER);
        
        //AdminMovieList2 admin = new AdminMovieList2();
        // fill collapsible panel with CompanyAndDisk panel
        //add(admin, BorderLayout.CENTER);
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