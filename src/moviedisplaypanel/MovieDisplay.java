package moviedisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import databasemanager.*;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JTextArea;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import wom.MainWindow;

import cart.Cart;

import com.sun.xml.internal.ws.api.server.Container;

import admin.DAO.HibernateUtil;

public class MovieDisplay extends JPanel {

	static String id;
	TextLabel textlabel = new TextLabel();
	DownloadPanel downloadpanel;
	String path = "data/Films/";
	JLabel runtimegenres, rating, director, company, des, updaterate,
			viewdetail, country;
	JLabel title, rate, coun, runt;
	JTextPane desc = new JTextPane();
	ListPanel lc, ld;
	StarsList starslist;
	Border border = BorderFactory.createLineBorder(Color.lightGray);
	Border imageborder = BorderFactory.createTitledBorder(border,
			"Screen Shot", TitledBorder.CENTER, TitledBorder.TOP, new Font(
					"Tahoma", Font.BOLD, 14), Color.black);
	String[] listcompanyname;
	String[] listcompanyid;
	String[] liststarname;
	String[] liststarid;
	String[] listdirid;
	String[] listdirname;
	int companysize, starsize, directorsize, h;
	LinkedList liststar, listcompany, listdir;
	Company com;
	Person person;
	LinkedList liststarlabel, listdirectorlabel, listcompanylabel;
	private DatabaseManager databasemanager = new DatabaseManager();

	public MovieDisplay(String id) {
		this.id = id;
		path = path + id + "/";
		setLayout(null);
		setBackground(Color.white);
		Dimension d;
		setAutoscrolls(true);

		// Get film info
		liststar = databasemanager.getStarByMovie(id);
		liststarname = new String[liststar.size()];
		liststarid = new String[liststar.size()];
		for (int i = 0; i < liststar.size(); i++) {
			person = (Person) liststar.get(i);
			liststarname[i] = person.getName();
			liststarid[i] = person.getId();
		}

		listdir = databasemanager.getDirectorByMovie(id);
		listdirname = new String[listdir.size()];
		listdirid = new String[listdir.size()];
		for (int i = 0; i < listdir.size(); i++) {
			person = (Person) listdir.get(i);
			listdirname[i] = person.getName();
			listdirid[i] = person.getId();
		}

		listcompany = databasemanager.getCompanyByMovie(id);
		listcompanyname = new String[listcompany.size()];
		listcompanyid = new String[listcompany.size()];
		for (int i = 0; i < listcompany.size(); i++) {
			com = (Company) listcompany.get(i);
			listcompanyname[i] = com.getName();
			listcompanyid[i] = com.getId();
		}

		Movie movieinfo = databasemanager.getMovieinfo(id);

		if (liststarname.length % 2 == 1)
			starsize = (liststarname.length / 2 + 1) * 50 + 35;
		else
			starsize = (liststarname.length / 2) * 50 + 35;
		h = 500 + starsize + 310 + 10 + 10;

		setPreferredSize(new Dimension(600, h));
		setBackground(Color.white);
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Disk.class);
		Criteria criteria1 = session.createCriteria(MoneyUnit.class);
		criteria.add(Restrictions.eq("filmId", id));
		Disk disk = (Disk) criteria.uniqueResult();
		MoneyUnit mu = (MoneyUnit) criteria1.uniqueResult();
		session.close();
		// Add Title
		title = textlabel.BoldTextLabel(
				movieinfo.getName() + " (" + movieinfo.getYear() + ") - "
						+ (int) Integer.parseInt(disk.getDiskPerMovie())
						* mu.getUsd() + " USD / " + disk.getDiskPerMovie()
						+ " disk(s)", 20, x, y);
		add(title);

		// Add runtime + Genres
		LinkedList gen = databasemanager.getMovieGenres(id);
		String genre = "";
		for (int i = 0; i < gen.size(); i++) {
			genre += (String) gen.get(i) + ", ";
		}
		genre = genre.substring(0, genre.length() - 2);
		String rg = movieinfo.getRuntime() + " mins | " + genre;
		runtimegenres = textlabel.NormalTextLabel(rg, 12, 0, 25);
		add(runtimegenres);

		// Add Poster Label
		ImageLabel poster = new ImageLabel(path + "0.jpg", 200, 310);
		d = poster.getPreferredSize();
		poster.setBounds(insets.left, insets.top + 45, d.width, d.height);
		add(poster);

