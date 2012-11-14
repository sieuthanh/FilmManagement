package databasemanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cartelement")
public class CartElement {
	@Id
	@Column(name = "filmid")
	private String filmId;
	@Column(name = "amount")
	private int amount;
	public String getFilmId() {
		return filmId;
	}
	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
	public int getDiskAmount() {
		return amount;
	}
	public void setDiskAmount(int amount) {
		this.amount = amount;
	}
	public String toString(){
		return filmId + "\t" + amount;
	}
}
