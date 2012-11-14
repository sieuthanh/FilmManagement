package admin.view;



import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.JTextComponent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import admin.Crawler.ImdbCrawlerMovie;
import admin.DAO.HibernateUtil;
import admin.bean.FilmCompany;
import admin.bean.FilmCompanyPrimaryKey;
import admin.bean.FilmDirector;
import admin.bean.FilmDirectorPrimaryKey;
import admin.bean.FilmGenre;
import admin.bean.FilmGenrePrimaryKey;
import admin.bean.FilmStar;
import admin.bean.FilmStarPrimaryKey;
import admin.controller.CompanySearchController;
import admin.controller.FilmController;
import databasemanager.Company;
import databasemanager.Movie;
import databasemanager.Person;

public class AddMovieFrame2 extends javax.swing.JFrame implements ActionListener {

	/**
	 * Creates new form AddMovieFrame
	 */
	private String selectedID;
	public AddMovieFrame2() {
		super();
		initComponents();
		Image icon = getToolkit().getImage(getClass().getResource("/images/app_icon_32.png"));
        setIconImage(icon);
        setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		moviePanel = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		nameTextField = new javax.swing.JTextField();
		yearTextField = new javax.swing.JTextField();
		runtimeTextField = new javax.swing.JTextField();
		ratingTextField = new javax.swing.JTextField();
		countryTextFiled = new javax.swing.JTextField();
		shortStoryPanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		shortStoryTextArea = new javax.swing.JTextArea();
		longStoryPanel = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		longStoryArea = new javax.swing.JTextArea();
		OkButton = new javax.swing.JButton();
		CancelButton = new javax.swing.JButton();


		moviePanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Movie"));

		jLabel2.setText("Name");

		jLabel3.setText("Year");

		jLabel4.setText("Runtime");

		jLabel5.setText("Rating");

		jLabel6.setText("Country");


		javax.swing.GroupLayout moviePanelLayout = new javax.swing.GroupLayout(
				moviePanel);
		moviePanel.setLayout(moviePanelLayout);
		moviePanelLayout
				.setHorizontalGroup(moviePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								moviePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel1)
														.addComponent(jLabel2)
														.addComponent(jLabel3))
										.addGap(18, 18, 18)
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																yearTextField,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																206,
																Short.MAX_VALUE)
														.addComponent(
																nameTextField))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																moviePanelLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				jLabel5)
																		.addComponent(
																				jLabel4))
														.addComponent(
																jLabel6,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(18, 18, 18)
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																runtimeTextField)
														.addComponent(
																ratingTextField)
														.addComponent(
																countryTextFiled,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																194,
																Short.MAX_VALUE))
										.addGap(26, 26, 26)));
		moviePanelLayout
				.setVerticalGroup(moviePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								moviePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(jLabel4)
														.addComponent(
																runtimeTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel2)
														.addComponent(jLabel5)
														.addComponent(
																nameTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																ratingTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												moviePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel3)
														.addComponent(jLabel6)
														.addComponent(
																yearTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																countryTextFiled,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(26, Short.MAX_VALUE)));

		shortStoryPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Short Story"));

		shortStoryTextArea.setColumns(20);
		shortStoryTextArea.setRows(5);
		shortStoryTextArea.setLineWrap(true);
		jScrollPane1.setViewportView(shortStoryTextArea);

		javax.swing.GroupLayout shortStoryPanelLayout = new javax.swing.GroupLayout(
				shortStoryPanel);
		shortStoryPanel.setLayout(shortStoryPanelLayout);
		shortStoryPanelLayout
				.setHorizontalGroup(shortStoryPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								shortStoryPanelLayout
										.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												536,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		shortStoryPanelLayout.setVerticalGroup(shortStoryPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						shortStoryPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										116, Short.MAX_VALUE)));

		longStoryPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Long Story"));

		longStoryArea.setColumns(20);
		longStoryArea.setRows(5);
		longStoryArea.setLineWrap(true);
		jScrollPane2.setViewportView(longStoryArea);

		javax.swing.GroupLayout longStoryPanelLayout = new javax.swing.GroupLayout(
				longStoryPanel);
		longStoryPanel.setLayout(longStoryPanelLayout);
		longStoryPanelLayout
				.setHorizontalGroup(longStoryPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								longStoryPanelLayout
										.createSequentialGroup()
										.addGap(0, 28, Short.MAX_VALUE)
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												538,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		longStoryPanelLayout.setVerticalGroup(longStoryPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						longStoryPanelLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										122, Short.MAX_VALUE)));

		OkButton.setText("OK");
		CancelButton.setText("Cancel");
		CancelButton.addActionListener(this);
		OkButton.addActionListener(this);
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(moviePanel, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(shortStoryPanel,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(longStoryPanel,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(OkButton,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										67,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(33, 33, 33).addComponent(CancelButton)
								.addGap(96, 96, 96)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(moviePanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(shortStoryPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(longStoryPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										52, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(OkButton)
												.addComponent(CancelButton))
								.addGap(19, 19, 19)));

		pack();
	}// </editor-fold>

	@Override
	public void actionPerformed(ActionEvent e) {
        // TODO add your handling code here:
    	String ac = e.getActionCommand();
    	if (ac.equals("OK")) {
        String name = nameTextField.getText();
        int year =  Integer.parseInt(yearTextField.getText());
        int runtime = Integer.parseInt(runtimeTextField.getText());
        float rating = Float.parseFloat(ratingTextField.getText());
        String country = countryTextFiled.getText();

        if (FilmController.insert("1", name, year, runtime, rating, country )) {
            new CustomMessageDialog(null, true, "Done!", "Insert sucessfully!", CustomMessageDialog.DONE);
            AdminMovieList2.reloadTable();
        }
    	}
    	if (ac.equals("Cancel")) {
			setVisible(false);
			dispose();

		}
    }//GEN-LAST:event_btnUpdateActionPerformed

	// Variables declaration - do not modify
	private javax.swing.JButton CancelButton;
	private javax.swing.JButton OkButton;
	private javax.swing.JTextField countryTextFiled;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea longStoryArea;
	private javax.swing.JPanel longStoryPanel;
	private javax.swing.JPanel moviePanel;
	private javax.swing.JTextField nameTextField;
	private javax.swing.JTextField ratingTextField;
	private javax.swing.JTextField runtimeTextField;
	private javax.swing.JPanel shortStoryPanel;
	private javax.swing.JTextArea shortStoryTextArea;
	private javax.swing.JTextField yearTextField;

	

	// End of variables declaration

}

