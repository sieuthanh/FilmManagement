package bill;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.DAO.HibernateUtil;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "billarchive")
public class BillArchive {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "total", nullable = false)
	private int total;
	@Column(name = "day", nullable = false)
	private int day;
	@Column(name = "month", nullable = false)
	private int month;
	@Column(name = "year", nullable = false)
	private int year;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public static void main(String[] args) {
		HibernateUtil hu = new HibernateUtil();
		hu.createTable(BillArchive.class);
	}
}
