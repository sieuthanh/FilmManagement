package admin.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import admin.DAO.HibernateUtil;
import databasemanager.Company;

public class PersonSearchController {
	HibernateUtil hu;
	SessionFactory sf;

	public PersonSearchController(HibernateUtil hu, SessionFactory sf) {
		this.hu = hu;
		this.sf = sf;
	}

	public boolean personExist(String id, String name){
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Company.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("name",name));
		Company tmp = (Company) criteria.uniqueResult();
		if(tmp!=null){
			return true;
		}else{
			return false;
		}
	}
}
