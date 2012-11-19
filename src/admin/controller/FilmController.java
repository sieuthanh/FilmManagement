package admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import databasemanager.Company;
import databasemanager.Disk;
import databasemanager.HotMovie;
import databasemanager.Movie;
import databasemanager.NewestMovie;

import admin.DAO.HibernateUtil;
import admin.bean.FilmCompany;
import admin.bean.FilmCompanyPrimaryKey;
import admin.bean.FilmDirector;
import admin.bean.FilmDirectorPrimaryKey;
import admin.bean.FilmGenre;
import admin.bean.FilmGenrePrimaryKey;
import admin.bean.FilmStar;
import admin.bean.FilmStarPrimaryKey;
import admin.view.CustomMessageDialog;


public class FilmController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();
	
	public static Vector<Movie> searchMovie2(String keyword) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Movie.class);
		keyword = keyword.trim();

		Disjunction or = Restrictions.disjunction();

		or.add(Restrictions.like("name", "%" + keyword + "%"));
		or.add(Restrictions.like("country", "%" + keyword + "%"));
		if (FilmController.isInteger(keyword)) {
			or.add(Restrictions.like("year",Integer.parseInt(keyword)));
		}
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
	
	public static HotMovie getHotMovie(String id) {
        HotMovie hotMovie = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            hotMovie = (HotMovie) session.get(HotMovie.class, id);
            //session.getTransaction().commit();
            //session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return hotMovie;
    }
	
	public static NewestMovie getNewestMovie(String id) {
        NewestMovie newestMovie = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            newestMovie = (NewestMovie) session.get(NewestMovie.class, id);
            //session.getTransaction().commit();
            //session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return newestMovie;
    }
	
    public static boolean delete(String id) {
    	Session session = sf.openSession();
		Criteria criteria = session.createCriteria(FilmCompany.class);
		Criteria criteria2 = session.createCriteria(FilmStar.class);
		Criteria criteria3 = session.createCriteria(FilmDirector.class);
		criteria.add(Restrictions.eq("filmCompanyPrimaryKey.fid", id));
		criteria2.add(Restrictions.eq("filmStarPrimaryKey.fid", id));
		criteria3.add(Restrictions.eq("filmDirectorPrimaryKey.fid", id));
        try {
            session.getTransaction().begin();
            Movie movie = (Movie) session.get(Movie.class, id);
            Disk disk = (Disk) session.get(Disk.class, id);
            ArrayList<FilmCompany> result = (ArrayList) criteria.list();
            ArrayList<FilmStar> result2 = (ArrayList) criteria2.list();
            ArrayList<FilmDirector> result3 = (ArrayList) criteria3.list();
			new CustomMessageDialog(
					null,
					true,
					"Warning",
					"<html>All records of person,disk,company with this movie will be deleted. Still continue?</html>",
					CustomMessageDialog.CONFIRM);
			if (CustomMessageDialog.STATUS == CustomMessageDialog.CANCEL) {
				return false;
			}
            session.delete(movie);
            if(result!=null){
            	for(FilmCompany tmp : result){
            		session.delete(tmp);
            	}
            }
            if(result2!=null){
            	for(FilmStar tmp : result2){
            		session.delete(tmp);
            	}
            }
            if(result3!=null){
            	for(FilmDirector tmp : result3){
            		session.delete(tmp);
            	}
            }
            session.delete(disk);
            if(checkHotMovie(id)){
            	HotMovie hotMovie = (HotMovie) session.get(HotMovie.class, id);
            	session.delete(hotMovie);
            }
            if(checkNewMovie(id)){
            	NewestMovie newMovie = (NewestMovie) session.get(NewestMovie.class, id);
            	session.delete(newMovie);
            }
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
    
    public static boolean insertStarLink(String fid, String sid) {
    	Session session = sf.openSession();
        try {
            //session.getTransaction().begin();
    		FilmStar filmStar = new FilmStar();
    		FilmStarPrimaryKey fspk = new FilmStarPrimaryKey(fid, sid);
    		filmStar.setFilmStarPrimaryKey(fspk);
    		session.save(filmStar);
    		session.beginTransaction().commit();
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
    
    public static boolean insertDirectorLink(String fid, String did) {
    	Session session = sf.openSession();
        try {
            //session.getTransaction().begin();
    		FilmDirector filmDirector = new FilmDirector();
    		FilmDirectorPrimaryKey fspk = new FilmDirectorPrimaryKey(fid, did);
    		filmDirector.setFilmDirectorPrimaryKey(fspk);
    		session.save(filmDirector);
    		session.beginTransaction().commit();
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
    
    public static boolean insertCompanyLink(String fid, String cid) {
    	Session session = sf.openSession();
        try {
            //session.getTransaction().begin();
    		FilmCompanyPrimaryKey fcpk = new FilmCompanyPrimaryKey(fid, cid);
    		FilmCompany fc = new FilmCompany();
    		fc.setFilmCompanyPrimaryKey(fcpk);
    		session.save(fc);
    		session.beginTransaction().commit();
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
        
    public static boolean checkHotMovie(String hotmovieid) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from HotMovie").list();
            for (Object o : list) {
                HotMovie hm = (HotMovie) o;
                if (hm.getId().equals(hotmovieid)) {
                    return true;
                }
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean checkNewMovie(String newestmovieid) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from NewestMovie").list();
            for (Object o : list) {
                NewestMovie nm = (NewestMovie) o;
                if (nm.getId().equals(newestmovieid)) {
                    return true;
                }
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
    
}
