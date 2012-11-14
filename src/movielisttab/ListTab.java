//
package movielisttab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import moviedisplaypanel.*;
import persondisplaypanel.*;
import companydisplaypanel.*;
import java.awt.Cursor;
import javax.swing.JScrollBar;

/**
 *
 * @author Fight For Fun Group
 */
public class ListTab extends JPanel implements ActionListener {
    private MovieList list = new MovieList();
    private BackToList backtolist = new BackToList();
    private Insets insets = getInsets();
    private JScrollPane scrollpane;
    private int state = 0; //0: list 1:movie 2:person/director 3: company

    private MovieDisplay moviedisplay;
    private PersonDisplay persondisplay;
    private CompanyDisplay companydisplay;

    public ListTab() {
        super();
        setBackground(Color.white);
        Dimension d;
        setLayout(null);
        setPreferredSize(new Dimension(650, 490));
        //add movie list
        d = list.getPreferredSize();
        list.setBounds(insets.left, insets.top + 40, d.width, d.height);
        add(list);
        //add back to list link
        d = backtolist.getPreferredSize();
        backtolist.setBounds(insets.left + 20, insets.top, d.width, d.height);
        add(backtolist);
        //add listener to table
        updateListener();
        //add actionlistener to backtolist button
        backtolist.addActionListener(this);
        //add current jpanel to scrollpane
        scrollpane = new JScrollPane(this);
        scrollpane.getVerticalScrollBar().setUnitIncrement(20);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.setBorder(null);
    }

    public JScrollPane getScrollPane() {//return the scrollpane
        return scrollpane;
    }

    public final void addMouseListenerToList() {//add listener to table
        list.getTable().addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !backtolist.isActive()) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    JTable clicked = (JTable)e.getSource();
                    int row = clicked.getSelectedRow();
                    String id = (String)clicked.getModel().getValueAt(row, 5);

                    //remove current movie list
                    remove(list);

                    //show movie display panel and also locate it
                    state = 1;
                    moviedisplay = new MovieDisplay(id);
                    Dimension d = moviedisplay.getPreferredSize();
                    moviedisplay.setBounds(insets.left + 20, insets.top + 50, d.width, d.height);
                    setPreferredSize(new Dimension(650, d.height + 50));
                    add(moviedisplay);

                    //activate backtolist button
                    backtolist.setActive(true);
                    //update listener
                    updateListener();
                    repaint();
                    setCursor(null);
                }
            }
        });
    }

    void refresh() {//restore current panel after state change happens
        JScrollBar x = scrollpane.getVerticalScrollBar();
        x.setValue(x.getMinimum());
        updateListener();
        revalidate();
        repaint();
    }

    public void addMouseListenerToMovieDisplay() {
        List personlist;
        LinkedLabel x;
        //add mouselistener to personlist
        personlist = moviedisplay.getListPersonLabel();
        for (int i = 0; i < personlist.size(); i ++) {
            x = (LinkedLabel)personlist.get(i);
            final String id = x.getId();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {                    
                    persondisplay = new PersonDisplay(id);
                    state = 2;
                    remove(moviedisplay);
                    Dimension d = persondisplay.getPreferredSize();
                    persondisplay.setBounds(insets.left + 20, insets.top + 50, d.width, d.height);
                    add(persondisplay);
                    setPreferredSize(new Dimension(650, d.height + 50));
                    refresh();                    
                }
            });
        }
        //add mouselistener to company label
        List companylist = moviedisplay.getListCompanyLabel();
        for (int i = 0; i < companylist.size(); i ++) {
            x = (LinkedLabel)companylist.get(i);
            final String id = x.getId();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    companydisplay = new CompanyDisplay(id);
                    state = 3;
                    remove(moviedisplay);
                    Dimension d = companydisplay.getPreferredSize();
                    companydisplay.setBounds(insets.left + 20, insets.top + 50, d.width, d.height);
                    add(companydisplay);
                    setPreferredSize(new Dimension(650, d.height + 50));
                    refresh();
                }
            });
        }
    }

    public void addMouseListenerToPersonDisplay() {
        List movielist;
        persondisplaypanel.MovieNameLabel x;
        movielist = persondisplay.getNameLabelList();
        for (int i = 0; i < movielist.size(); i ++) {
            x = (persondisplaypanel.MovieNameLabel)movielist.get(i);
            final String id = x.getID();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    moviedisplay = new MovieDisplay(id);
                    state = 1;
                    remove(persondisplay);
                    Dimension d = moviedisplay.getPreferredSize();
                    moviedisplay.setBounds(insets.left + 20, insets.top + 50, d.width, d.height);
                    add(moviedisplay);
                    setPreferredSize(new Dimension(650, d.height + 50));
                    refresh();
                }
            });
        }
    }

    public void addMouseListenerToCompanyDisplay() {
        List movielist;
        companydisplaypanel.MovieNameLabel x;
        movielist = companydisplay.getNameLabelList();
        for (int i = 0; i < movielist.size(); i ++) {
            x = (companydisplaypanel.MovieNameLabel)movielist.get(i);
            final String id = x.getID();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    moviedisplay = new MovieDisplay(id);
                    state = 1;
                    remove(companydisplay);
                    Dimension d = moviedisplay.getPreferredSize();
                    moviedisplay.setBounds(insets.left + 20, insets.top + 50, d.width, d.height);
                    add(moviedisplay);
                    setPreferredSize(new Dimension(650, d.height + 50));
                    refresh();
                }
            });
        }
    }

    public final void updateListener() {
        switch (state) {
            case 0: addMouseListenerToList(); break;
            case 1: addMouseListenerToMovieDisplay(); break;
            case 2: addMouseListenerToPersonDisplay(); break;
            case 3: addMouseListenerToCompanyDisplay(); break;
        }
    }

    public void tabChanged() {
        switch (this.state) {
            case 0: remove(list); break;
            case 1: remove(moviedisplay); break;
            case 2: remove(persondisplay); break;
            case 3: remove(companydisplay); break;
        }
        state = 0;
        list = new MovieList();
        Dimension d = list.getPreferredSize();
        list.setBounds(insets.left, insets.top + 40, d.width, d.height);
        add(list);
        setPreferredSize(new Dimension(650, 490));
        //update actionlistener
        backtolist.setActive(false);        
        revalidate();
        repaint();
        updateListener();
    }

    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (e.getSource() == backtolist) {
            switch (state) {//remove movie or person display
                case 1: remove(moviedisplay); break;
                case 2: remove(persondisplay); break;
                case 3: remove(companydisplay); break;
            }
            //update current state
            state = 0;
            //add new movielist and locate
            Dimension d = list.getPreferredSize();
            list.setBounds(insets.left, insets.top + 40, d.width, d.height);
            add(list);
            setPreferredSize(new Dimension(650, 490));
            //update actionlistener
            updateListener();
            backtolist.setActive(false);
            revalidate();
            repaint();
        }
    }
}
