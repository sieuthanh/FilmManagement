package admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import databasemanager.Movie;

import admin.DAO.HibernateUtil;

public class SearchFilmController {
	HibernateUtil dm = new HibernateUtil();
	SessionFactory sf = dm.getSessionFactory();

	public List<Movie> searchMovie(String keyword) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Movie.class);
		keyword = keyword.trim();

		Disjunction or = Restrictions.disjunction();

		or.add(Restrictions.like("name", "%" + keyword + "%"));
		if (this.isInteger(keyword)) {
			or.add(Restrictions.like("year", Integer.parseInt(keyword)));
		}
		or.add(Restrictions.like("country", "%" + keyword + "%"));
		if (this.isFloat(keyword)) {
			or.add(Restrictions.like("rating", Float.parseFloat(keyword)));
		}
		if (this.isInteger(keyword)) {
			or.add(Restrictions.like("runtime", Integer.parseInt(keyword)));
		}
		criteria.add(or);
		List<Movie> result = new ArrayList<Movie>();
		result = criteria.list();
		return result;
	}

	public boolean searchAddMovie(String film, int year) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Movie.class);
		film = film.trim();
		criteria.add(Restrictions.eq("name", film));
		criteria.add(Restrictions.eq("year", year));
		Movie movie = (Movie) criteria.uniqueResult();
		if (movie == null) {
			return false;
		}
		return true;
	}

	public static boolean isFloat(String a) {

		try {
			Float f = Float.parseFloat(a);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static boolean isInteger(String a) {
		try {
			int i = Integer.parseInt(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
