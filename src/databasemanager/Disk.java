package databasemanager;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.DAO.HibernateUtil;

@Entity
public class Disk implements Serializable {
	@Id
	@Column(name = "filmid")
	private String filmId;
	@Column(name = "diskpermovie")
	private String diskPerMovie;
	@Column(name = "totalamount")
	private String totalAmount;

	public Disk() {
		filmId = "";
		diskPerMovie = "";
		totalAmount = "";
	}

	public Disk(String filmId, String diskPerMovie, String totalAmount) {
		super();
		this.filmId = filmId;
		this.diskPerMovie = diskPerMovie;
		this.totalAmount = totalAmount;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	public String getDiskPerMovie() {
		return diskPerMovie;
	}

	public void setDiskPerMovie(String diskPerMovie) {
		this.diskPerMovie = diskPerMovie;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public static void main(String[] args) {
		HibernateUtil hu = new HibernateUtil();
		if (hu.createTable(Disk.class)) {
			System.out.println("Success");
		}
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		Disk d = new Disk("1290400", "3", "20");
		Disk d1 = new Disk("1179034", "5", "10");
		Disk d2 = new Disk("1190080", "3", "30");
		session.save(d);
		session.save(d1);
		session.save(d2);
		session.beginTransaction().commit();
		session.close();
	}
}
