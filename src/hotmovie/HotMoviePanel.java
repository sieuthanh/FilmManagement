package hotmovie;

import hometab.HomeTab;
import hometab.RandomPanel;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import admin.DAO.HibernateUtil;

import databasemanager.Movie;

import moviedisplaypanel.MovieDisplay;

import wom.MainWindow;
import wom.Tab;

public class HotMoviePanel extends JPanel {

	private static final int TIMER_DELAY = 25;
	private Timer timer;
	private int xPos;
	private int yPos;
	private ArrayList<HotMovieElementPanel> panelList = new ArrayList<HotMovieElementPanel>();
	private MainWindow mainWindow1;
	private int count = 0;

	public HotMoviePanel(ArrayList<String> id, MainWindow mainWindow) {
		// TODO Auto-generated constructor stub
		this.mainWindow1 = mainWindow;
		xPos = 0;
		yPos = (int) HotMovieFrame.SIZE.getHeight();
		HotMovieElementPanel tmp;
		setLayout(null);
		setBackground(Color.white);
		for (int i = 0; i < id.size(); i++) {
			tmp = new HotMovieElementPanel(timer, id.get(i));
			panelList.add(tmp);
			tmp.setBounds(xPos, yPos + i
					* (int) tmp.getPreferredSize().getHeight(), (int) tmp
					.getPreferredSize().getWidth(), (int) tmp
					.getPreferredSize().getHeight());
			add(tmp);
		}
		for (int i = 0; i < panelList.size(); i++) {
			panelList.get(i).setBounds(
					xPos,
					yPos
							+ i
							* (int) panelList.get(i).getPreferredSize()
									.getHeight(),
					(int) panelList.get(i).getPreferredSize().getWidth(),
					(int) panelList.get(i).getPreferredSize().getHeight());
			panelList.get(i).addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					int tmp = yPos;
					yPos = tmp;
					timer.stop();
				}

				public void mouseExited(MouseEvent e) {
					timer.start();
				}

				public void mouseClicked(MouseEvent e) {
				

					Tab tab = mainWindow1.getTab();
					tab.setSelectedIndex(0);

					HomeTab homeTab = tab.getHometab();

					HotMovieElementPanel hmep = (HotMovieElementPanel) e
							.getSource();
					String id = hmep.getId();
					SessionFactory sf = new HibernateUtil().getSessionFactory();
					Session session = sf.openSession();
					Criteria criteria = session.createCriteria(Movie.class);
					criteria.add(Restrictions.eq("id", id));
					Movie movie = (Movie) criteria.uniqueResult();
					session.close();
					if (movie != null) {
						homeTab.getSearchbox().setText(movie.getName());
						homeTab.getSearchbtn().doClick();
						
						homeTab.getResultlist().setSelectedIndex(0);
					}

				}
			});
			add(panelList.get(i));
		}
		timer = new javax.swing.Timer(TIMER_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				yPos--;
				for (int i = 0; i < panelList.size(); i++) {
					panelList.get(i).setBounds(
							xPos,
							yPos
									+ i
									* (int) panelList.get(i).getPreferredSize()
											.getHeight(),
							(int) panelList.get(i).getPreferredSize()
									.getWidth(),
							(int) panelList.get(i).getPreferredSize()
									.getHeight());
					repaint();
					if (yPos < panelList.size()
							* (int) panelList.get(i).getPreferredSize()
									.getHeight() * -1) {
						yPos = (int) HotMovieFrame.SIZE.getHeight();
					}
				}
			}
		});
		timer.start();
	}

}
