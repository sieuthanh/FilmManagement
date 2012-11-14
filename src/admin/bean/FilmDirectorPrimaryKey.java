package admin.bean;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class FilmDirectorPrimaryKey implements Serializable {
	private String fid;
	private String did;

	public FilmDirectorPrimaryKey(String fid, String did) {
		super();
		this.fid = fid;
		this.did = did;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
}
