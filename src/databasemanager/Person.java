package databasemanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "sex")
	private String sex;
	@Column(name = "name")
	private String name;
	@Column(name = "day")
	private String day;
	@Column(name = "year")
	private String year;
	@Column(name = "born")
	private String born;
	@Column(name = "isdirector")
	private String isdirector;
	@Column(name = "isstar")
	private String isstar;
	@Transient
	private int commonChar;

	public Person(String id, String name, String sex, String day, String year,
			String born, String isdirector, String isstar) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.day = day;
		this.sex = sex;
		this.born = born;
		this.isdirector = isdirector;
		this.isstar = isstar;
	}

	public Person() {
		// TODO Auto-generated constructor stub
		this.id = "No information";
		this.name = "No information";
		this.year = "No information";
		this.day = "No information";
		this.sex = "No information";
		this.born = "No information";
		this.isdirector = "No information";
		this.isstar = "No information";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setBorn(String born) {
		this.born = born;
	}

	public void setIsdirector(String isdirector) {
		this.isdirector = isdirector;
	}

	public void setIsstar(String isstar) {
		this.isstar = isstar;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getSex() {
		return sex;
	}

	public String getDay() {
		return day;
	}

	public String getYear() {
		return year;
	}

	public String getBorn() {
		return born;
	}

	public String getIsstar() {
		return isstar;
	}

	public String getIsdirector() {
		return isdirector;
	}

	public float getCommonChar() {
		return commonChar;
	}

	public void setCommonChar(int x) {
		commonChar = x;
	}

	public boolean isDirector() {
		if (this.getIsdirector().equals("1")) {
			return true;
		}
		return false;
	}

	public boolean isStar() {
		if (this.getIsstar().equals("1")) {
			return true;
		}
		return false;
	}

	public String toString() {
		return id + "\t" + sex + "\t" + name + "\t" + day + "\t" + year + "\t"
				+ born + "\t" + isdirector + "\t" + isstar + "\n";
	}
}
