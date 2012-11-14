package hometab;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import databasemanager.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import moviedisplaypanel.*;
import persondisplaypanel.*;
import companydisplaypanel.*;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author bs90
 */
public class HomeTab extends JPanel implements ActionListener {

	private JTextField searchbox = new JTextField();
	private JButton searchbtn = new JButton("Search");
	// Option
	private JRadioButton title = new JRadioButton("Title");
	private JRadioButton person = new JRadioButton("Person");
	private JRadioButton all = new JRadioButton("All");
	private ButtonGroup group = new ButtonGroup();

	private Insets insets;
	private Dimension d;

	private RandomPanel randompanel = new RandomPanel();
	private DatabaseManager databasemanager = new DatabaseManager();
	private List movielist;
	public JList getResultlist() {
		return resultlist;
	}

	public void setResultlist(JList resultlist) {
		this.resultlist = resultlist;
	}

	private List personlist;

	private DefaultListModel model = new DefaultListModel();
	private JList resultlist = new JList(model);
	private JScrollPane resultlistScroll;
	private JScrollPane tabScroll;

	private int state = 4;// 0: resultlist 1:movie 2: person 3: company 4:
							// origin
	private MovieDisplay moviedisplay;
	private PersonDisplay persondisplay;
	private CompanyDisplay companydisplay;
	private Movierandomdisplay[] m = randompanel.getMovierandomdisplay();

	public JButton getSearchbtn() {
		return searchbtn;
	}

	public void setSearchbtn(JButton searchbtn) {
		this.searchbtn = searchbtn;
	}

	public JTextField getSearchbox() {
		return searchbox;
	}

	public void setSearchbox(JTextField searchbox) {
		this.searchbox = searchbox;
	}

