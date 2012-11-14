/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package resultdisplayhandle;

import java.util.List;
import javax.swing.JPanel;
import moviedisplaypanel.*;
import persondisplaypanel.*;
import companydisplaypanel.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Administrator
 */
public class ResultDisplay {
    private int state;
    private MovieDisplay moviedisplay;
    private PersonDisplay persondisplay;
    private CompanyDisplay companydisplay;
    private String id;
    
    public ResultDisplay(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state, String id) {
        this.state = state;
        switch (state) {
            case 1: {
                moviedisplay = new MovieDisplay(id);
                addMouseListenerToMovieDisplay();
                break;
            }
            case 2: {                
                persondisplay = new PersonDisplay(id);
                addMouseListenerToPersonDisplay();
                break;
            }
            case 3: {
                companydisplay = new CompanyDisplay(id);
                addMouseListenerToCompanyDisplay();
                break;
            }
        }
    }

    public MovieDisplay getMovieDisplay() {
        return moviedisplay;
    }

    public PersonDisplay getPersonDisplay() {
        return persondisplay;
    }

    public CompanyDisplay getCompanyDisplay() {
        return companydisplay;
    }

    public void addMouseListenerToMovieDisplay() {
        List personlist;
        LinkedLabel x;
        //add mouselistener to personlist
        personlist = moviedisplay.getListPersonLabel();
        for (int i = 0; i < personlist.size(); i ++) {
            x = (LinkedLabel)personlist.get(i);
            id = x.getId();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setState(2, id);
                }
            });
        }
        //add mouselistener to company label
        List companylist = moviedisplay.getListCompanyLabel();
        for (int i = 0; i < companylist.size(); i ++) {
            x = (LinkedLabel)companylist.get(i);
            id = x.getId();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setState(3, id);
                    
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
            id = x.getID();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setState(1, id);
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
            id = x.getID();
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setState(1, id);
                }
            });
        }
    }
}
