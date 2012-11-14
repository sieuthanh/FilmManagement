package admin.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure("/admin/DAO/hibernate.cfg.xml")
					.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public boolean createTable(Class name){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(name);
	
		config.configure("/admin/DAO/hibernate.cfg.xml");
		new SchemaUpdate(config).execute(true,true);
		return true;
	}
}
