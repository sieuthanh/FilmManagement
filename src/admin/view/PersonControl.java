package admin.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import admin.controller.FilmController;


public class PersonControl extends JPanel {

    private JXCollapsiblePane collapsiblePane; //swingX
    
    public PersonControl() {
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
        AdminPersonList admin = new AdminPersonList();
        add(admin, BorderLayout.CENTER);
        //admin.updatePaging();
        // create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        // add it to the bottom
        add(buttonPanel, BorderLayout.SOUTH);
    }
  
    // set event on click the collapse button (to toogle the collapsible panel)
    private void bind() {
    }
}