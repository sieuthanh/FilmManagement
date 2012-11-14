/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShiftsAndBuildings.java
 *
 * Created on Nov 5, 2011, 6:19:26 PM
 */
package admin.view;


import java.awt.Color;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
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
import databasemanager.Disk;

import admin.controller.CompanyController;
import admin.controller.DiskController;

/**
 *
 * @author CodeBlue
 */
public class CompanyAndDisk extends javax.swing.JPanel {

    MyCompanyTableModel buildingModel = new MyCompanyTableModel();
    MyDiskTableModel shiftModel = new MyDiskTableModel();
    Highlighter simpleStripHL = HighlighterFactory.createSimpleStriping();
    MyTableScroller ts = null;
    // ensure only 1 component listener is added to jtable
    // so that when delete an item, table will not go to bottom
    private static boolean componentListenerIsOn = false;

    /** Creates new form ShiftsAndBuildings */
    public CompanyAndDisk() {
        initComponents();
        customizeBuildingTable();
        customizeShiftTable();
    }

    //<editor-fold defaultstate="collapsed" desc="customize table">
    // Customize building table
    private void customizeBuildingTable() {
        jxBuilding.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxBuilding.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxBuilding);
        jxBuilding.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                JTextComponent[] tcp = {txtBuildingName, taDesc};
                if (jxBuilding.getSelectedRow() != -1) {
                    btnBldDelete.setEnabled(true);
                    btnBldUpdate.setEnabled(true);
                    int realRow = jxBuilding.convertRowIndexToModel(jxBuilding.getSelectedRow());
                    String id = (String) (jxBuilding.getModel().getValueAt(realRow, 0));
                    Company building = CompanyController.getCompany(id);
                    txtBuildingName.setText(building.getName());
                    taDesc.setText(building.getId());
                } else {
                    btnBldDelete.setEnabled(false);
                    btnBldUpdate.setEnabled(false);
                    Validator.setBlankFields(tcp);
                }
            }
        });
        jxBuilding.getColumn(0).setMaxWidth(100);
        // center the table header
        TableColumnModel columnModel = jxBuilding.getColumnModel();
        jxBuilding.setTableHeader(new JXTableHeader(columnModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxBuilding.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }

    // Customize shift table
    private void customizeShiftTable() {
        jxShift.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxShift.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        jxShift.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                JTextComponent[] tcp = {txtPerHour, txtGradeName, taGradeDesc};
                if (jxShift.getSelectedRow() != -1) {
                    btnShfUpdate.setEnabled(true);
                    btnShfDelete.setEnabled(true);
                    int realRow = jxShift.convertRowIndexToModel(jxShift.getSelectedRow());
                    String id = (String) jxShift.getModel().getValueAt(realRow, 0);
                    Disk grade = DiskController.getDisk(id);
                    txtPerHour.setText("" + grade.getDiskPerMovie());
                    txtGradeName.setText(grade.getTotalAmount());
                    taGradeDesc.setText(grade.getFilmId());
                } else {
                    btnShfUpdate.setEnabled(false);
                    btnShfDelete.setEnabled(false);
                    Validator.setBlankFields(tcp);
                }
            }
        });
        jxShift.getColumn(0).setMaxWidth(100);
        jxShift.getColumn(2).setMaxWidth(100);
        jxShift.getColumn(2).setCellRenderer(new MyTableRenderer.CurrencyRenderer());
        // center the table header
        TableColumnModel columnModel = jxShift.getColumnModel();
        jxShift.setTableHeader(new JXTableHeader(columnModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxShift.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="reload table">
    private void reloadBuildingTable() {
        jxBuilding.setModel(new MyCompanyTableModel());
        jxBuilding.getColumn(0).setMaxWidth(100);
        jxBuilding.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }

    private void reloadShiftTable() {
        jxShift.setModel(new MyDiskTableModel());
        jxShift.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
    }
    //</editor-fold>

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jxBuilding = new org.jdesktop.swingx.JXTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtBuildingName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDesc = new javax.swing.JTextArea();
        btnBldAdd = new javax.swing.JButton();
        btnBldUpdate = new javax.swing.JButton();
        btnBldDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jxShift = new org.jdesktop.swingx.JXTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtShiftName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPerHour = new javax.swing.JTextField();
        txtGradeName = new javax.swing.JTextField();
        taGradeDesc = new javax.swing.JTextArea();
        cbStart = new javax.swing.JComboBox();
        cbEnd = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtDuration = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnShfAdd = new javax.swing.JButton();
        btnShfUpdate = new javax.swing.JButton();
        btnShfDelete = new javax.swing.JButton();
        sfStartHour = new com.toedter.components.JSpinField();
        sfStartMinute = new com.toedter.components.JSpinField();
        sfEndHour = new com.toedter.components.JSpinField();
        sfEndMinute = new com.toedter.components.JSpinField();
        jLabel11 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Buildings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(102, 0, 0))); // NOI18N

        jxBuilding.setModel(buildingModel);
        jxBuilding.setHighlighters(simpleStripHL);
        jxBuilding.setShowGrid(false);
        jScrollPane1.setViewportView(jxBuilding);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Building name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Description");

        txtBuildingName.setFont(new java.awt.Font("Tahoma", 0, 12));

        taDesc.setColumns(20);
        taDesc.setFont(new java.awt.Font("Verdana", 0, 12));
        taDesc.setLineWrap(true);
        taDesc.setRows(5);
        taDesc.setWrapStyleWord(true);
        jScrollPane2.setViewportView(taDesc);

        btnBldAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnBldAdd.setText("Insert");
        btnBldAdd.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnBldAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBldAddActionPerformed(evt);
            }
        });

        btnBldUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnBldUpdate.setText("Update");
        btnBldUpdate.setEnabled(false);
        btnBldUpdate.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnBldUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBldUpdateActionPerformed(evt);
            }
        });

        btnBldDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnBldDelete.setText("Delete");
        btnBldDelete.setEnabled(false);
        btnBldDelete.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnBldDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBldDeleteActionPerformed(evt);
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
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuildingName, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBldAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnBldUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnBldDelete)
                        .addGap(58, 58, 58))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuildingName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBldAdd)
                    .addComponent(btnBldUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBldDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage Salary Grade", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(102, 0, 0))); // NOI18N

        jxShift.setModel(shiftModel);
        jxShift.setHighlighters(simpleStripHL);
        jxShift.setShowGrid(false);
        jScrollPane3.setViewportView(jxShift);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Grade name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Salary per hour");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Description");

        txtPerHour.setFont(new java.awt.Font("Tahoma", 0, 12));

        txtGradeName.setFont(new java.awt.Font("Tahoma", 0, 12));

        taGradeDesc.setColumns(20);
        taGradeDesc.setFont(new java.awt.Font("Verdana", 0, 12));
        taGradeDesc.setLineWrap(true);
        taGradeDesc.setRows(3);
        taGradeDesc.setWrapStyleWord(true);
        jScrollPane4.setViewportView(taGradeDesc);

        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("$");

        btnShfAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnShfAdd.setText("Insert");
        btnShfAdd.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnShfAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShfAddActionPerformed(evt);
            }
        });

        btnShfUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        btnShfUpdate.setText("Update");
        btnShfUpdate.setEnabled(false);
        btnShfUpdate.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnShfUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShfUpdateActionPerformed(evt);
            }
        });

        btnShfDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnShfDelete.setText("Delete");
        btnShfDelete.setEnabled(false);
        btnShfDelete.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnShfDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShfDeleteActionPerformed(evt);
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
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addComponent(txtGradeName, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(186, 186, 186)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnShfAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnShfUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnShfDelete)
                        .addGap(57, 57, 57))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtGradeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPerHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShfAdd)
                    .addComponent(btnShfUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShfDelete))
                .addContainerGap())
        );

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/status.png"))); // NOI18N
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salarygrade.jpg"))); // NOI18N
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc="events on building table buttons">           
    private void btnBldAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBldAddActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtBuildingName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        if (!componentListenerIsOn) {
            jxBuilding.addComponentListener(ts);
            componentListenerIsOn = true;
        }
        String name = txtBuildingName.getText();
        String desc = taDesc.getText();
        if (CompanyController.insert(name, desc)) {
            new CustomMessageDialog(null, true, "Done!", "Added a new building sucessfully!", CustomMessageDialog.DONE);
            reloadBuildingTable();
            // select the inserted row
            int count = jxBuilding.getRowCount();
            jxBuilding.changeSelection(count - 1, count - 1, false, false);
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                for (int i = 4; i < ManageAttendance.jxTable.getColumnCount(true); i += 2) {
//                    ManageAttendance.jxTable.getColumn(i).setCellEditor(new DefaultCellEditor(AttendanceDAO.readBuildingToCBB()));
//                }
//            }
        }
    }//GEN-LAST:event_btnBldAddActionPerformed

    private void btnBldUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBldUpdateActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtBuildingName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        int realRow = jxBuilding.convertRowIndexToModel(jxBuilding.getSelectedRow());
        String id = (String) jxBuilding.getModel().getValueAt(realRow, 0);
        String name = txtBuildingName.getText();
        String desc = taDesc.getText();
        if (CompanyController.update(id, name)) {
            new CustomMessageDialog(null, true, "Done!", "Updated sucessfully!", CustomMessageDialog.DONE);
            reloadBuildingTable();
            // select the row just updated
            jxBuilding.changeSelection(realRow, realRow, false, false);
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                ManageAttendance.reloadTable();
//            }
        }
    }//GEN-LAST:event_btnBldUpdateActionPerformed

    private void btnBldDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBldDeleteActionPerformed
        // TODO add your handling code here:
        if (componentListenerIsOn) {
            jxBuilding.removeComponentListener(ts);
            componentListenerIsOn = false;
        }
        int realRow = jxBuilding.convertRowIndexToModel(jxBuilding.getSelectedRow());
        String id = (String) jxBuilding.getModel().getValueAt(realRow, 0);
        if (CompanyController.delete(id)) {
            new CustomMessageDialog(null, true, "Done!", "Deleted sucessfully!", CustomMessageDialog.DONE);
            reloadBuildingTable();
            // reset cell editor for attendance table
//            if (ManageAttendance.jxDate.getDate() != null) {
//                ManageAttendance.reloadTable();
//            }
        }
    }//GEN-LAST:event_btnBldDeleteActionPerformed
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="events on shift table buttons">
    private void btnShfAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeAddActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtPerHour, txtGradeName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        if (!Validator.validateFloatField(txtPerHour)) {
            return;
        }
        String salaryPerHour = txtPerHour.getText();
        String name = txtGradeName.getText();
        String desc = taGradeDesc.getText();
        if (DiskController.insert(desc, salaryPerHour, name)) {
            new CustomMessageDialog(null, true, "Done!", "Added a new grade sucessfully!", CustomMessageDialog.DONE);
            reloadShiftTable();
            // select the inserted row
            int count = jxShift.getRowCount();
            jxShift.changeSelection(count - 1, count - 1, false, false);
        }
    }//GEN-LAST:event_btnGradeAddActionPerformed

    private void btnShfUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeUpdateActionPerformed
        // TODO add your handling code here:
        JTextComponent[] tcp = {txtPerHour, txtGradeName};
        if (!Validator.validateComponents(tcp)) {
            return;
        }
        if (!Validator.validateFloatField(txtPerHour)) {
            return;
        }
        int realRow = jxShift.convertRowIndexToModel(jxShift.getSelectedRow());
        int id = ((Integer) jxShift.getModel().getValueAt(realRow, 0)).intValue();
        String name = txtGradeName.getText();
        String desc = taGradeDesc.getText();
        if (DiskController.update(id, name, desc)) {
            new CustomMessageDialog(null, true, "Done!", "Updated sucessfully!", CustomMessageDialog.DONE);
            reloadShiftTable();
            // select the row just updated
            jxShift.changeSelection(realRow, realRow, false, false);
        }
    }//GEN-LAST:event_btnGradeUpdateActionPerformed

    private void btnShfDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeDelActionPerformed
        // TODO add your handling code here:
        int realRow = jxShift.convertRowIndexToModel(jxShift.getSelectedRow());
        String id = (String) jxShift.getModel().getValueAt(realRow, 0);
        if (DiskController.delete(id)) {
            new CustomMessageDialog(null, true, "Done!", "Deleted sucessfully!", CustomMessageDialog.DONE);
            reloadShiftTable();
        }
    }//GEN-LAST:event_btnGradeDelActionPerformed
    //</editor-fold>
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBldAdd;
    private javax.swing.JButton btnBldDelete;
    private javax.swing.JButton btnBldUpdate;
    private javax.swing.JButton btnShfAdd;
    private javax.swing.JButton btnShfDelete;
    private javax.swing.JButton btnShfUpdate;
    private javax.swing.JComboBox cbEnd;
    private javax.swing.JComboBox cbStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private org.jdesktop.swingx.JXTable jxBuilding;
    private org.jdesktop.swingx.JXTable jxShift;
    private com.toedter.components.JSpinField sfEndHour;
    private com.toedter.components.JSpinField sfEndMinute;
    private com.toedter.components.JSpinField sfStartHour;
    private com.toedter.components.JSpinField sfStartMinute;
    private javax.swing.JTextArea taDesc;
    private javax.swing.JTextField txtBuildingName;
    private javax.swing.JTextField txtDuration;
    private javax.swing.JTextField txtShiftName;
    private javax.swing.JTextArea taGradeDesc;
    private javax.swing.JTextField txtGradeName;
    private javax.swing.JTextField txtPerHour;
    // End of variables declaration//GEN-END:variables
}