		// Add Rating Label
		rating = textlabel.BoldTextLabel("Rating : ", 14, x + 205, y + 43);
		rate = textlabel.NormalTextLabel(movieinfo.getRating() + "/10", 14,
				x + 262, y + 46);
		add(rate);
		add(rating);

		// Add Update Rate Label
		updaterate = textlabel.ActionLabel("Update Rating", 12);
		d = updaterate.getPreferredSize();
		updaterate.setBounds(insets.left + 300, insets.top + 45, d.width,
				d.height);
		updaterate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				UpdateRate();
			}
		});
		add(updaterate);

		// Add Coutry Label
		country = textlabel.BoldTextLabel("Country : ", 14, x + 205, y + 65);
		coun = textlabel.NormalTextLabel(movieinfo.getCountry(), 12, x + 275,
				y + 68);
		add(coun);
		add(country);

		// Add Director Label
		director = textlabel.BoldTextLabel("Director : ", 14, x + 205, y + 87);
		add(director);
		ld = new ListPanel(listdirname, listdirid, 16);
		d = ld.getPreferredSize();
		ld.setBounds(insets.left + 280, insets.top + 89, d.width, d.height);
		add(ld);
		directorsize = d.height - 21;
		listdirectorlabel = ld.getListLabel();

		// Add Company
		company = textlabel.BoldTextLabel("Company : ", 14, x + 205, y + 109
				+ directorsize);
		add(company);
		lc = new ListPanel(listcompanyname, listcompanyid, 16);
		d = lc.getPreferredSize();
		lc.setBounds(insets.left + 290, insets.top + 111 + directorsize,
				d.width, d.height);
		add(lc);
		companysize = d.height;
		listcompanylabel = lc.getListLabel();

		// Add Des Label
		des = textlabel.BoldTextLabel("Short Description ", 14, x + 205, y
				+ 111 + companysize + directorsize);
		add(des);

		// Add View Detail
		viewdetail = textlabel.ActionLabel("View Detail", 12);
		d = viewdetail.getPreferredSize();
		viewdetail.setBounds(insets.left + 400, insets.top + 111 + companysize
				+ directorsize, d.width, d.height);
		viewdetail.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				viewfulldetail();
			}
		});

		add(viewdetail);

		// Add to cart button

		 session = sf.openSession();
		Criteria criteria2 = session.createCriteria(Disk.class);
		criteria2.add(Restrictions.eq("filmId", id));
		 disk = (Disk) criteria2.uniqueResult();
		session.beginTransaction().commit();
		session.close();
		JButton addToCart;
		if (Integer.parseInt(disk.getTotalAmount()) < 1) {
			addToCart = new JButton("Out of stock");
			addToCart.setEnabled(false);
			d = addToCart.getPreferredSize();
			addToCart.setBounds(insets.left + 210, insets.top + 250
					+ companysize + directorsize, d.width, d.height);
		} else {
			addToCart = new JButton("Add to cart");
			d = addToCart.getPreferredSize();
			addToCart.setBounds(insets.left + 210, insets.top + 250
					+ companysize + directorsize, d.width, d.height);
			addToCart.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					HibernateUtil hu = new HibernateUtil();
					hu.createTable(CartElement.class);
					SessionFactory sf = hu.getSessionFactory();
					ArrayList<CartElement> cart = new ArrayList<CartElement>();
					String tmp;
					String[] cartElementString;
					CartElement ce;
					Session session = sf.openSession();
					Criteria checkElementExistCriteria = session
							.createCriteria(CartElement.class);
					checkElementExistCriteria.add(Restrictions.eq("filmId",
							MovieDisplay.id));
					ce = (CartElement) checkElementExistCriteria.uniqueResult();
					session.beginTransaction().commit();
					session.close();

					if (ce == null) {
						ce = new CartElement();
						ce.setFilmId(MovieDisplay.id);
						ce.setDiskAmount(1);

					} else {
						ce.setDiskAmount(ce.getDiskAmount() + 1);
					}

					session = sf.openSession();
					session.saveOrUpdate(ce);
					session.beginTransaction().commit();
					session.close();
					JOptionPane.showMessageDialog(null, "Add complete");
					if (MainWindow.cartClick != null) {
						if (MainWindow.cartClick.isOpen) {

							MainWindow.cartClick.setVisible(false);
							MainWindow.cartClick.dispose();
							System.gc();
							MainWindow.cartClick = new Cart();
							MainWindow.cartClick.setVisible(true);
							MainWindow.cartClick.isOpen = true;
							// MainWindow.cartClick.setVisible(true);
						}

						else {
							MainWindow.cartClick = new Cart();
							MainWindow.cartClick.setVisible(true);
							MainWindow.cartClick.isOpen = true;
						}

					}
				}
			});
		}
		add(addToCart);
		// Add Description
		ReadFile rf = new ReadFile(path + "Short_Des.txt");
		desc.setText(rf.ReadAllFile());
		desc.setPreferredSize(new Dimension(390, 175));
		d = desc.getPreferredSize();
		desc.setBounds(insets.left + 205, insets.top + 130 + companysize
				+ directorsize, d.width, d.height);
		desc.setEditable(false);
		add(desc);

		// Add Film Image Panel
		FilmImagePanel fip = new FilmImagePanel(path);
		fip.setBorder(imageborder);
		d = fip.getPreferredSize();
		fip.setBounds(insets.left, insets.top + 355, d.width, d.height);
		add(fip);

		// Add List Stars Panel
		starslist = new StarsList(liststarname, liststarid, 50);
		d = starslist.getPreferredSize();
		starslist.setBounds(insets.left, insets.top + 495, d.width, d.height);
		add(starslist);
		liststarlabel = starslist.getStarsLabel();

		// Download Panel
		downloadpanel = new DownloadPanel(path);
		downloadpanel.setBounds(insets.left, insets.top + starsize + 500, 587,
				300);
		add(downloadpanel);
	}

	private void viewfulldetail() {
		JTextPane detail = new JTextPane();
		ReadFile rf = new ReadFile(path + "Long_Des.txt");
		detail.setEditable(false);
		detail.setText(rf.ReadAllFile());
		// detail.setPreferredSize(new Dimension(600, 10000));
		JScrollPane sp = new JScrollPane(detail);
		sp.setPreferredSize(new Dimension(600, 400));
		/*
		 * sp.getVerticalScrollBar().setUnitIncrement(20);
		 * sp.setHorizontalScrollBarPolicy
		 * (JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		 * sp.getVerticalScrollBar().setValue
		 * (sp.getVerticalScrollBar().getMinimum());
		 */
		JFrame f = new JFrame("Detail");
		f.add(sp);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void UpdateRate() {
		BufferedReader in, nin;
		URLConnection con;
		String line, newrate = null;
		String currate = rate.getText().substring(0, 3);
		URL imdb;
		int p = -7;

		try {
			String u = "http://www.imdb.com/title/tt" + id + "/";
			// System.out.println(u);
			imdb = new URL(u);
			con = imdb.openConnection();
			con.setRequestProperty("User-Agent", "imdb");
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((line = in.readLine()) != null) {
				if ((p = line
						.indexOf("<div class=\"titlePageSprite star-box-giga-star\">")) != -1) {
					line = in.readLine();

					break;
				}
			}
			in.close();
			newrate = line.toString();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR ",
					JOptionPane.ERROR_MESSAGE);
		}
		if (newrate != null) {

			if (!newrate.equals(currate)) {
				// System.out.println(newrate + " " + currate);
				String info = "Current Rate on IMDB : " + newrate;
				info += "\nDo you want to update to Database ?";
				int i = JOptionPane.showConfirmDialog(null, info,
						"New Rate Available", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (i == 0) {
					databasemanager.updateRating(id,
							Float.valueOf(newrate.trim()).floatValue());
					rate.setText(newrate + "/10");
					JOptionPane.showMessageDialog(null,
							"New Rate has been updated to your database !",
							"Update Sucessfully",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else
				JOptionPane.showMessageDialog(null,
						"Current Rate on IMDB is still " + newrate,
						"No Change", JOptionPane.INFORMATION_MESSAGE);
		}

		setCursor(null);
	}

	public LinkedList getListCompanyLabel() {
		return listcompanylabel;
	}

	public LinkedList getListPersonLabel() {
		for (int i = 0; i < listdirectorlabel.size(); i++)
			liststarlabel.add((LinkedLabel) listdirectorlabel.get(i));
		return liststarlabel;
	}
}
