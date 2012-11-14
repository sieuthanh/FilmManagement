package personadvancedsearchtab;

import appsearch.AppSearch;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import movieadvancedsearchtab.*;
import databasemanager.*;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import moviedisplaypanel.*;
import persondisplaypanel.*;
import companydisplaypanel.*;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import wom.IconManager;

/**
 *
 * @author uNstOppAbLe
 */
public class PersonAdvancedSearch extends JPanel implements ActionListener {
    private JScrollPane resultlistScroll = new JScrollPane();
    private JLabel gender_label = new JLabel("Gender");
    private JLabel result_label = new JLabel("Result");
    private JRadioButton nevermind = new JRadioButton("Nevermind");
    private JRadioButton male = new JRadioButton("Male");
    private JRadioButton female = new JRadioButton("Female");
    private ButtonGroup group = new ButtonGroup();
    private JLabel roleinmovie = new JLabel("Role in movie");
    private JCheckBox isdirector = new JCheckBox("Director");
    private JCheckBox isstar = new JCheckBox("Star");

    private JTextField searchbox = new JTextField();
    private JButton search_button = new JButton("Search");
    private JButton clear_button = new JButton("Clear");
    private Dimension d = new Dimension();
    private String year_value[] = {"1850","1851","1852","1853","1854","1855","1856","1857","1858","1859","1860","1861","1862","1863","1864","1865","1866","1867","1868","1869","1870","1871","1872","1873","1874","1875","1876","1877","1878","1879","1880","1881","1882","1883","1884","1885","1886","1887","1888","1889","1890","1891","1892","1893","1894","1895","1896","1897","1898","1899","1900","1901","1902","1903","1904","1905","1906","1907","1908","1909","1910","1911","1912","1913","1914","1915","1916","1917","1918","1919","1920","1921","1922","1923","1924","1925","1926","1927","1928","1929","1930","1931","1932","1933","1934","1935","1936","1937","1938","1939","1940","1941","1942","1943","1944","1945","1946","1947","1948","1949","1950","1951","1952","1953","1954","1955","1956","1957","1958","1959","1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006"};
    private Fromto birthday = new Fromto("Birthday",year_value);

    private LinkedList result = new LinkedList();

    private DefaultListModel model = new DefaultListModel();
    private JList resultlist = new JList(model);

    private Person person;

    private DatabaseManager databaseManager = new DatabaseManager();

    //Scrollpane for the whole tab
    private JScrollPane tabScroll;
    //
    private int state = 0;
    private MovieDisplay moviedisplay;
    private PersonDisplay persondisplay;
    private CompanyDisplay companydisplay;
    private Insets insets = getInsets();
    
