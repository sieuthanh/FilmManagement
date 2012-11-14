package admin.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.DAO.HibernateUtil;

@Entity
@Table(name = "filmstar")
public class FilmStar {
	@Id
	private FilmStarPrimaryKey filmStarPrimaryKey;

	public FilmStarPrimaryKey getFilmStarPrimaryKey() {
		return filmStarPrimaryKey;
	}

	public void setFilmStarPrimaryKey(FilmStarPrimaryKey filmStarPrimaryKey) {
		this.filmStarPrimaryKey = filmStarPrimaryKey;
	}

//	public static void main(String[] args) {
//		HibernateUtil hu = new HibernateUtil();
//		SessionFactory sessionFactory = hu.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		FilmStar filmStar = new FilmStar();
//		FilmStarPrimaryKey fspk = new FilmStarPrimaryKey("1", "2");
//		filmStar.setFilmStarPrimaryKey(fspk);
//		session.save(filmStar);
//		session.beginTransaction().commit();
//		session.close();
//		System.out.println("done");
//	}

}
