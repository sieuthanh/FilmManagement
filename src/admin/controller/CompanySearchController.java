package admin.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import databasemanager.Company;

import admin.DAO.HibernateUtil;

public class CompanySearchController {
	HibernateUtil hu;
	SessionFactory sf;

	public CompanySearchController(HibernateUtil hu, SessionFactory sf) {
		this.hu = hu;
		this.sf = sf;
	}

	public boolean companyExist(Company company){
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Company.class);
		criteria.add(Restrictions.eq("id", company.getId()));
		criteria.add(Restrictions.eq("name",company.getName()));
		Company tmp = (Company) criteria.uniqueResult();
		if(tmp!=null){
			return true;
		}else{
			return false;
		}
	}
}
