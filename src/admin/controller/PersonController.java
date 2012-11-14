package admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import databasemanager.Person;

import admin.DAO.HibernateUtil;
import admin.view.CustomMessageDialog;


public class PersonController {
	static HibernateUtil hu = new HibernateUtil();
	static SessionFactory sf = hu.getSessionFactory();
	
	public static Vector<Person> searchPerson(String keyword) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Person.class);
		keyword = keyword.trim();

		Disjunction or = Restrictions.disjunction();

		or.add(Restrictions.like("name", "%" + keyword + "%"));
		or.add(Restrictions.like("day", "%" + keyword + "%"));
		or.add(Restrictions.like("year", "%" + keyword + "%"));
		or.add(Restrictions.like("sex", "%" + keyword + "%"));
		or.add(Restrictions.like("born", "%" + keyword + "%"));
		criteria.add(or);
		Vector vt = new Vector();
		List result = new Vector<Person>();
		result = criteria.list();
		for (Object o : result) {
            Person person = (Person) o;
            Vector temp = new Vector();
            temp.add(person.getId());
            temp.add(person.getName());
            vt.add(temp);
        }
		return vt;
	}

	public static Vector<Person> searchPerson2(String keyword) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(Person.class);
		keyword = keyword.trim();

		Disjunction or = Restrictions.disjunction();

		or.add(Restrictions.like("name", "%" + keyword + "%"));
		or.add(Restrictions.like("day", "%" + keyword + "%"));
		or.add(Restrictions.like("year", "%" + keyword + "%"));
		or.add(Restrictions.like("sex", "%" + keyword + "%"));
		or.add(Restrictions.like("born", "%" + keyword + "%"));
		criteria.add(or);
		Vector vt = new Vector();
		List result = new Vector<Person>();
		result = criteria.list();
		for (Object o : result) {
            Person person = (Person) o;
            Vector temp = new Vector();
            temp.add(person.getName());
            temp.add(person.getSex());
            temp.add(person.getBorn());
            temp.add(person.getYear());
            temp.add(person.getDay());
            temp.add(person.getId());
            temp.add(person.getIsdirector());
            temp.add(person.getIsstar());
            vt.add(temp);
        }
		return vt;
	}
	
	private static boolean isFloat(String a) {

		try {
			Float f = Float.parseFloat(a);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	private static boolean isInteger(String a) {
		try {
			int i = Integer.parseInt(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public static Vector getPersonList() {
        Vector vt = new Vector();
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from Person").list();
            for (Object o : list) {
                Person person = (Person) o;
                Vector temp = new Vector();
                temp.add(person.getName());
                temp.add(person.getSex());
                temp.add(person.getBorn());
                temp.add(person.getYear());
                temp.add(person.getDay());
                temp.add(person.getId());
                temp.add(person.getIsdirector());
                temp.add(person.getIsstar());
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
	
	public static Vector getPersonList2() {
        Vector vt = new Vector();
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            List list = session.createQuery("from Person").list();
            for (Object o : list) {
                Person person = (Person) o;
                Vector temp = new Vector();
                temp.add(person.getId());
                temp.add(person.getName());
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
	
	public static Person getPerson(String id) {
        Person person = null;
        Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            person = (Person) session.get(Person.class, id);
            //session.getTransaction().commit();
            //session.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return person;
    }
	
    public static boolean delete(String id) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Person person = (Person) session.get(Person.class, id);
            session.delete(person);
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
    
    public static boolean update(String id, String name, String sex, String day, String year,
			String born, String isdirector, String isstar) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Person person = (Person) session.get(Person.class, id);
            person.setName(name);
            person.setYear(year);
            person.setSex(sex);
            person.setDay(day);
            person.setYear(year);
            person.setBorn(born);
            person.setIsdirector(isdirector);
            person.setIsstar(isstar);
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
    
    public static boolean insert(String id, String name, String sex, String day, String year,
			String born, String isdirector, String isstar) {
    	Session session = sf.openSession();
        try {
            session.getTransaction().begin();
            Person person = new Person(id, name, sex, day, year, born, isdirector, isstar);
            session.save(person);
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
