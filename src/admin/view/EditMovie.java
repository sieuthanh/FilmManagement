/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditLabour.java
 *
 * Created on Nov 1, 2011, 1:24:03 AM
 */
package admin.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import moviedisplaypanel.ReadFile;
import moviedisplaypanel.WriteFile;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import databasemanager.Company;
import databasemanager.DatabaseManager;
import databasemanager.HotMovie;
import databasemanager.Movie;
import databasemanager.Person;

import admin.bean.FilmGenre;
import admin.bean.FilmGenrePrimaryKey;
import admin.controller.CompanyController;
import admin.controller.FilmController;
import admin.controller.PersonController;

/**
 *
 * @author CodeBlue
 */
public class EditMovie extends javax.swing.JDialog {

    Highlighter simpleStripHL = HighlighterFactory.createSimpleStriping();
    private static String selectedID;
    String path = "data/Films/";
    static DatabaseManager databasemanager = new DatabaseManager();
    MyTableScroller ts = null;
    private final static String[] columnNames = {"Company List"};
    private final static String[] columnNames2 = {"Star List"};
    private final static String[] columnNames3 = {"Director List"};
    private static final Color evenColor = new Color(240, 255, 250);
    private final static DefaultTableModel companyModel = new DefaultTableModel(null, columnNames) {
    	@Override public Class<?> getColumnClass(int column) {
         	return (column==0)?Integer.class:Object.class;
    	}
    };
    private final static DefaultTableModel starModel = new DefaultTableModel(null, columnNames2) {
    	@Override public Class<?> getColumnClass(int column) {
         	return (column==0)?Integer.class:Object.class;
    	}
    };
    private final static DefaultTableModel directorModel = new DefaultTableModel(null, columnNames3) {
    	@Override public Class<?> getColumnClass(int column) {
         	return (column==0)?Integer.class:Object.class;
    	}
    };
    private final static JXTable jxCompany = new JXTable(companyModel) {
    	@Override public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
    		Component c = super.prepareRenderer(tcr, row, column);
    		if(isRowSelected(row)) {
    			c.setForeground(getSelectionForeground());
    			c.setBackground(getSelectionBackground());
    		}else{
    			c.setForeground(getForeground());
    			c.setBackground((row%2==0)?evenColor:getBackground());
    		}
    		return c;
    	}
    };
    private final static JXTable jxDirector = new JXTable(directorModel) {
    	@Override public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
    		Component c = super.prepareRenderer(tcr, row, column);
    		if(isRowSelected(row)) {
    			c.setForeground(getSelectionForeground());
    			c.setBackground(getSelectionBackground());
    		}else{
    			c.setForeground(getForeground());
    			c.setBackground((row%2==0)?evenColor:getBackground());
    		}
    		return c;
    	}
    };
    private final static JXTable jxStar = new JXTable(starModel) {
    	@Override public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
    		Component c = super.prepareRenderer(tcr, row, column);
    		if(isRowSelected(row)) {
    			c.setForeground(getSelectionForeground());
    			c.setBackground(getSelectionBackground());
    		}else{
    			c.setForeground(getForeground());
    			c.setBackground((row%2==0)?evenColor:getBackground());
    		}
    		return c;
    	}
    };
    
    
    /** Creates new form EditLabour */
    public EditMovie(String selectedID) {
        super();
        this.selectedID = selectedID;
		LinkedList listcompany = databasemanager.getCompanyByMovie2(selectedID);
		for (int i = 0; i < listcompany.size(); i++) {
			Company com = (Company) listcompany.get(i);
			companyModel.addRow(new Object[] { com.getName()});
		}
		LinkedList liststar = databasemanager.getStarByMovie2(selectedID);
		for (int i = 0; i < liststar.size(); i++) {
			Person person = (Person) liststar.get(i);
			starModel.addRow(new Object[] { person.getName()});
		}
		LinkedList listdir = databasemanager.getDirectorByMovie2(selectedID);
		for (int i = 0; i < listdir.size(); i++) {
			Person person2 = (Person) listdir.get(i);
			directorModel.addRow(new Object[] { person2.getName()});
		}
        initComponents();
        Image icon = getToolkit().getImage(getClass().getResource("/images/app_icon_32.png"));
        setIconImage(icon);
        setLocation(210, 50);
        setVisible(true);
        customizeCompanyTable();
        customizeStarTable();
        customizeDirectorTable();
        fillFields();
    }

    // fill fields on dialog
    private void fillFields() {
    	path = path + selectedID + "/";
    	ReadFile rf = new ReadFile(path + "Short_Des.txt");
    	ReadFile rf2 = new ReadFile(path + "Long_Des.txt");
        Movie movie = FilmController.getMovie(selectedID);
		LinkedList gen = databasemanager.getMovieGenres(selectedID);
		String genre = "";
		for (int i = 0; i < gen.size(); i++) {
			genre += (String) gen.get(i) + ", ";
		}
		genre = genre.substring(0, genre.length() - 2);
        boolean hm = FilmController.checkHotMovie(selectedID);
        boolean nm = FilmController.checkNewMovie(selectedID);
        txtID.setText(selectedID);
        txtName.setText(movie.getName());
        txtYear.setText(String.valueOf(movie.getYear()));
        txtCountry.setText(movie.getCountry());
        txtRatting.setText(String.valueOf(movie.getRating()));
        txtRuntime.setText(String.valueOf(movie.getRuntime()));
        txtGenre.setText(genre);
		taShortStory.setText(rf.ReadAllFile());
		taLongStory.setText(rf2.ReadAllFile());

        if (hm) {
            chkHotMovie.setSelected(true);
        }
        if (nm) {
            chkNewMovie.setSelected(true);
        }

    }
    
    private void customizeCompanyTable() {
        jxCompany.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxCompany.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxCompany);
        jxCompany.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if (jxCompany.getSelectedRow() != -1) {
                    btnDelCompany.setEnabled(true);
//                    int realRow = jxCompany.convertRowIndexTocompanyModel(jxCompany.getSelectedRow());
//                    String id = (String) (jxCompany.getcompanyModel().getValueAt(realRow, 0));
                } else {
                    btnDelCompany.setEnabled(false);
                }
            }
        });
        //jxCompany.getColumn(0).setResizable(true);
        // center the table header
        TableColumnModel columncompanyModel = jxCompany.getColumnModel();
        jxCompany.setTableHeader(new JXTableHeader(columncompanyModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxCompany.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    private void customizeStarTable() {
        jxStar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxStar.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxStar);
        jxStar.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if (jxStar.getSelectedRow() != -1) {
                    btnDelStar.setEnabled(true);
//                    int realRow = jxStar.convertRowIndexToStarModel(jxStar.getSelectedRow());
//                    String id = (String) (jxStar.getStarModel().getValueAt(realRow, 0));
                } else {
                    btnDelStar.setEnabled(false);
                }
            }
        });
        //jxStar.getColumn(0).setResizable(true);
        // center the table header
        TableColumnModel columnStarModel = jxStar.getColumnModel();
        jxStar.setTableHeader(new JXTableHeader(columnStarModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxStar.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    private void customizeDirectorTable() {
        jxDirector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxDirector.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxDirector);
        jxDirector.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if (jxDirector.getSelectedRow() != -1) {
                    btnDelDirector.setEnabled(true);
//                    int realRow = jxDirector.convertRowIndexToDirectorModel(jxDirector.getSelectedRow());
//                    String id = (String) (jxDirector.getDirectorModel().getValueAt(realRow, 0));
                } else {
                    btnDelDirector.setEnabled(false);
                }
            }
        });
        //jxDirector.getColumn(0).setResizable(true);
        // center the table header
        TableColumnModel columnDirectorModel = jxDirector.getColumnModel();
        jxDirector.setTableHeader(new JXTableHeader(columnDirectorModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxDirector.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }

    protected static void reloadCompanyTable() {
		LinkedList listcompany = databasemanager.getCompanyByMovie2(selectedID);
		for (int i = 0; i < listcompany.size(); i++) {
			Company com = (Company) listcompany.get(i);
			companyModel.addRow(new Object[] { com.getName()});
		}
        jxCompany.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    protected static void reloadStarTable() {
		LinkedList liststar = databasemanager.getStarByMovie2(selectedID);
		for (int i = 0; i < liststar.size(); i++) {
			Person person = (Person) liststar.get(i);
			starModel.addRow(new Object[] { person.getName()});
		}
        jxStar.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    protected static void reloadDirectorTable() {
		LinkedList listdir = databasemanager.getDirectorByMovie2(selectedID);
		for (int i = 0; i < listdir.size(); i++) {
			Person person2 = (Person) listdir.get(i);
			directorModel.addRow(new Object[] { person2.getName()});
		}
        jxDirector.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	buttonGroup1 = new javax.swing.ButtonGroup();
        
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        
        jLabelHeading = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelYear = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jLabelRank = new javax.swing.JLabel();
        jLabelRuntime = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabelRatting = new javax.swing.JLabel();
        jLabelPerson = new javax.swing.JLabel();
        jLabelCompany = new javax.swing.JLabel();
        jLabelGender = new javax.swing.JLabel();
        jLabelBirth = new javax.swing.JLabel();
        jLabelStar = new javax.swing.JLabel();
        jLabelDirector = new javax.swing.JLabel();
        jLabelBorn = new javax.swing.JLabel();
        jLabelGenre = new javax.swing.JLabel();
        
        txtID = new javax.swing.JTextField();
        txtYear = new javax.swing.JTextField();
        txtRatting = new javax.swing.JTextField();
        txtRuntime = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtCountry = new javax.swing.JTextField();
        txtGenre = new javax.swing.JTextField();
        
        chkHotMovie = new javax.swing.JCheckBox();
        chkNewMovie = new javax.swing.JCheckBox();

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        
        taStar = new javax.swing.JTextArea();
        taDirector = new javax.swing.JTextArea();
        taShortStory = new javax.swing.JTextArea();
        taLongStory = new javax.swing.JTextArea();
        taCompany = new javax.swing.JTextArea();

        btnAddStar = new javax.swing.JButton();
        btnDelStar = new javax.swing.JButton();
        btnAddDirector = new javax.swing.JButton();
        btnDelDirector = new javax.swing.JButton();
        btnAddCompany = new javax.swing.JButton();
        btnDelCompany = new javax.swing.JButton(); 
        btnUpdate = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        
        //jxCompany = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update movie");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabelHeading.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabelHeading.setForeground(new java.awt.Color(0, 102, 153));
        jLabelHeading.setText("Update Movie");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Movie Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        jLabelId.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelId.setForeground(new java.awt.Color(51, 51, 51));
        jLabelId.setText("ID");

        jLabelName.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelName.setForeground(new java.awt.Color(51, 51, 51));
        jLabelName.setText("Name");

        jLabelYear.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelYear.setForeground(new java.awt.Color(51, 51, 51));
        jLabelYear.setText("Year");

        jLabelCountry.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCountry.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCountry.setText("Country");

        jLabelRank.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelRank.setForeground(new java.awt.Color(51, 51, 51));
        jLabelRank.setText("Rank");
        
        jLabelRuntime.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelRuntime.setForeground(new java.awt.Color(51, 51, 51));
        jLabelRuntime.setText("Runtime");
        
        jLabelGenre.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelGenre.setForeground(new java.awt.Color(51, 51, 51));
        jLabelGenre.setText("Genre");

        txtID.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtID.setForeground(new java.awt.Color(0, 0, 102));

        txtYear.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtYear.setForeground(new java.awt.Color(0, 0, 102));
        
        txtRatting.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtRatting.setForeground(new java.awt.Color(0, 0, 102));
        
        txtRuntime.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtRuntime.setForeground(new java.awt.Color(0, 0, 102));

        txtName.setForeground(new java.awt.Color(0, 0, 102));
        txtName.setFont(new java.awt.Font("Tahoma", 0, 12));

        txtCountry.setForeground(new java.awt.Color(0, 0, 102));
        txtCountry.setFont(new java.awt.Font("Tahoma", 0, 12));
        
        txtGenre.setForeground(new java.awt.Color(0, 0, 102));
        txtGenre.setFont(new java.awt.Font("Tahoma", 0, 12));

        chkHotMovie.setBackground(new java.awt.Color(255, 255, 255));
        chkHotMovie.setText("Hot Movie");
        chkHotMovie.setFocusable(false);

        chkNewMovie.setBackground(new java.awt.Color(255, 255, 255));
        chkNewMovie.setText("New Movie");
        chkNewMovie.setFocusable(false);
        
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("(s)");

        jLabelRatting.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelRatting.setForeground(new java.awt.Color(51, 51, 51));
        jLabelRatting.setText("Ratting");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCountry)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelId)
                            .addComponent(jLabelYear)
                            .addComponent(jLabelRank)
                            .addComponent(jLabelName)
                            .addComponent(jLabelRatting)
                            .addComponent(jLabelRuntime)
                            .addComponent(jLabelGenre))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(txtRatting, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addComponent(txtRuntime, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addComponent(txtGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)                        	.addGroup(jPanel2Layout.createSequentialGroup()
                        			.addComponent(chkHotMovie)
                        			.addComponent(chkNewMovie))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCountry, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel17))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18))))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelYear)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCountry)
                    .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRank)
                    .addComponent(chkHotMovie)
                    .addComponent(chkNewMovie))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRatting)
                    .addComponent(txtRatting))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRuntime)
                    .addComponent(txtRuntime))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelGenre)
                    .addComponent(txtGenre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Person/Company Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        jLabelPerson.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelPerson.setForeground(new java.awt.Color(51, 51, 51));
        jLabelPerson.setText("Person");

        jLabelCompany.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCompany.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCompany.setText("Company");
        
        taStar.setColumns(20);
        taStar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        taStar.setForeground(new java.awt.Color(0, 0, 102));
        taStar.setLineWrap(true);
        taStar.setRows(3);
        taStar.setWrapStyleWord(true);
        //jScrollPane3.setViewportView(taStar);
        
        jxStar.setHighlighters(simpleStripHL);
        jxStar.setShowGrid(false);
        jScrollPane3.setViewportView(jxStar);
        
        taDirector.setColumns(20);
        taDirector.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        taDirector.setForeground(new java.awt.Color(0, 0, 102));
        taDirector.setLineWrap(true);
        taDirector.setRows(3);
        taDirector.setWrapStyleWord(true);
        //jScrollPane4.setViewportView(taDirector);
        
        jxDirector.setHighlighters(simpleStripHL);
        jxDirector.setShowGrid(false);
        jScrollPane4.setViewportView(jxDirector);
        
        taCompany.setColumns(20);
        taCompany.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        taCompany.setForeground(new java.awt.Color(0, 0, 102));
        taCompany.setLineWrap(true);
        taCompany.setRows(3);
        taCompany.setWrapStyleWord(true);
        //jScrollPane5.setViewportView(taCompany);
               
        //jxCompany.setcompanyModel(companyModel);
        jxCompany.setHighlighters(simpleStripHL);
        jxCompany.setShowGrid(false);
        jScrollPane5.setViewportView(jxCompany);

        jLabelGender.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelGender.setForeground(new java.awt.Color(51, 51, 51));
        jLabelGender.setText("Gender");

        jLabelBirth.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelBirth.setForeground(new java.awt.Color(51, 51, 51));
        jLabelBirth.setText("Birthday");

        jLabelStar.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelStar.setForeground(new java.awt.Color(51, 51, 51));
        jLabelStar.setText("Star");

        jLabelDirector.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelDirector.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDirector.setText("Director");

        jLabelBorn.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelBorn.setForeground(new java.awt.Color(51, 51, 51));
        jLabelBorn.setText("Born");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelStar)
                            .addComponent(jLabelDirector)
                            .addComponent(jLabelCompany))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addGroup(jPanel3Layout.createSequentialGroup()
                        	.addComponent(btnAddStar)
                        	.addComponent(btnDelStar))
                       		.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                       		.addGroup(jPanel3Layout.createSequentialGroup()
                       		.addComponent(btnAddDirector)
                       		.addComponent(btnDelDirector))
                       		.addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                       		.addGroup(jPanel3Layout.createSequentialGroup()
                       		.addComponent(btnAddCompany)
                       		.addComponent(btnDelCompany)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStar)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddStar)
                    .addComponent(btnDelStar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDirector)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDirector)
                    .addComponent(btnDelDirector))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                	.addComponent(jLabelCompany)
                	.addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                	.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCompany)
                    .addComponent(btnDelCompany))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        btnAddStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnAddStar.setText("Add");
        btnAddStar.setMargin(new java.awt.Insets(2, 9, 2, 9));
        btnAddStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStarActionPerformed(evt);
            }
        });
        
        btnDelStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDelStar.setText("Del");
        btnDelStar.setEnabled(false);
        btnDelStar.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnDelStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelStarActionPerformed(evt);
            }
        });
        
        btnAddDirector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnAddDirector.setText("Add");
        btnAddDirector.setMargin(new java.awt.Insets(2, 9, 2, 9));
        btnAddDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDirectorActionPerformed(evt);
            }
        });
        
        btnDelDirector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDelDirector.setText("Del");
        btnDelDirector.setEnabled(false);
        btnDelDirector.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnDelDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelDirectorActionPerformed(evt);
            }
        });
        
        btnAddCompany.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnAddCompany.setText("Add");
        btnAddCompany.setMargin(new java.awt.Insets(2, 9, 2, 9));
        btnAddCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompanyActionPerformed(evt);
            }
        });

        btnDelCompany.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDelCompany.setText("Del");
        btnDelCompany.setEnabled(false);
        btnDelCompany.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnDelCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelCompanyActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Short Story", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        taShortStory.setColumns(20);
        taShortStory.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        taShortStory.setForeground(new java.awt.Color(0, 0, 102));
        taShortStory.setLineWrap(true);
        taShortStory.setRows(5);
        taShortStory.setWrapStyleWord(true);
        jScrollPane1.setViewportView(taShortStory);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Long Story", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(153, 0, 0))); // NOI18N

        taLongStory.setColumns(20);
        taLongStory.setFont(new java.awt.Font("Verdana", 0, 12));
        taLongStory.setForeground(new java.awt.Color(0, 0, 102));
        taLongStory.setLineWrap(true);
        taLongStory.setRows(5);
        taLongStory.setWrapStyleWord(true);
        jScrollPane2.setViewportView(taLongStory);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(320, 320, 320)
                        .addComponent(jLabelHeading))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(btnUpdate)
                        .addGap(60, 60, 60)
                        .addComponent(btnExit)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelHeading)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnExit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void btnDelCompanyActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void btnAddCompanyActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame("Add Company");
		jf.setVisible(true);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setResizable(false);
		//jf.setSize(800, 100);
		jf.setVisible(true);
		jf.setLocation(230,100);
		jf.add(new AddCompany(selectedID));
		jf.pack();
	}

	protected void btnDelDirectorActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void btnAddDirectorActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame("Add Director");
		jf.setVisible(true);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setResizable(false);
		//jf.setSize(800, 100);
		jf.setVisible(true);
		jf.setLocation(230,100);
		jf.add(new AddDirector(selectedID));
		jf.pack();
	}

	protected void btnDelStarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	protected void btnAddStarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame("Add Star");
		jf.setVisible(true);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setResizable(false);
		//jf.setSize(800, 100);
		jf.setVisible(true);
		jf.setLocation(230,100);
		jf.add(new AddStar(selectedID));
		jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		jf.pack();
	}

	//<editor-fold defaultstate="collapsed" desc="events on buttons"> 
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    	JTextComponent[] tcp = {txtID, txtName, txtYear, txtRatting, txtRuntime, 
    			txtCountry, taShortStory, taLongStory};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        String id  = txtID.getText();
        String name = txtName.getText();
        String country = txtCountry.getText();
        String shortStory = taShortStory.getText();
        String longStory = taLongStory.getText();
        int year =  Integer.parseInt(txtYear.getText());
        Integer runtime = null;
        Float rating = null;
        
        boolean hotMovie = false;
        if (chkHotMovie.isSelected()) {
            hotMovie = true;
        }
        boolean newMovie = false;
        if (chkNewMovie.isSelected()) {
            newMovie = true;
        }
        String star = taStar.getText();
        String director = taDirector.getText();

        if (!txtRatting.getText().trim().equals("")) {
            if (!Validator.validateFloatField(txtRatting)) {
                return;
            }
            rating = new Float(txtRatting.getText());
        }

        if (!txtRuntime.getText().trim().equals("")) {
            if (!Validator.validateFloatField(txtRuntime)) {
                return;
            }
            runtime = new Integer(txtRuntime.getText());
        }

        if (!txtYear.getText().trim().equals("")) {
            if (!Validator.validateYear(txtYear)) {
                return;
            }
        }
        path = path + selectedID + "/";
        if (FilmController.update(selectedID, name, year, runtime, rating, country )) {
        	new WriteFile(path + "Short_Des.txt", taShortStory.getText());
        	new WriteFile(path + "Long_Des.txt", taLongStory.getText());
            new CustomMessageDialog(null, true, "Done!", "Updated sucessfully!", CustomMessageDialog.DONE);
            AdminMovieList2.reloadTable();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed
    
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    //</editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnAddStar;
    private javax.swing.JButton btnDelStar;
    private javax.swing.JButton btnAddDirector;
    private javax.swing.JButton btnDelDirector;
    private javax.swing.JButton btnAddCompany;
    private javax.swing.JButton btnDelCompany;
    private javax.swing.ButtonGroup buttonGroup1;
    
    private javax.swing.JCheckBox chkHotMovie;
    private javax.swing.JCheckBox chkNewMovie;
    
    private javax.swing.JLabel jLabelHeading;
    private javax.swing.JLabel jLabelBirth;
    private javax.swing.JLabel jLabelStar;
    private javax.swing.JLabel jLabelDirector;
    private javax.swing.JLabel jLabelBorn;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabelRatting;
    private javax.swing.JLabel jLabelRuntime;    
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelYear;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelRank;
    private javax.swing.JLabel jLabelPerson;
    private javax.swing.JLabel jLabelCompany;
    private javax.swing.JLabel jLabelGender;
    private javax.swing.JLabel jLabelGenre;
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    
    private javax.swing.JTextArea taShortStory;
    private javax.swing.JTextArea taLongStory;
    private javax.swing.JTextArea taStar;
    private javax.swing.JTextArea taDirector;
    private javax.swing.JTextArea taCompany;
    
    private javax.swing.JTextField txtYear;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtRatting;
    private javax.swing.JTextField txtRuntime;
    private javax.swing.JTextField txtGenre;
    
   // private org.jdesktop.swingx.JXTable jxCompany;
    // End of variables declaration//GEN-END:variables
}
