package admin.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filmgenre")
public class FilmGenre {
	@Id
	private FilmGenrePrimaryKey filmGenrePrimaryKey;
	
	public FilmGenrePrimaryKey getFilmGenrePrimaryKey() {
		return filmGenrePrimaryKey;
	}

	public void setFilmGenrePrimaryKey(FilmGenrePrimaryKey filmGenrePrimaryKey) {
		this.filmGenrePrimaryKey = filmGenrePrimaryKey;
	}
	
}
