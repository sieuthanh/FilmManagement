package wom;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;

import org.hibernate.Session;

import admin.DAO.HibernateUtil;
import databasemanager.CartElement;
import databasemanager.DatabaseManager;
import databasemanager.VipMember;

/**
 *
 * @author Fight For Fun Group
 */
public class Main {
    public static void main(String arg[]) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {}
        DatabaseManager databaseManager = new DatabaseManager();
        if (!databaseManager.CheckConnection()) JOptionPane.showMessageDialog(null,"Cannot connect to database !" ,"Connecting Error",JOptionPane.ERROR_MESSAGE);
        else {
        	HibernateUtil hu = new HibernateUtil();
            Session session = hu.getSessionFactory().openSession();
            int sql = session.createSQLQuery("delete from cartelement").executeUpdate();
            session.beginTransaction().commit();
            JWindow splashscreen = new SplashScreen();
            
        }
    }
}
