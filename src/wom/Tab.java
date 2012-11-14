package wom;

import javax.swing.event.ChangeEvent;
import personadvancedsearchtab.*;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import movielisttab.*;
import movieadvancedsearchtab.*;
import hometab.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin
 */
public class Tab extends JTabbedPane {
    private Icon home;
    private Icon movielist;
    private Icon titlesearch;
    private Icon personsearch;
    
    private HomeTab hometab = new HomeTab();
    private ListTab listtab = new ListTab();
    private MovieAdvancedSearch movietab = new MovieAdvancedSearch();
    private PersonAdvancedSearch persontab = new PersonAdvancedSearch();
    private String name = new GetTheme().Theme();
    
    public Tab() {
        super();
        setFocusable(false);
        home = new IconManager("/resources/theme/" + name + "/home.png").getIcon();
        movielist = new IconManager("/resources/theme/" + name + "/movie-list.png").getIcon();
        titlesearch = new IconManager("/resources/theme/" + name + "/title-search.png").getIcon();
        personsearch = new IconManager("/resources/theme/" + name + "/person-search.png").getIcon();
        addTab("", home, hometab.getScrollPane());
        addTab("", movielist, listtab.getScrollPane());
        addTab("", titlesearch, movietab.getScrollPane());
        addTab("", personsearch, persontab.getScrollPane());
        setTabPlacement(JTabbedPane.LEFT);        
        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {                
                switch (getSelectedIndex()) {
                    case 0: {
                        hometab.tabChanged();
                        break;
                    }
                    case 1: {
                        listtab.tabChanged();
                        break;
                    }
                    case 2: {
                        movietab.tabChanged();
                        break;
                    }
                    case 3: {
                        persontab.tabChanged();
                        break;
                    }
                }
            }            
        });
    }

    public HomeTab getHometab() {
		return hometab;
	}

	public void setHometab(HomeTab hometab) {
		this.hometab = hometab;
	}

	public void changeTheme(){
        String newtheme = new GetTheme().Theme();
        home = new IconManager("/theme/" + newtheme + "/home.png").getIcon();
        movielist = new IconManager("/theme/" + newtheme + "/movie-list.png").getIcon();
        titlesearch = new IconManager("/theme/" + newtheme + "/title-search.png").getIcon();
        personsearch = new IconManager("/theme/" + newtheme + "/person-search.png").getIcon();
        setIconAt(0, home);
        setIconAt(1, movielist);
        setIconAt(2, titlesearch);
        setIconAt(3, personsearch);
    }
}