	public HomeTab() {
		super();
		setLayout(null);
		setBackground(Color.white);
		setPreferredSize(new Dimension(650, 490));
		insets = getInsets();
		searchbox.setPreferredSize(new Dimension(450, 25));
		searchbox.setBounds(insets.left + 50, insets.top + 25, 450, 25);
		add(searchbox);
		searchbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					searchbtn.doClick();
			}
		});
		// add search button
		searchbtn.setPreferredSize(new Dimension(100, 25));
		searchbtn.setBounds(insets.left + 510, insets.top + 25, 100, 25);
		searchbtn.setFocusPainted(false);
		add(searchbtn);
		searchbtn.addActionListener(this);

		title.setBackground(Color.white);
		person.setBackground(Color.white);
		all.setBackground(Color.white);
		title.setFocusPainted(false);
		person.setFocusPainted(false);
		all.setFocusPainted(false);
		group.add(title);
		group.add(person);
		group.add(all);
		all.setSelected(true);
		d = title.getPreferredSize();
		title.setBounds(insets.left + 150, insets.top + 50, d.width, d.height);
		d = person.getPreferredSize();
		person.setBounds(insets.left + 250, insets.top + 50, d.width, d.height);
		d = all.getPreferredSize();
		all.setBounds(insets.left + 350, insets.top + 50, d.width, d.height);
		add(title);
		add(person);
		add(all);
		// add resultpanel
		d = randompanel.getPreferredSize();
		randompanel.setBounds(insets.left + 50, insets.top + 75, d.width,
				d.height);
		add(randompanel);
		// result list scrollpane
		resultlistScroll = new JScrollPane(resultlist);
		resultlistScroll.setPreferredSize(new Dimension(560, 370));
		// jscrollpane for whole tab
		tabScroll = new JScrollPane(this);
		tabScroll.getVerticalScrollBar().setUnitIncrement(20);
		tabScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabScroll.setBorder(null);
		// update mouse listener
		updateListener();
	}

	public HomeTab(String id) {
		super();
		setLayout(null);
		setBackground(Color.white);
		setPreferredSize(new Dimension(650, 490));
		insets = getInsets();
		searchbox.setPreferredSize(new Dimension(450, 25));
		searchbox.setBounds(insets.left + 50, insets.top + 25, 450, 25);
		add(searchbox);
		searchbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					searchbtn.doClick();
			}
		});
		// add search button
		searchbtn.setPreferredSize(new Dimension(100, 25));
		searchbtn.setBounds(insets.left + 510, insets.top + 25, 100, 25);
		searchbtn.setFocusPainted(false);
		add(searchbtn);
		searchbtn.addActionListener(this);

		title.setBackground(Color.white);
		person.setBackground(Color.white);
		all.setBackground(Color.white);
		title.setFocusPainted(false);
		person.setFocusPainted(false);
		all.setFocusPainted(false);
		group.add(title);
		group.add(person);
		group.add(all);
		all.setSelected(true);
		d = title.getPreferredSize();
		title.setBounds(insets.left + 150, insets.top + 50, d.width, d.height);
		d = person.getPreferredSize();
		person.setBounds(insets.left + 250, insets.top + 50, d.width, d.height);
		d = all.getPreferredSize();
		all.setBounds(insets.left + 350, insets.top + 50, d.width, d.height);
		add(title);
		add(person);
		add(all);
		// add resultpanel
		MovieDisplay moviedisplay = new MovieDisplay(id);
		Insets insets = getInsets();
		Dimension d = moviedisplay.getPreferredSize();
		moviedisplay.setBounds(insets.left + 20, insets.top + 75, d.width,
				d.height);
		add(moviedisplay);
		setPreferredSize(new Dimension(650, 80 + d.height));
		// result list scrollpane
		resultlistScroll = new JScrollPane(resultlist);
		resultlistScroll.setPreferredSize(new Dimension(560, 370));
		// jscrollpane for whole tab
		tabScroll = new JScrollPane(this);
		tabScroll.getVerticalScrollBar().setUnitIncrement(20);
		tabScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tabScroll.setBorder(null);
		// update mouse listener
		updateListener();
	}

	public void tabChanged() {
		searchbox.setText("");
		switch (this.state) {
		case 0:
			remove(resultlistScroll);
			break;
		case 1:
			remove(moviedisplay);
			break;
		case 2:
			remove(persondisplay);
			break;
		case 3:
			remove(companydisplay);
			break;
		case 4:
			remove(randompanel);
			break;
		}
		state = 4;
		refresh();
		updateListener();
	}

	// getters and setters
	public int getState() {
		int check = 0;
		if (title.isSelected() == true)
			check = 1;
		if (person.isSelected() == true)
			check = 2;
		if (all.isSelected() == true)
			check = 3;
		return check;
	}

	public String getSearchCommand() {
		return searchbox.getText();
	}

	public JScrollPane getScrollPane() {
		return tabScroll;
	}

	// end getters and setters

	public final void updateListener() {
		switch (state) {
		case 0:
			addMouseListenerToResultList();
			break;
		case 1:
			addMouseListenerToMovieDisplay();
			break;
		case 2:
			addMouseListenerToPersonDisplay();
			break;
		case 3:
			addMouseListenerToCompanyDisplay();
			break;
		case 4:
			addMouseListenerToRandomMoviePanel();
			break;
		}
	}

	public MovieDisplay getMoviedisplay() {
		return moviedisplay;
	}

	public void setMoviedisplay(MovieDisplay moviedisplay) {
		this.moviedisplay = moviedisplay;
	}

	public void refresh() {
		switch (state) {
		case 1: {
			d = moviedisplay.getPreferredSize();
			moviedisplay.setBounds(insets.left + 20, insets.top + 75, d.width,
					d.height);
			add(moviedisplay);
			setPreferredSize(new Dimension(650, 80 + d.height));
			repaint();
			revalidate();
			break;
		}
		case 2: {
			d = persondisplay.getPreferredSize();
			persondisplay.setBounds(insets.left + 20, insets.top + 75, d.width,
					d.height);
			add(persondisplay);
			setPreferredSize(new Dimension(650, 80 + d.height));
			repaint();
			revalidate();
			break;
		}
		case 3: {
			d = companydisplay.getPreferredSize();
			companydisplay.setBounds(insets.left + 20, insets.top + 75,
					d.width, d.height);
			add(companydisplay);
			setPreferredSize(new Dimension(650, d.height + 80));
			repaint();
			revalidate();
			break;
		}
		case 4: {
			randompanel.tabChanged();
			m = randompanel.getMovierandomdisplay();
			d = randompanel.getPreferredSize();
			randompanel.setBounds(insets.left + 50, insets.top + 75, d.width,
					d.height);
			add(randompanel);
			setPreferredSize(new Dimension(650, 490));
			repaint();
			revalidate();
			break;
		}
		}
		tabScroll.getVerticalScrollBar().setValue(75);
	}

	// add mouselistener fucntions
	public void addMouseListenerToRandomMoviePanel() {
		for (int i = 0; i < 10; i++) {
			final String id = m[i].getID();
			m[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					state = 1;
					moviedisplay = new MovieDisplay(id);
					remove(randompanel);
					refresh();
					updateListener();
				}
			});
		}
	}

	public RandomPanel getRandompanel() {
		return randompanel;
	}

	public void setRandompanel(RandomPanel randompanel) {
		this.randompanel = randompanel;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void addMouseListenerToResultList() {
		resultlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					JList clicked = (JList) e.getSource();
					int selected = clicked.getSelectedIndex();
					if ((getState() == 1)
							|| ((getState() == 3) && (selected < movielist
									.size()))) {
						Movie movie = (Movie) movielist.get(selected);
						moviedisplay = new MovieDisplay(movie.getId());
						state = 1;
					} else if ((getState() == 2)
							|| ((getState() == 3) && (selected >= movielist
									.size()))) {
						Person person = (Person) personlist.get(selected
								- movielist.size());
						persondisplay = new PersonDisplay(person.getId());
						state = 2;
					}
					// remove result display panel
					remove(resultlistScroll);
					//
					refresh();
					updateListener();
				}
			}
		});
	}

	public void addMouseListenerToMovieDisplay() {
		List personlist;
		LinkedLabel x;
		// add mouselistener to personlist
		personlist = moviedisplay.getListPersonLabel();
		for (int i = 0; i < personlist.size(); i++) {
			x = (LinkedLabel) personlist.get(i);
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
		// add mouselistener to company label
		List companylist = moviedisplay.getListCompanyLabel();
		for (int i = 0; i < companylist.size(); i++) {
			x = (LinkedLabel) companylist.get(i);
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
		for (int i = 0; i < movielist.size(); i++) {
			x = (persondisplaypanel.MovieNameLabel) movielist.get(i);
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
		for (int i = 0; i < movielist.size(); i++) {
			x = (companydisplaypanel.MovieNameLabel) movielist.get(i);
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

	// end add mouselistener functions

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchbtn) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			switch (state) {
			case 0:
				remove(resultlistScroll);
				break;
			case 1:
				remove(moviedisplay);
				break;
			case 2:
				remove(persondisplay);
				break;
			case 3:
				remove(companydisplay);
				break;
			case 4:
				remove(randompanel);
				break;
			}
			model.clear();
			resultlist = new JList(model);
			resultlistScroll = new JScrollPane(resultlist);
			resultlistScroll.setPreferredSize(new Dimension(560, 370));
			d = resultlistScroll.getPreferredSize();
			resultlistScroll.setBounds(insets.left + 50, insets.top + 75,
					d.width, d.height);
			add(resultlistScroll);
			setPreferredSize(new Dimension(650, 490));
			repaint();
			revalidate();
			switch (getState()) {
			case 1: {
				movielist = databasemanager
						.SimpleMovieSearch(getSearchCommand());
				personlist = new LinkedList();
				break;
			}
			case 2: {
				personlist = databasemanager
						.SimplePersonSearch(getSearchCommand());
				movielist = new LinkedList();
				break;
			}
			case 3: {
				movielist = databasemanager
						.SimpleMovieSearch(getSearchCommand());
				personlist = databasemanager
						.SimplePersonSearch(getSearchCommand());
				break;
			}
			}
			for (int i = 0; i < movielist.size(); i++) {
				Movie movie = (Movie) movielist.get(i);
				model.add(i, movie.getName());
			}
			for (int i = 0; i < personlist.size(); i++) {
				Person person = (Person) personlist.get(i);
				model.add(movielist.size() + i, person.getName());
			}
			// add result scrollpane to panel
			state = 0;
			updateListener();
			setCursor(null);
		}
	}

}
