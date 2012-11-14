package databasemanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "film")
public class Movie {
	@Column(name = "name")
	private String name;
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "year")
	private int year;
	@Column(name = "runtime")
	private int runtime;
	@Column(name = "rating")
	private float rating;
	@Column(name = "country")
	private String country;
	@Transient
	private int commonChar;

	public Movie() {

	}

	public Movie(String id, String name, int year, String country,
			float rating, int runtime) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.runtime = runtime;
		this.rating = rating;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getCountry() {
		return country;
	}

	public int getRuntime() {
		return runtime;
	}

	public int getYear() {
		return year;
	}

	public float getRating() {
		return rating;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCommonChar() {
		return commonChar;
	}

	public void setCommonChar(int x) {
		commonChar = x;
	}
	public String toString(){
		return this.id+"\t"+this.name+"\t"+this.rating+"\t"+this.runtime+"\t"+this.country+"\t"+this.year;
	}
}
