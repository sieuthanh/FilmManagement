package admin.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filmdirector")
public class FilmDirector {
	@Id
	private FilmDirectorPrimaryKey filmDirectorPrimaryKey;

	public FilmDirectorPrimaryKey getFilmDirectorPrimaryKey() {
		return filmDirectorPrimaryKey;
	}

	public void setFilmDirectorPrimaryKey(
			FilmDirectorPrimaryKey filmDirectorPrimaryKey) {
		this.filmDirectorPrimaryKey = filmDirectorPrimaryKey;
	}

}
