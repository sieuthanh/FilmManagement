package admin.bean;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class FilmGenrePrimaryKey implements Serializable{
	private String fid;
	private String genre;
	public FilmGenrePrimaryKey(String fid, String genre) {
		super();
		this.fid = fid;
		this.genre = genre;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
}
