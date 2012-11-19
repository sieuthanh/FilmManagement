/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import databasemanager.Company;
import databasemanager.DatabaseManager;
import databasemanager.Disk;
import databasemanager.MoneyUnit;
import databasemanager.Movie;

import admin.DAO.HibernateUtil;
import admin.view.CustomMessageDialog;

/**
 * 
 * @author CodeBlue
 */
public class DiskController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();

	// load data, then pass it to disk table model
	public static Vector loadDisk() {
		Vector vt = new Vector();
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			List list = session.createQuery("from Disk").list();
			Criteria criteria;
			for (Object o : list) {
				Disk disk = (Disk) o;

				Vector temp = new Vector();
				criteria = session.createCriteria(Movie.class);
				criteria.add(Restrictions.eq("id", disk.getFilmId()));
				Movie m = (Movie) criteria.uniqueResult();
				temp.add(disk.getFilmId());
				temp.add(m.getName());
				temp.add(disk.getDiskPerMovie());
				temp.add(disk.getTotalAmount());
				vt.add(temp);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		return vt;
	}

	public static Vector<Disk> searchDisk(String keyword) {
		Session session = sf.openSession();

		Vector vt = new Vector();
		String sql = "Select * from (Select * from Disk d, film m where d.filmId = m.id) as result where result.diskPerMovie like '%"
				+ keyword
				+ "%' or result.name like '%"
				+ keyword
				+ "%' or result.totalAmount like '%" + keyword + "%'";

		List result = session.createSQLQuery(sql).addEntity(Disk.class).addEntity(Movie.class).list();
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			Object[] obj = (Object[]) ite.next();
			Movie movie = (Movie) obj[1];
			Disk disk = (Disk) obj[0];
			Vector tmp = new Vector();
			tmp.add(movie.getId());
			tmp.add(movie.getName());
			tmp.add(disk.getDiskPerMovie());
			tmp.add(disk.getTotalAmount());
			vt.add(tmp);
		}

		return vt;
	}

	// add new disk
	public static boolean insert(String movieName, String diskPerMovie,
			String totalAmount) {
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			// Movie movie = (Movie) session.get(Movie.class, movieName);
			Disk disk = new Disk(movieName/* movie.getId() */, diskPerMovie,
					totalAmount);
			session.save(disk);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		// return false if error
		new CustomMessageDialog(null, true, "Oops...!", "An error occured!",
				CustomMessageDialog.MESSAGE);
		return false;
	}

	// update a disk
	public static boolean update(int id, String diskPerMovie, String totalAmount) {
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			Disk disk = (Disk) session.get(Disk.class, id);
			disk.setDiskPerMovie(diskPerMovie);
			disk.setTotalAmount(totalAmount);
			session.update(disk);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		// return false if error
		new CustomMessageDialog(null, true, "Oops...!", "An error occured!",
				CustomMessageDialog.MESSAGE);
		return false;
	}

	// delete a building
	public static boolean delete(String id) {
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			Disk disk = (Disk) session.get(Disk.class, id);
			Movie movie = (Movie) session.get(Movie.class, id);
			new CustomMessageDialog(
					null,
					true,
					"Warning",
					"<html>All records of film with this disk will be deleted. Still continue?</html>",
					CustomMessageDialog.CONFIRM);
			if (CustomMessageDialog.STATUS == CustomMessageDialog.CANCEL) {
				return false;
			}
			session.delete(disk);
			session.delete(movie);
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
		// return false if error
		new CustomMessageDialog(null, true, "Oops...!", "An error occured!",
				CustomMessageDialog.MESSAGE);
		return false;
	}

	// get a disk object by id
	public static Disk getDisk(String id) {
		Disk disk = null;
		Session session = sf.openSession();
		try {
			session.getTransaction().begin();
			disk = (Disk) session.get(Disk.class, id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
				e.printStackTrace();
			}
		}
		return disk;
	}

	public static String[] analysis(String time) {
		String[] arr = new String[3];
		String[] sub1 = time.split(" ");
		if (sub1[1].equals("AM")) {
			arr[0] = "0";
		} else {
			arr[0] = "1";
		}
		String[] sub2 = sub1[0].split(":");
		arr[1] = sub2[0];
		arr[2] = sub2[1];
		return arr;
	}

}
