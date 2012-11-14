package wom;

import hotmovie.HotMovieFrame;
import hotmovie.NewestMovieFrame;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingWorker;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import databasemanager.HotMovie;
import databasemanager.NewestMovie;

import admin.DAO.HibernateUtil;

/**
 * 
 * @author Administrator
 */

public class SplashScreen extends JWindow {

	class SplashBackground extends JPanel implements PropertyChangeListener {
		private Image image;
		private JProgressBar progressbar = new JProgressBar(0, 100);
		private Insets insets = getInsets();
		private int wait = 30;
		private JLabel loading = new JLabel();

		class Task extends SwingWorker<Void, Void> {
			@Override
			public Void doInBackground() {
				int progress = 0;

				setProgress(progress);
				while (progress < 100) {
					try {
						Thread.sleep(wait);
					} catch (InterruptedException ignore) {
					}
					progress++;
					setProgress(progress);
				}
				return null;
			}

			@Override
			public void done() {
				setCursor(null); // turn off the wait cursor
				dispose();
				HibernateUtil hu = new HibernateUtil();
				SessionFactory sf = hu.getSessionFactory();
				Session session = sf.openSession();
				ArrayList<HotMovie> idList = new ArrayList<HotMovie>();
				ArrayList<String> idListString = new ArrayList<String>();
				ArrayList<String> idMovieNewestString = new ArrayList<String>();
				ArrayList<NewestMovie> idMovieNewest = new ArrayList<NewestMovie>();
				Criteria criteria = session.createCriteria(HotMovie.class);
				Criteria newestMovieCriteria = session
						.createCriteria(NewestMovie.class);
				idList = (ArrayList<HotMovie>) criteria.list();
				idMovieNewest = (ArrayList<NewestMovie>) newestMovieCriteria
						.list();
				session.close();
				for (int i = 0; i < idList.size(); i++) {
					idListString.add(idList.get(i).getId());
				}
				for (int i = 0; i < idMovieNewest.size(); i++) {
					idMovieNewestString.add(idMovieNewest.get(i).getId());
				}
				MainWindow window = new MainWindow("World of Movies 1.0");
				HotMovieFrame hmf = new HotMovieFrame(idListString, window);
				NewestMovieFrame newestMovie = new NewestMovieFrame(
						idMovieNewestString, window);
			}
		}

		public SplashBackground() {
			URL url = WindowBackgroundPanel.class
					.getResource("/splashscreen.png");
			if (url == null)
				System.out.println("Image not found");
			image = new ImageIcon(url).getImage();
			setPreferredSize(new Dimension(600, 200));
			setLayout(null);
			setOpaque(false);
			progressbar.setPreferredSize(new Dimension(600, 7));
			// add progress bar
			Dimension di = progressbar.getPreferredSize();
			progressbar.setBounds(insets.left, insets.top + 190, di.width,
					di.height);
			add(progressbar);
			// add loading label
			loading.setPreferredSize(new Dimension(100, 20));
			di = loading.getPreferredSize();
			loading.setBounds(insets.left + 5, insets.top + 170, di.width,
					di.height);
			loading.setText("Loading...");
			add(loading);
			// set mouse waiting
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			//
			Task task = new Task();
			task.addPropertyChangeListener(this);
			task.execute();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, null);
		}

		public void propertyChange(PropertyChangeEvent e) {
			// throw new UnsupportedOperationException("Not supported yet.");
			if (e.getPropertyName().equals("progress")) {
				int progress = (Integer) e.getNewValue();
				loading.setText("Loading..." + progress + "%");
				progressbar.setValue(progress);
			}
		}
	}

	public SplashScreen() {
		add(new SplashBackground());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
