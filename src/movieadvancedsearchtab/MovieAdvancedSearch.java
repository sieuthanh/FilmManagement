
package movieadvancedsearchtab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import databasemanager.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import appsearch.*;
import moviedisplaypanel.*;
import persondisplaypanel.*;
import companydisplaypanel.*;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import wom.IconManager;

/**
 *
 * @author Admin
 */

public class MovieAdvancedSearch extends JPanel implements ActionListener {
    private JTextField searchbox = new JTextField();
    private JButton searchbt = new JButton("Search");
    private JButton clearbt = new JButton("Clear");
    private Dimension d;
    private String [] y = {"1900","1901","1902","1903","1904","1905","1906","1907","1908","1909","1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010"};
    private String [] r = {"0.0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1.0","1.1","1.2","1.3","1.4","1.5","1.6","1.7","1.8","1.9","2.0","2.1","2.2","2.3","2.4","2.5","2.6","2.7","2.8","2.9","3.0","3.1","3.2","3.3","3.4","3.5","3.6","3.7","3.8","3.9","4.0","4.1","4.2","4.3","4.4","4.5","4.6","4.7","4.8","4.9","5.0","5.1","5.2","5.3","5.4","5.5","5.6","5.7","5.8","5.9","6.0","6.1","6.2","6.3","6.4","6.5","6.6","6.7","6.8","6.9","7.0","7.1","7.2","7.3","7.4","7.5","7.6","7.7","7.8","7.9","8.0","8.1","8.2","8.3","8.4","8.5","8.6","8.7","8.8","8.9","9.0","9.1","9.2","9.3","9.4","9.5","9.6","9.7","9.8","9.9","10.0"};
    private String [] rt = {"10","20","30","40","50","60","70","80","90","100","110","120","130","140","150","160","170","180","190","200","210","220","230","240","250","260","270","280","290","300","310","320","330","340","350","360","370","380","390","400","410","420","430","440","450","460","470","480","490","500"};
    private genres gen = new genres();
    private Fromto Rating = new Fromto("Rating",r);
    private Fromto Year = new Fromto("Year",y);
    private Fromto Runtime = new Fromto("Runtime", rt);
    private countrylist country = new countrylist();
    private Border border = BorderFactory.createBevelBorder(1);

    private LinkedList result = new LinkedList();

    private JLabel result_label = new JLabel("Result");
    private DefaultListModel model = new DefaultListModel();
    private JList resultlist = new JList(model);
    private JScrollPane resultlistScroll;
    private JScrollPane tabScroll;
    private DatabaseManager databaseManager = new DatabaseManager();
    private Insets insets = getInsets();
    //
    private int state = 0;
    private MovieDisplay moviedisplay;
    private PersonDisplay persondisplay;
    private CompanyDisplay companydisplay;
    
