package admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import databasemanager.Movie;

import admin.DAO.HibernateUtil;
import admin.view.CustomMessageDialog;


public class FilmController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();
	public static ArrayList storeManager = null;
	
	public static Vector<Movie> searchMovie2(String keyword) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Movie.class);
		keyword = keyword.trim();

		Disjunction or = Restrictions.disjunction();

		or.add(Restrictions.like("name", "%" + keyword + "%"));
		if (FilmController.isInteger(keyword)) {
			or.add(Restrictions.like("year",Integer.parseInt(keyword)));
		}
		or.add(Restrictions.like("country", "%" + keyword + "%"));
		if (FilmController.isFloat(keyword)) {
			or.add(Restrictions.like("rating", Float.parseFloat(keyword)));
		}
		if (FilmController.isInteger(keyword)) {
			or.add(Restrictions.like("runtime", Integer.parseInt(keyword)));
		}
		criteria.add(or);
		Vector vt = new Vector();
		List result = new Vector<Movie>();
		result = criteria.list();
		for (Object o : result) {
            Movie movie = (Movie) o;
            Vector temp = new Vector();
            temp.add(movie.getName());
            temp.add(movie.getCountry());
            temp.add(movie.getYear());
            temp.add(movie.getRating());
            temp.add(movie.getRuntime());
            temp.add(movie.getId());
            vt.add(temp);
        }
		return vt;
	}

	private static boolean isFloat(String a) {

		try {
			Float f = Float.parseFloat(a);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	private static boolean isInteger(String a) {
		try {
			int i = Integer.parseInt(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public static Vector getMovieList2() {
        Vector vt = new Vector();
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from Movie").list();
            for (Object o : list) {
                Movie movie = (Movie) o;
                Vector temp = new Vector();
                temp.add(movie.getName());
                temp.add(movie.getCountry());
                temp.add(movie.getYear());
                temp.add(movie.getRating());
                temp.add(movie.getRuntime());
                temp.add(movie.getId());
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
	
	public static Movie getMovie(String id) {
        Movie movie = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            movie = (Movie) session.get(Movie.class, id);
            //session.getTransaction().commit();
            //session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return movie;
    }
	
    public static boolean delete(String id) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Movie movie = (Movie) session.get(Movie.class, id);
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
        new CustomMessageDialog(null, true, "Oops...!", "An error occured!", CustomMessageDialog.MESSAGE);
        return false;
    }
    
    public static boolean update(String id, String name, int year, int runtime, float rating, String country) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Movie movie = (Movie) session.get(Movie.class, id);
            movie.setName(name);
            movie.setYear(year);
            movie.setRuntime(runtime);
            movie.setRating(rating);
            movie.setCountry(country);
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
        new CustomMessageDialog(null, true, "Oops...!", "An error occured!", CustomMessageDialog.MESSAGE);
        return false;
    }
    
    public static boolean insert(String id, String name, int year, int runtime, float rating, String country) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Movie movie = new Movie(id, name, year, country, rating, runtime);
            session.save(movie);
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
        new CustomMessageDialog(null, true, "Oops...!", "An error occured!", CustomMessageDialog.MESSAGE);
        return false;
    }
    
    public static void readManagerToCBB(JComboBox combo) {
        storeManager = new ArrayList();
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("select m.userId, m.fullname from Manager m").list();
            for (Iterator it = list.iterator(); it.hasNext();) {
                Object[] objects = (Object[]) it.next();
                storeManager.add(objects);
                combo.addItem(objects[1]);
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
