package admin.controller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import databasemanager.Movie;


import admin.DAO.HibernateUtil;
import admin.view.CustomMessageDialog;

public class DeleteController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();
	
	public static boolean delete(String id) {
		Session session = sf.openSession();
        try {
            session.getTransaction().begin();
    		Criteria criteria = session.createCriteria(Movie.class);
    		criteria.add(Restrictions.eq("id", id));
            session.delete(criteria);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        // return false if error
        new CustomMessageDialog(null, true, "Oops...!", "An error occured!", CustomMessageDialog.MESSAGE);
        return false;
    }
}
