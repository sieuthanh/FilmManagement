/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * disksAndcompanys.java
 *
 * Created on Nov 5, 2011, 6:19:26 PM
 */
package admin.view;


import java.awt.Color;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import databasemanager.Company;
import databasemanager.DatabaseManager;
import databasemanager.Disk;
import databasemanager.MoneyUnit;
import databasemanager.Movie;
import databasemanager.Person;

import admin.controller.CompanyController;
import admin.controller.DiskController;

/**
 *
 * @author CodeBlue
 */
public class CompanyAndDisk extends javax.swing.JPanel {

    MyCompanyTableModel companyModel = new MyCompanyTableModel();
    MyDiskTableModel diskModel = new MyDiskTableModel();
    Highlighter simpleStripHL = HighlighterFactory.createSimpleStriping();
    MyTableScroller ts = null;
    DatabaseManager databaseManager = new DatabaseManager();
    // ensure only 1 component listener is added to jtable
    // so that when delete an item, table will not go to bottom
    private static boolean componentListenerIsOn = false;

    /** Creates new form disksAndcompanys */
    public CompanyAndDisk() {
        initComponents();
        customizecompanyTable();
        customizediskTable();
    }

    //<editor-fold defaultstate="collapsed" desc="customize table">
    // Customize company table
    private void customizecompanyTable() {
        jxcompany.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxcompany.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxcompany);
        jxcompany.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            	String name = "";
                JTextComponent[] tcp = {txtCid, txtCompanyName, txtCMovie};
                if (jxcompany.getSelectedRow() != -1) {
                    btnCompanyDelete.setEnabled(true);
                    btnCompanyUpdate.setEnabled(true);
                    int realRow = jxcompany.convertRowIndexToModel(jxcompany.getSelectedRow());
                    String id = (String) (jxcompany.getModel().getValueAt(realRow, 0));
                    Company company = CompanyController.getCompany(id);
                    LinkedList<Movie> movie = databaseManager.getMovieByCompany2(id);                   
            		for (int i = 0; i < movie.size(); i++) {
            			name += movie.get(i).getName() + ", ";
            		}
            		System.out.print(name);

            		txtCMovie.setText(name);
                    txtCid.setText(company.getId());
                    txtCompanyName.setText(company.getName());
                } else {
                    btnCompanyDelete.setEnabled(false);
                    btnCompanyUpdate.setEnabled(false);
                    Validator.setBlankFields(tcp);
                }
            }
        });
        jxcompany.getColumn(0).setMaxWidth(100);
        // center the table header
        TableColumnModel columnModel = jxcompany.getColumnModel();
        jxcompany.setTableHeader(new JXTableHeader(columnModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxcompany.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }

    // Customize disk table
    private void customizediskTable() {
        jxdisk.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxdisk.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        jxdisk.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                JTextComponent[] tcp = {txtDid, txtDiskPerM, txtMoneyUnit, txtTotal};
                if (jxdisk.getSelectedRow() != -1) {
                    btnDiskUpdate.setEnabled(true);
                    btnDiskDelete.setEnabled(true);
                    int realRow = jxdisk.convertRowIndexToModel(jxdisk.getSelectedRow());
                    String id = (String) jxdisk.getModel().getValueAt(realRow, 0);
                    Disk disk = DiskController.getDisk(id);
                    LinkedList mu = databaseManager.getMoneyUnit();
                    txtDid.setText(disk.getFilmId());
            		for (int i = 0; i < mu.size(); i++) {
            			MoneyUnit mu2 = (MoneyUnit) mu.get(i);
            			txtMoneyUnit.setText(String.valueOf(mu2.getUsd()));
            		}
                    //txtMoneyUnit.setText(String.valueOf(mu));
                    txtDiskPerM.setText(disk.getDiskPerMovie());
                    txtTotal.setText(disk.getTotalAmount());
                } else {
                    btnDiskUpdate.setEnabled(false);
                    btnDiskDelete.setEnabled(false);
                    Validator.setBlankFields(tcp);
                }
            }
        });
        jxdisk.getColumn(0).setMaxWidth(100);
        jxdisk.getColumn(2).setMaxWidth(100);
        jxdisk.getColumn(2).setCellRenderer(new MyTableRenderer.CurrencyRenderer());
        // center the table header
        TableColumnModel columnModel = jxdisk.getColumnModel();
        jxdisk.setTableHeader(new JXTableHeader(columnModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxdisk.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="reload table">
    private void reloadcompanyTable() {
        jxcompany.setModel(new MyCompanyTableModel());
        jxcompany.getColumn(0).setMaxWidth(100);
        jxcompany.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }

    private void reloaddiskTable() {
        jxdisk.setModel(new MyDiskTableModel());
        jxdisk.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    //</editor-fold>

    private JTextField getTxtSearchC() {
		if (txtSearchC == null) {
			txtSearchC = new JTextField();
			txtSearchC.getDocument().addDocumentListener(
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
							String text = txtSearchC.getText();
							companyModel.data = CompanyController.searchCompany(text);
							jxcompany.setModel(companyModel);
							//jxTable.setRowSorter(sorter);
							//initLinkBox(10,1);     
							jxcompany.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
						}
					});

		}
		return txtSearchC;
	}
    private JTextField getTxtSearchD() {
		if (txtSearchD == null) {
			txtSearchD = new JTextField();
			txtSearchD.getDocument().addDocumentListener(
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
							String text = txtSearchD.getText();
							diskModel.data = DiskController.searchDisk(text);
							jxdisk.setModel(diskModel);
							//jxTable.setRowSorter(sorter);
							//initLinkBox(10,1);     
							jxdisk.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
						}
					});

		}
		return txtSearchD;
	}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        
        jxcompany = new org.jdesktop.swingx.JXTable();
        jxdisk = new org.jdesktop.swingx.JXTable();
        
        jLabelCName = new javax.swing.JLabel();
        jLabelDName = new javax.swing.JLabel();
        jLabelMovie = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jLabelDId = new javax.swing.JLabel();
        jLabelMoneyUnit = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelDiskPM = new javax.swing.JLabel();
        jLabelSearchC = new javax.swing.JLabel();
        jLabelSearchD = new javax.swing.JLabel();
        
        txtCompanyName = new javax.swing.JTextField();
        txtMoneyUnit = new javax.swing.JTextField();
        txtDiskPerM = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtDMovie = new javax.swing.JTextField();
        txtDid = new javax.swing.JTextField();
        txtCid = new javax.swing.JTextField();
        
        txtCMovie = new javax.swing.JTextArea();
        //txtTotal = new javax.swing.JTextArea();
        
        btnCompanyAdd = new javax.swing.JButton();
        btnCompanyUpdate = new javax.swing.JButton();
        btnCompanyDelete = new javax.swing.JButton();
        btnDiskUpdate = new javax.swing.JButton();
        btnDiskDelete = new javax.swing.JButton();                       

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Companies");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Companys", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(102, 0, 0))); // NOI18N

        jxcompany.setModel(companyModel);
        jxcompany.setHighlighters(simpleStripHL);
        jxcompany.setShowGrid(false);
        jScrollPane1.setViewportView(jxcompany);

        jLabelCName.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCName.setForeground(new java.awt.Color(51, 51, 51));
        jLabelCName.setText("Name");
        
        jLabelSearchC.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelSearchC.setForeground(new java.awt.Color(51, 51, 51));
        jLabelSearchC.setText("SearchC");
        
        jLabelSearchD.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelSearchD.setForeground(new java.awt.Color(51, 51, 51));
        jLabelSearchD.setText("SearchD");
        
        jLabelDName.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelDName.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDName.setText("Name");
        
        jLabelId.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelId.setForeground(new java.awt.Color(51, 51, 51));
        jLabelId.setText("Id");
        
        jLabelDId.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelDId.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDId.setText("Id");

        jLabelMovie.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelMovie.setForeground(new java.awt.Color(51, 51, 51));
        jLabelMovie.setText("Movies");

        txtCompanyName.setFont(new java.awt.Font("Tahoma", 0, 12));

        txtCMovie.setColumns(20);
        txtCMovie.setFont(new java.awt.Font("Verdana", 0, 12));
        txtCMovie.setLineWrap(true);
        txtCMovie.setRows(5);
        txtCMovie.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtCMovie);

        btnCompanyAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnCompanyAdd.setText("Insert");
        btnCompanyAdd.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnCompanyAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompanyAddActionPerformed(evt);
            }
        });

        btnCompanyUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnCompanyUpdate.setText("Update");
        btnCompanyUpdate.setEnabled(false);
        btnCompanyUpdate.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnCompanyUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompanyUpdateActionPerformed(evt);
            }
        });

        btnCompanyDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnCompanyDelete.setText("Delete");
        btnCompanyDelete.setEnabled(false);
        btnCompanyDelete.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnCompanyDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompanyDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSearchC)
                        	.addComponent(jLabelId)
                        	.addComponent(jLabelCName)
                            .addComponent(jLabelMovie))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(getTxtSearchC(), javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addComponent(txtCid, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addComponent(txtCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCompanyAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnCompanyUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnCompanyDelete)
                        .addGap(58, 58, 58))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSearchC)
                    .addComponent(getTxtSearchC(), javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(txtCid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCName)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMovie)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCompanyAdd)
                    .addComponent(btnCompanyUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompanyDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Salary Grade", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(102, 0, 0))); // NOI18N

        jxdisk.setModel(diskModel);
        jxdisk.setHighlighters(simpleStripHL);
        jxdisk.setShowGrid(false);
        jScrollPane3.setViewportView(jxdisk);

        jLabelDiskPM.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelDiskPM.setForeground(new java.awt.Color(51, 51, 51));
        jLabelDiskPM.setText("Disk Per Movie");

        jLabelMoneyUnit.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelMoneyUnit.setForeground(new java.awt.Color(51, 51, 51));
        jLabelMoneyUnit.setText("Money Unit");

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelTotal.setForeground(new java.awt.Color(51, 51, 51));
        jLabelTotal.setText("Total");

        txtMoneyUnit.setFont(new java.awt.Font("Tahoma", 0, 12));

        txtDiskPerM.setFont(new java.awt.Font("Tahoma", 0, 12));

//        txtTotal.setColumns(20);
//        txtTotal.setFont(new java.awt.Font("Verdana", 0, 12));
//        txtTotal.setLineWrap(true);
//        txtTotal.setRows(3);
//        txtTotal.setWrapStyleWord(true);
//        jScrollPane4.setViewportView(txtTotal);

        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("$");

        btnDiskUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnDiskUpdate.setText("Update");
        btnDiskUpdate.setEnabled(false);
        btnDiskUpdate.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnDiskUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiskUpdateActionPerformed(evt);
            }
        });

        btnDiskDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDiskDelete.setText("Delete");
        btnDiskDelete.setEnabled(false);
        btnDiskDelete.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnDiskDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiskDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSearchD)
                        	.addComponent(jLabelDId)
                        	.addComponent(jLabelMoneyUnit)
                            .addComponent(jLabelDiskPM)
                            .addComponent(jLabelTotal)
                            .addComponent(jLabelDName))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addComponent(getTxtSearchD(), javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        	.addComponent(txtDid, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiskPerM, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtMoneyUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(186, 186, 186)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDiskUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDiskDelete)
                        .addGap(57, 57, 57))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSearchD)
                    .addComponent(getTxtSearchD(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDId)
                    .addComponent(txtDid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDiskPM)
                    .addComponent(txtDiskPerM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMoneyUnit)
                    .addComponent(txtMoneyUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDName)
                    .addComponent(txtDMovie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)                
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDiskUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDiskDelete))
                .addContainerGap())
        );

        jLabel8.setText("Disks");
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel8.setForeground(new java.awt.Color(0, 102, 153));
        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building.png"))); // NOI18N
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                	.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="events on company table buttons">           
    private void btnCompanyAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompanyAddActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtCid, txtCompanyName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        if (!componentListenerIsOn) {
            jxcompany.addComponentListener(ts);
            componentListenerIsOn = true;
        }
        String id = txtCid.getText();
        String name = txtCompanyName.getText();
        if (CompanyController.insert(name, id)) {
            new CustomMessageDialog(null, true, "Done!", "Added a new company sucessfully!", CustomMessageDialog.DONE);
            reloadcompanyTable();
            // select the inserted row
            int count = jxcompany.getRowCount();
            jxcompany.changeSelection(count - 1, count - 1, false, false);
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                for (int i = 4; i < ManageAttendance.jxTable.getColumnCount(true); i += 2) {
//                    ManageAttendance.jxTable.getColumn(i).setCellEditor(new DefaultCellEditor(AttendanceDAO.readcompanyToCBB()));
//                }
//            }
        }
    }//GEN-LAST:event_btnCompanyAddActionPerformed

    private void btnCompanyUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompanyUpdateActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtCompanyName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        int realRow = jxcompany.convertRowIndexToModel(jxcompany.getSelectedRow());
        String id = (String) jxcompany.getModel().getValueAt(realRow, 0);
        String name = txtCompanyName.getText();
        if (CompanyController.update(id, name)) {
            new CustomMessageDialog(null, true, "Done!", "Updated sucessfully!", CustomMessageDialog.DONE);
            reloadcompanyTable();
            // select the row just updated
            jxcompany.changeSelection(realRow, realRow, false, false);
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                ManageAttendance.reloadTable();
//            }
        }
    }//GEN-LAST:event_btnCompanyUpdateActionPerformed

    private void btnCompanyDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompanyDeleteActionPerformed
        // TODO add your handling code here:
        if (componentListenerIsOn) {
            jxcompany.removeComponentListener(ts);
            componentListenerIsOn = false;
        }
        int realRow = jxcompany.convertRowIndexToModel(jxcompany.getSelectedRow());
        String id = (String) jxcompany.getModel().getValueAt(realRow, 0);
        if (CompanyController.delete(id)) {
            new CustomMessageDialog(null, true, "Done!", "Deleted sucessfully!", CustomMessageDialog.DONE);
            reloadcompanyTable();
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                ManageAttendance.reloadTable();
//            }
        }
    }//GEN-LAST:event_btnCompanyDeleteActionPerformed
    //</editor-fold>

    private void btnDiskUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeUpdateActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtMoneyUnit, txtDiskPerM, txtTotal};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        if (!Validator.validateIntField(txtDiskPerM)) {
            return;
        }
        if (!Validator.validateIntField(txtTotal)) {
            return;
        }
        int realRow = jxdisk.convertRowIndexToModel(jxdisk.getSelectedRow());
        int id = ((Integer) jxdisk.getModel().getValueAt(realRow, 0)).intValue();
        String dpm = txtDiskPerM.getText();
        String tt = txtTotal.getText();
        if (DiskController.update(id, dpm, tt)) {
            new CustomMessageDialog(null, true, "Done!", "Updated sucessfully!", CustomMessageDialog.DONE);
            reloaddiskTable();
            // select the row just updated
            jxdisk.changeSelection(realRow, realRow, false, false);
        }
    }//GEN-LAST:event_btnGradeUpdateActionPerformed

    private void btnDiskDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeDelActionPerformed
        // TODO add your handling code here:
        int realRow = jxdisk.convertRowIndexToModel(jxdisk.getSelectedRow());
        String id = (String) jxdisk.getModel().getValueAt(realRow, 0);
        if (DiskController.delete(id)) {
            new CustomMessageDialog(null, true, "Done!", "Deleted sucessfully!", CustomMessageDialog.DONE);
            reloaddiskTable();
        }
    }//GEN-LAST:event_btnGradeDelActionPerformed
    //</editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompanyAdd;
    private javax.swing.JButton btnCompanyDelete;
    private javax.swing.JButton btnCompanyUpdate;
    private javax.swing.JButton btnDiskDelete;
    private javax.swing.JButton btnDiskUpdate;

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelDId;
    private javax.swing.JLabel jLabelCName;
    private javax.swing.JLabel jLabelDName;
    private javax.swing.JLabel jLabelMovie;
    private javax.swing.JLabel jLabelMoneyUnit;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelDiskPM;
    private javax.swing.JLabel jLabelSearchC;
    private javax.swing.JLabel jLabelSearchD;
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    
    private org.jdesktop.swingx.JXTable jxcompany;
    private org.jdesktop.swingx.JXTable jxdisk;
    
    private javax.swing.JTextArea txtCMovie;
    //private javax.swing.JTextArea txtTotal;
    
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtDiskPerM;
    private javax.swing.JTextField txtMoneyUnit;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtDMovie;
    private javax.swing.JTextField txtDid;
    private javax.swing.JTextField txtCid;
    private javax.swing.JTextField txtSearchC;
    private javax.swing.JTextField txtSearchD;
    // End of variables declaration//GEN-END:variables
}
