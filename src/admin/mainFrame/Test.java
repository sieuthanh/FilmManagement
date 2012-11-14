package admin.mainFrame;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import movielisttab.MovieList;

import admin.view.AddMovieFrame;
import admin.view.AdminMainFrame;
import admin.view.AdminMainFrame2;
import admin.view.LoginPanel;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
//					.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(AddMovieFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(AddMovieFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(AddMovieFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(AddMovieFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		}
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		try { 
//		    UIManager.setLookAndFeel( 
//		            ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel()); 
//		} catch (Exception e) { 
//		    // ... 
//		} 
		new AdminMainFrame2().setVisible(true);
//		JFrame jf = new JFrame();
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setResizable(false);
//		jf.setLocationRelativeTo(null);
//		jf.add(new LoginPanel());
//		jf.pack();
	}
}