    public MovieAdvancedSearch(){
        super();
        setLayout(null);
        setBackground(Color.white);        

        //add searchbox
        this.setPreferredSize(new Dimension(640,490));
        searchbox.setPreferredSize(new Dimension(600,25));
        d=searchbox.getPreferredSize();
        searchbox.setBounds(insets.left + 10,insets.top + 10,d.width,d.height);
        add(searchbox);
        searchbox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) searchbt.doClick();
            }
        });

        //add Genres Panel
        JLabel Genre = new JLabel("Genres");
        Genre.setFont(new Font("Tahoma",Font.BOLD,12));
        d=Genre.getPreferredSize();
        Genre.setBounds(insets.left + 10,insets.top + 45,d.width,d.height);
        this.add(Genre);
        d=gen.getPreferredSize();
        gen.setBounds(insets.left + 10, insets.top + 65, d.width, d.height);
        setwhitebg(gen);
        gen.setBorder(border);
        this.add(gen);

        //add Rating
        d = Rating.getPreferredSize();
        Rating.setBounds(insets.left + 350, insets.top + 170, d.width, d.height);
        this.add(Rating);

        //add Year
        d = Year.getPreferredSize();
        Year.setBounds(insets.left + 10, insets.top + 170, d.width, d.height);
        this.add(Year);
        
        //add Country
        d = country.getPreferredSize();
        country.setBounds(insets.left + 10, insets.top + 200, d.width, d.height);
        this.add(country);

        //add runtime
        d = Runtime.getPreferredSize();
        Runtime.setBounds(insets.left + 350, insets.top + 200, d.width, d.height);
        add(Runtime);

        //add search button
        searchbt.setPreferredSize(new Dimension(210,35));
        d = searchbt.getPreferredSize();        
        searchbt.setBounds(insets.left + 70, insets.top + 250, d.width, d.height);
        searchbt.setIcon(new IconManager("/resources/theme/search.png").getIcon());
        this.add(searchbt);
        searchbt.addActionListener(this);

        //add clear button
        clearbt.setPreferredSize(new Dimension(210,35));
        d = clearbt.getPreferredSize();
        clearbt.setBounds(insets.left + 330, insets.top + 250, d.width, d.height);
        clearbt.setIcon(new IconManager("/resources/theme/clear.png").getIcon());
        this.add(clearbt);
        clearbt.addActionListener(this);

        //add result
        result_label.setFont(new Font("Tahoma", Font.BOLD, 12));
        d = result_label.getPreferredSize();
        result_label.setPreferredSize(new Dimension(600,d.height));
        d = result_label.getPreferredSize();
        result_label.setBounds(insets.left + 10, insets.top + 290, d.width, d.height);
        add(result_label);               
        
        //add this panel to a ScroolPane
        tabScroll = new JScrollPane(this);
        tabScroll.getVerticalScrollBar().setUnitIncrement(20);
        tabScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabScroll.setBorder(null);
        //
        resultlistScroll = new JScrollPane(resultlist);
        resultlistScroll.setPreferredSize(new Dimension(615,170));
        d = resultlistScroll.getPreferredSize();
        resultlistScroll.setBounds(insets.left + 10, insets.top + 310, d.width, d.height);
        add(resultlistScroll);
        //update mouse listener
        updateListener();        
    }

    //getters and setters
    private void setwhitebg(JPanel c){
        Component [] com = c.getComponents();
        for (int i=0;i<com.length;i++){
            com[i].setBackground(Color.white);
        }
    }

    public String getInputString () {
        return searchbox.getText();
    }

    public LinkedList getSelectedGenre(){
        int j=0;
        LinkedList list = new LinkedList();
        Component [] com = gen.getComponents();
        for (int i=0;i<com.length;i++){
            JCheckBox cb=(JCheckBox)com[i];
            if(cb.isSelected()) list.add(cb.getText());
        }
        return list;
    }

    public String getYearFrom() {
        return Year.valuefrom();
    }

    public String getYearTo() {
        return Year.valueto();
    }

    public float getRatingFrom() {
        float f = Float.valueOf(Rating.valuefrom()).floatValue();
        return f;
    }

    public float getRatingTo() {
        float f = Float.valueOf(Rating.valueto()).floatValue();
        return f;
    }

    public String getSelectedCountry() {
        return country.countryselected();
    }

    public JList getResultList () {
        return resultlist;
    }

    public int getRuntimeFrom ()
    {
        int n = Integer.parseInt(Runtime.valuefrom());
        return n;
    }

    public int getRuntimeTo ()
    {
        int n = Integer.parseInt(Runtime.valueto());
        return n;
    }

    public List getResult () {
        return result;
    }

    public JScrollPane getScrollPane() {
        return tabScroll;
    }
    //end getters and setters

    public final void updateListener() {
        switch (state) {
            case 0: addMouseListenerToResultList(); break;
            case 1: addMouseListenerToMovieDisplay(); break;
            case 2: addMouseListenerToPersonDisplay(); break;
            case 3: addMouseListenerToCompanyDisplay(); break;
        }
    }

    public void refresh() {
        switch (state) {            
            case 1: {
                d = moviedisplay.getPreferredSize();
                moviedisplay.setBounds(insets.left + 10, insets.top + 310, d.width, d.height);
                add(moviedisplay);                
                break;
            }
            case 2: {
                d = persondisplay.getPreferredSize();
                persondisplay.setBounds(insets.left + 10, insets.top + 310, d.width, d.height);
                add(persondisplay);                
                break;
            }
            case 3: {
                d = companydisplay.getPreferredSize();
                companydisplay.setBounds(insets.left + 20, insets.top + 310, d.width, d.height);
                add(companydisplay);                
                break;
            }
        }
        setPreferredSize(new Dimension(650, 320 + d.height));
        tabScroll.getVerticalScrollBar().setValue(310);
        repaint();
        revalidate();
    }

    //add mouselistener fucntions
    public void addMouseListenerToResultList() {
        resultlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    JList clicked = (JList)e.getSource();
                    int selected = clicked.getSelectedIndex();
                    state = 1;
                    Movie movie = (Movie)result.get(selected);
                    //remove result display panel
                    remove(resultlistScroll);
                    //
                    moviedisplay = new MovieDisplay(movie.getId());
                    refresh();
                    updateListener();
                    setCursor(null);
                }
            }
        });
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
                    refresh();
                    updateListener();
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
                    refresh();
                    updateListener();
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
                    refresh();
                    updateListener();               
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
                    refresh();
                    updateListener();
                }
            });
        }
    }
    //end add mouselistener functions

    public void tabChanged() {        
        searchbox.setText("");
        gen.clear();
        result_label.setText("Result");
        country.clear();
        Year.clear();
        Rating.clear();
        model.clear();
        switch (state) {
            case 0: return;
            case 1: remove(moviedisplay); break;
            case 2: remove(persondisplay); break;
            case 3: remove(companydisplay); break;
        }
        d = resultlistScroll.getPreferredSize();
        resultlistScroll.setBounds(insets.left+10, insets.top+310, d.width, d.height);
        add(resultlistScroll);
        setPreferredSize(new Dimension(640, 490));
        repaint();
        revalidate();
    }

    public boolean check(Movie movie) {
        if ((movie.getYear() < Integer.parseInt(getYearFrom())) ||
                (movie.getYear() > Integer.parseInt(getYearTo()))) return false;
        if ((movie.getRating() < getRatingFrom()) ||
                (movie.getRating() > getRatingTo())) return false;
        if ((movie.getRuntime() < getRuntimeFrom()) ||
                (movie.getRuntime() > getRuntimeTo())) return false;
        if (!getSelectedCountry().equals("Never mind"))
            if (!movie.getCountry().equals(getSelectedCountry())) return false;
        return true;
    }
    
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Movie movie;
        if (e.getSource()==searchbt){
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            float minrate,maxrate;
            int minyear,maxyear,minruntime,maxruntime;
            minrate = (new Float(Rating.valuefrom())).floatValue();
            maxrate = (new Float(Rating.valueto())).floatValue();
            minyear = (new Integer(Year.valuefrom())).intValue();
            maxyear = (new Integer(Year.valueto())).intValue();
            minruntime = (new Integer(Runtime.valuefrom())).intValue();
            maxruntime = (new Integer(Runtime.valueto())).intValue();
            if (minrate>maxrate || minyear>maxyear || minruntime > maxruntime)
            {
                JOptionPane.showMessageDialog(this,"Input Error !" ,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            else {
                model.clear();                
                switch (state) {
                    case 1: remove(moviedisplay); break;
                    case 2: remove(persondisplay); break;
                    case 3: remove(companydisplay); break;
                }
                d = resultlistScroll.getPreferredSize();
                resultlistScroll.setBounds(insets.left+10, insets.top+310, d.width, d.height);
                add(resultlistScroll);
                setPreferredSize(new Dimension(650, 490));
                repaint();
                revalidate();
                result = databaseManager.AdvanceMovieSearch(getInputString(), getSelectedGenre(), getYearFrom(), getYearTo(), getRatingFrom(), getRatingTo(),getSelectedCountry(),getRuntimeFrom(),getRuntimeTo());                
                String input = databaseManager.getSearchCommand(getInputString());
                if (result.size() != 0)
                    result_label.setText("Result (" + result.size() + ")");
                if (result.size() == 0 && input.length() != 0) {
                    result = databaseManager.getMovieList();
                    int i = 0;
                    while (i < result.size()) {
                        movie = (Movie)result.get(i);
                        String temp = movie.getName().toLowerCase();
                        int x = AppSearch.commonChar(temp, input);
                        movie.setCommonChar(x);
                        if (!check(movie)) result.remove(i);
                        else
                            if ((float)x / input.length() < 0.7) result.remove(i);
                            else i ++;
                    }
                    for (i = 0; i < result.size(); i ++)
                        for (int j = i + 1; j < result.size(); j ++) {
                            Movie moviei = (Movie)result.get(i);
                            Movie moviej = (Movie)result.get(j);
                            if (moviei.getCommonChar() < moviej.getCommonChar()) {
                                //System.out.println(moviei.getCommonChar() + " " + moviej.getCommonChar());
                                Movie temp = moviei;
                                result.set(i, moviej);
                                result.set(j, temp);
                            }
                        }
                    if (input.length()>30) input = input.substring(0,26) + "...";
                    result_label.setText("<html>Your search - <font color = red>" + input + "</font> - did not match any movie in database. Suggestions:</html>");
                }            
                for (int i=0; i < result.size(); i++) {
                    movie = (Movie)result.get(i);
                    model.add(i, movie.getName());
                }                
            }
            setCursor(null);
        }
        if (e.getSource()==clearbt) {
            searchbox.setText("");
            gen.clear();
            result_label.setText("Result");
            country.clear();
            Year.clear();
            Rating.clear();
            Runtime.clear();
            model.clear();
            switch (state) {
                case 1: remove(moviedisplay); break;
                case 2: remove(persondisplay); break;
                case 3: remove(companydisplay); break;
            }            
            d = resultlistScroll.getPreferredSize();
            resultlistScroll.setBounds(insets.left+10, insets.top+310, d.width, d.height);
            add(resultlistScroll);
            setPreferredSize(new Dimension(650, 490));
            repaint();
            revalidate();
        }
    }
}
