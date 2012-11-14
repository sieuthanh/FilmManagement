package admin.bean;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class FilmCompanyPrimaryKey implements Serializable {
	private String fid;
	private String sid;

	public FilmCompanyPrimaryKey() {
		// TODO Auto-generated constructor stub
	}

	public FilmCompanyPrimaryKey(String fid, String sid) {
		super();
		this.fid = fid;
		this.sid = sid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
