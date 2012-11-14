package admin.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.DAO.HibernateUtil;

@Entity
@Table(name = "filmcompany")
public class FilmCompany {
	@Id
	private FilmCompanyPrimaryKey filmCompanyPrimaryKey;
	public FilmCompany() {
		// TODO Auto-generated constructor stub
	}
	public FilmCompanyPrimaryKey getFilmCompanyPrimaryKey() {
		return filmCompanyPrimaryKey;
	}

	public void setFilmCompanyPrimaryKey(
			FilmCompanyPrimaryKey filmCompanyPrimaryKey) {
		this.filmCompanyPrimaryKey = filmCompanyPrimaryKey;
	}

	public static void main(String[] args) {
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		FilmCompanyPrimaryKey fcpk = new FilmCompanyPrimaryKey("1", "2");
		FilmCompany fc = new FilmCompany();
		fc.setFilmCompanyPrimaryKey(fcpk);
		
		session.save(fc);
		session.beginTransaction().commit();
		session.close();
	}
}
