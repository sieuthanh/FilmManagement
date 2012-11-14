package databasemanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="moneyunit")
public class MoneyUnit {
	@Id
	@Column(name="USD")
	private int usd;

	public int getUsd() {
		return usd;
	}

	public void setUsd(int usd) {
		this.usd = usd;
	}
}
