package databasemanager;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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


}