    public PersonAdvancedSearch () {
        super();
        setLayout(null);
        
        setPreferredSize(new Dimension(650,490));

        setBackground(Color.white);

        //Search box
        searchbox.setPreferredSize(new Dimension(625,25));
        d = searchbox.getPreferredSize();
        searchbox.setBounds(insets.left+10, insets.top+10, d.width, d.height);
        add(searchbox);
        searchbox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) search_button.doClick();
            }
        });

        //Gender
        gender_label.setFont(new Font("Tahoma", Font.BOLD, 12));
        d = gender_label.getPreferredSize();
        gender_label.setBounds(insets.left + 10, insets.top + 50, d.width, d.height);
        add(gender_label);
        d = nevermind.getPreferredSize();
        nevermind.setBounds(insets.left + 60, insets.top + 46, d.width, d.height);
        nevermind.setBackground(Color.white);
        add(nevermind);
        d = male.getPreferredSize();
        male.setBounds(insets.left + 140, insets.top + 46, d.width, d.height);
        male.setBackground(Color.white);
        add(male);
        d = female.getPreferredSize();
        female.setBounds(insets.left + 210, insets.top + 46, d.width, d.height);
        female.setBackground(Color.white);
        add(female);
        group.add(male);
        group.add(female);
        group.add(nevermind);
        nevermind.setSelected(true);

        //Year
        d = birthday.getPreferredSize();
        birthday.setBounds(insets.left + 350, insets.top + 42, d.width, d.height);
        add(birthday);

        //Add role in movie
        roleinmovie.setFont(new Font("Tahoma", Font.BOLD, 12));
        d = roleinmovie.getPreferredSize();
        roleinmovie.setBounds(insets.left + 10, insets.top + 75, d.width, d.height);
        add(roleinmovie);
        d = isstar.getPreferredSize();
        isstar.setBackground(Color.white);
        isstar.setBounds(insets.left + 100, insets.top + 71, d.width, d.height);
        add(isstar);
        d = isdirector.getPreferredSize();
        isdirector.setBackground(Color.white);
        isdirector.setBounds(insets.left + 160, insets.top + 71, d.width, d.height);
        add(isdirector);

        //Search & Clear Button
        search_button.setPreferredSize(new Dimension(210,35));
        d = search_button.getPreferredSize();
        search_button.setBounds(insets.left+100, insets.top+110, d.width, d.height);
        search_button.setIcon(new IconManager("/resources/theme/search.png").getIcon());
        add(search_button);
        search_button.addActionListener(this);
        clear_button.setPreferredSize(new Dimension(210,35));
        d = clear_button.getPreferredSize();
        clear_button.setBounds(insets.left+340, insets.top+110, d.width, d.height);
        clear_button.setIcon(new IconManager("/resources/theme/clear.png").getIcon());
        add(clear_button);
        clear_button.addActionListener(this);

        //Result
        result_label.setFont(new Font("Tahoma", Font.BOLD, 12));
        d = result_label.getPreferredSize();
        result_label.setPreferredSize(new Dimension(600,d.height));
        d = result_label.getPreferredSize();
        result_label.setBounds(insets.left + 10, insets.top + 150, d.width, d.height);
        add(result_label);
        add(resultlist);

        //Add resultlistScroll pane
        resultlistScroll = new JScrollPane(resultlist);
        resultlistScroll.setPreferredSize(new Dimension(630,310));
        d = resultlistScroll.getPreferredSize();
        resultlistScroll.setBounds(insets.left+10, insets.top+170, d.width, d.height);
        add(resultlistScroll);
        
        //add this panel to a ScroolPane
        tabScroll = new JScrollPane(this);
        tabScroll.getVerticalScrollBar().setUnitIncrement(20);
        tabScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabScroll.setBorder(null);
        refresh();
        updateListener();
    }

    //getters and setters
    public String getInputString ()
    {
        if (searchbox.getText() == null) return "";
        return searchbox.getText();
    }

    public String getSexState()
    {
        if (nevermind.isSelected()==true) return "";
        else if (male.isSelected()==true) return "Male";
        else return "Female";
    }

    public int getRoleState()
    {
        if (isstar.isSelected()==true && isdirector.isSelected()==true) return 0;
        else if (isstar.isSelected()==true) return 1;
        else if (isdirector.isSelected()==true) return 2;
        else return 3;
    }

    public String getYearFrom()
    {
        return birthday.valuefrom();
    }

    public String getYearTo()
    {
        return birthday.valueto();
    }

    public JList getResultList ()
    {
        return resultlist;
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

    public final void refresh() {//refresh panel after adding new result
        Dimension di = new Dimension(0, 150);        
        switch (state) {
            case 1: {
                di = moviedisplay.getPreferredSize();
                moviedisplay.setBounds(insets.left + 20, insets.top + 170, di.width, di.height);
                add(moviedisplay);
                break;
            }
            case 2: {
                di = persondisplay.getPreferredSize();
                persondisplay.setBounds(insets.left + 20, insets.top + 170, di.width, di.height);
                add(persondisplay);
                break;
            }
            case 3: {
                di = companydisplay.getPreferredSize();
                companydisplay.setBounds(insets.left + 20, insets.top + 170, di.width, di.height);
                add(companydisplay);
                setPreferredSize(new Dimension(650, 340 + di.height));
            }
        }
        tabScroll.getVerticalScrollBar().setValue(170);
        setPreferredSize(new Dimension(650, 180 + di.height));
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
                    state = 2;
                    Person person = (Person)result.get(selected);
                    //remove result display panel
                    remove(resultlistScroll);
                    persondisplay = new PersonDisplay(person.getId());
                    //add movie display                    
                    setPreferredSize(new Dimension(650, 340 + d.height));
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
                    
                    updateListener();
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
        searchbox.setText(null);
        model.clear();
        result_label.setText("Result");
        nevermind.setSelected(true);
        birthday.clear();
        isstar.setSelected(false);
        isdirector.setSelected(false);
        model.clear();
        switch (state) {
            case 0: return;
            case 1: remove(moviedisplay); break;
            case 2: remove(persondisplay); break;
            case 3: remove(companydisplay); break;
        }        
        d = resultlistScroll.getPreferredSize();
        resultlistScroll.setBounds(insets.left+10, insets.top+170, d.width, d.height);
        add(resultlistScroll);
        setPreferredSize(new Dimension(640, 490));
        repaint();
        revalidate();
    }

    public boolean check(Person person) {
        if (!getSexState().equals(""))
            if (!getSexState().equals(person.getSex())) return false;
        if (getRoleState() != 3) {
            if (getRoleState() == 0)
                if (person.getIsstar().equals("0") || person.getIsdirector().equals("0")) return false;
            if (getRoleState() == 1 && person.getIsstar().equals("0")) return false;
            if (getRoleState() == 2 && person.getIsdirector().equals("0")) return false;
        }
        if (!getYearFrom().equals("1850") || !getYearTo().equals("2006")) {
            if (person.getYear().equals("N/I")) return false;
            if (Integer.parseInt(person.getYear()) < Integer.parseInt(getYearFrom()) ||
                Integer.parseInt(person.getYear()) > Integer.parseInt(getYearTo())) return false;
        }
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (e.getSource() == search_button) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Integer minyear = (new Integer(birthday.valuefrom())).intValue();
            Integer maxyear = (new Integer(birthday.valueto())).intValue();
            if (minyear > maxyear)
            {
                if (minyear>maxyear) JOptionPane.showMessageDialog(this,"Birthday Error !" ,"Input Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                model.clear();
                switch (state) {
                    case 1: remove(moviedisplay); break;
                    case 2: remove(persondisplay); break;
                    case 3: remove(companydisplay); break;
                }
                d = resultlistScroll.getPreferredSize();
                resultlistScroll.setBounds(insets.left+10, insets.top+170, d.width, d.height);
                add(resultlistScroll);
                setPreferredSize(new Dimension(650, 490));
                repaint();
                revalidate();
                result = databaseManager.AdvancePersonSearch(getInputString(), getSexState(), getYearFrom(), getYearTo(), getRoleState());                
                String input = databaseManager.getSearchCommand(getInputString());
                if (result.size() != 0)
                    result_label.setText("Result (" + result.size() + ")");
                if (result.size() == 0 && input.length() != 0) {//approximate search
                    result = databaseManager.getPersonList();
                    int i = 0;
                    while (i < result.size()) {
                        person = (Person)result.get(i);
                        String temp = person.getName().toLowerCase();
                        int x = AppSearch.commonChar(temp, input);
                        person.setCommonChar(x);
                        if (!check(person)) result.remove(i);
                        else
                            if ((float)x / input.length() < 0.7) result.remove(i);
                            else i ++;
                    }
                    for (i = 0; i < result.size(); i ++)
                        for (int j = i + 1; j < result.size(); j ++) {
                            Person personi = (Person)result.get(i);
                            Person personj = (Person)result.get(j);                            
                            if (personi.getCommonChar() < personj.getCommonChar()) {                                
                                Person temp = personi;
                                result.set(i, personj);
                                result.set(j, temp);
                            }
                        }
                    if (input.length()>30) input = input.substring(0,26) + "...";
                    result_label.setText("<html>Your search - <font color = red>" + input + "</font> - did not match any person in database. Suggestions:</html>");
                }
                for (int i=0; i<result.size(); i++) {
                    person = (Person)result.get(i);
                    model.add(i, person.getName());
                }
            }
            setCursor(null);
        }
        if (e.getSource() == clear_button) {
            searchbox.setText(null);
            model.clear();
            result_label.setText("Result");
            nevermind.setSelected(true);
            birthday.clear();
            
            isstar.setSelected(false);
            isdirector.setSelected(false);
            switch (state) {
                case 1: remove(moviedisplay); break;
                case 2: remove(persondisplay); break;
                case 3: remove(companydisplay); break;
            }
            model.clear();                        
            d = resultlistScroll.getPreferredSize();
            resultlistScroll.setBounds(insets.left+10, insets.top+170, d.width, d.height);
            add(resultlistScroll);
            setPreferredSize(new Dimension(640, 490));
            repaint();
            revalidate();
        }
    }

}
