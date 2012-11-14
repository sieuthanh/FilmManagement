package databasemanager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	
	@Id
	@Column(name ="id")
    String id;
	@Column(name = "name")
    String name;
    public Company(String id,String name){
        this.id=id;
        this.name=name;
    }

    public Company() {
		// TODO Auto-generated constructor stub
	}
    public Company(String name){
    	this.name = name;
    }
	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    
}
