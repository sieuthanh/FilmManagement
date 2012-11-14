package admin.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import wom.SplashScreen;
import admin.controller.SearchFilmController;
import databasemanager.DatabaseManager;
import databasemanager.Movie;

//VS4E -- DO NOT REMOVE THIS LINE!

public class AdminMainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JMenuItem jMenuItem0;
	private JMenu jMenu0;
	private JMenuBar jMenuBar0;

	private JLabel jLabel0;

	private JTabbedPane jTabbedPane1;
	private JButton jButton0;
	private JButton jButton1;
	private JButton jButton2;
	private JTextField jTextField1;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private SearchFilmController sfc = new SearchFilmController();
	private DatabaseManager database = new DatabaseManager();

	public AdminMainFrame() {
		initComponents();
	}

	private List<Movie> movieList = database.getMovieList();
	private AdminMovieList adminMovieList = new AdminMovieList(movieList);

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJButton2(), new Constraints(new Leading(583, 95, 12, 12),
				new Leading(481, 12, 12)));
		add(getJButton1(), new Constraints(new Leading(317, 100, 10, 10),
				new Leading(483, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(61, 88, 10, 10),
				new Leading(481, 10, 10)));
		add(getJTabbedPane1(), new Constraints(new Leading(21, 659, 10, 10),
				new Leading(68, 403, 12, 12)));
		add(getJLabel0(), new Constraints(new Leading(421, 10, 10),
				new Leading(12, 12, 12)));
		add(getJTextField1(), new Constraints(new Leading(470, 190, 10, 10),
				new Leading(10, 12, 12)));
		setJMenuBar(getJMenuBar0());
		setSize(706, 570);
	}

	// SEARCH FIELD
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.getDocument().addDocumentListener(
					new DocumentListener() {

						@Override
						public void removeUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							warn();
						}

						@Override
						public void insertUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							warn();
						}

						@Override
						public void changedUpdate(DocumentEvent e) {
							// TODO Auto-generated method stub
							warn();
						}

						public void warn() {
							String text = jTextField1.getText();
							int i = jTabbedPane1.getSelectedIndex();
							if (i == 0) {
								movieList = sfc.searchMovie(text);
								adminMovieList.resetModel(movieList);
							}
						}
					});

		}
		return jTextField1;
	}

	// EDIT
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("Edit");
		}
		return jButton2;
	}

	// DELETE
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("Delete");
			jButton1.addActionListener(this);
		}
		return jButton1;
	}

	// ADD
	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Add");
			jButton0.addActionListener(this);
		}
		return jButton0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Search");
		}
		return jLabel0;
	}

	// MAIN VIEW
	private JTabbedPane getJTabbedPane1() {
		if (jTabbedPane1 == null) {
			jTabbedPane1 = new JTabbedPane();
			
			jTabbedPane1.addTab("Movie", adminMovieList);

		}
		return jTabbedPane1;
	}

	// JMENU BAR
	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenu0());
		}
		return jMenuBar0;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("Menu");
			jMenu0.setBorderPainted(true);
			jMenu0.setOpaque(false);
			jMenu0.setRolloverEnabled(false);
			jMenu0.add(getJMenuItem0());
		}
		return jMenu0;
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("User View");
			jMenuItem0.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new SplashScreen();
				}
			});
		}
		return jMenuItem0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	// public static void main(String[] args) {
	// installLnF();
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	public AdminMainFrame(String title) {
		AdminMainFrame frame = new AdminMainFrame();
		frame.setDefaultCloseOperation(AdminMainFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.getContentPane().setPreferredSize(frame.getSize());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// }
	// });
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		if (ac.equals("Add")) {
			MovieInfoInput movieInfoInput = new MovieInfoInput();
			movieInfoInput.start();
		}
	}

}
