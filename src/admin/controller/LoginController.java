package admin.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import admin.DAO.HibernateUtil;
import admin.bean.AdminBean;
import admin.mainFrame.Test;

public class LoginController {
	HibernateUtil hu = new HibernateUtil();
	SessionFactory sf = hu.getSessionFactory();
	
	public String validateLoginField(String username, String password) {
		String result = "";
		
		if (username.equals("") || username == null) {
			result = result + "Username must not empty\n";
		}
		if (password.equals("") || password.equals(null)) {
			result = result + "Password mustnot empty\n";
		}
		return result;
	}

	public boolean validateLoginInfo(String username, String password) {

		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(AdminBean.class);
		criteria.add(Restrictions.eq("username", username));

		AdminBean admin = new AdminBean();
		admin = (AdminBean) criteria.uniqueResult();
		
		if (admin != null) {
			if (password.equals(admin.getPassword())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
