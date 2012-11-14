/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controller;

import java.util.List;
import java.util.Vector;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import databasemanager.Company;
import databasemanager.Movie;

import admin.DAO.HibernateUtil;
import admin.view.CustomMessageDialog;

/**
 *
 * @author CodeBlue
 */
public class CompanyController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();

    // load data, then pass it to company table model
    public static Vector loadCompany() {
    	Vector vt = new Vector();
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from Company").list();
            for (Object o : list) {
                Company company = (Company) o;
                Vector temp = new Vector();
                temp.add(company.getId());
                temp.add(company.getName());
                vt.add(temp);
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return vt;
    }

    // add new company
    public static boolean insert(String companyName, String id){//, String movieName) {
        if (checkExist(companyName)) {
            new CustomMessageDialog(null, true, "Oops...!", "The company's name existed", CustomMessageDialog.MESSAGE);
            return false;
        }
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Company company = new Company(companyName);
            //Movie movie = (Movie) session.get(Movie.class, movieName);
            //company.setId(movie.getId());
            company.setId(id);
            session.save(company);
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

    // update a company
    public static boolean update(String id, String name) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Company company = (Company) session.get(Company.class, id);
            company.setName(name);
            session.update(company);
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

    // delete a company
    public static boolean delete(String id) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Company company = (Company) session.get(Company.class, id);
            new CustomMessageDialog(null, true, "Warning", "<html>All records of movie on this company will be deleted. Still continue?</html>", CustomMessageDialog.CONFIRM);
            if (CustomMessageDialog.STATUS == CustomMessageDialog.CANCEL) {
                return false;
            }
            session.delete(company);
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

    // get a company object by id
    public static Company getCompany(String id) {
        Company company = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            company = (Company) session.get(Company.class, id);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return company;
    }

    // get a company object by name
    public static Company getcompanyByName(String name) {
        Company company = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from company b where b.name = ?");
            query.setString(0, name);
            company = (Company) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
        return company;
    }

    // check  a company's name exist
    public static boolean checkExist(String name) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from company").list();
            for (Object o : list) {
                Company company = (Company) o;
                if (company.getName().equals(name)) {
                    return true;
                }
            }
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return false;
    }
}
