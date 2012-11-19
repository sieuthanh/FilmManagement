package admin.view;

import static org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import moviedisplaypanel.WriteFile;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import sun.java2d.Disposer;

import admin.controller.FilmController;
import admin.controller.PersonController;



public class AddDirector2 extends javax.swing.JFrame{

    private static MyPersonTableModel2 model = new MyPersonTableModel2();
    Highlighter simpleStripHL = HighlighterFactory.createSimpleStriping();
    MyTableFilter filterController = null;
    MyTableScroller ts = null;
    //private static String selectedfID;
    private static TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);;
    private static int LR_PAGE_SIZE = 5;
    private static final LinkViewRadioButtonUI ui = new LinkViewRadioButtonUI();
    // ensure only 1 component listener is added to jtable
    // so that when delete an item, table will not go to bottom
    private static boolean componentListenerIsOn = false;

    /** Creates new form ManageManager */
    public AddDirector2() {
    	//this.selectedfID = selectedID;
        initComponents();
        customizeTable();
        setCountLabel();
        //updatePaging();
    }

    // Customize table
    private void customizeTable() {
    	
        jxTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // set table rollover color
        jxTable.addHighlighter(new ColorHighlighter(HighlightPredicate.ROLLOVER_ROW,
                null, new Color(255, 102, 0)));
        ts = new MyTableScroller(jxTable);
        jxTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jxTable.getSelectedRow() != -1) {
                    btnAdd.setEnabled(true);
                } else {
                    btnAdd.setEnabled(false);
                }
            }
        });
        TableColumnModel columnModel = jxTable.getColumnModel();
        jxTable.setTableHeader(new JXTableHeader(columnModel) {

            @Override
            public void updateUI() {
                super.updateUI();
                // need to do in updateUI to survive toggling of LAF 
                if (getDefaultRenderer() instanceof JLabel) {
                    ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
                }
            }
        });
        jxTable.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
       // jxTable.setRowSorter(sorter);
		//initLinkBox(100,1);     
    }

    //count row
    private static void setCountLabel() {
        lbCountManager.setText("" + jxTable.getRowCount());
    }

    public static void reloadTable() {
        jxTable.setModel(new MyPersonTableModel2());
        jxTable.setRowSorter(sorter);
		initLinkBox(100,1);     
        jxTable.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
        setCountLabel();
    }

    private JTextField getTxtSearch2() {
		if (txtSearch2 == null) {
			txtSearch2 = new JTextField();
			txtSearch2.getDocument().addDocumentListener(
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
							String text = txtSearch2.getText();
							model.personList = PersonController.searchPerson2(text);
							//jxTable.setRowSorter(sorter);
							//initLinkBox(100,1);     
							jxTable.getColumn(0).setCellRenderer(new MyTableRenderer.IDRenderer());
						}
					});

		}
		return txtSearch2;
	}

    
    protected void updatePaging() {
        //if ((text == null)) {
        	sorter = new TableRowSorter<TableModel>(model);
        	jxTable.setRowSorter(sorter);
        //} else {
          //  sorter = null;
        //}
        initLinkBox(100,1);
        
    }
    
    protected static void initLinkBox(final int itemsPerPage, final int currentPageIndex) {
        //assert currentPageIndex>0;
        sorter.setRowFilter(makeRowFilter(itemsPerPage, currentPageIndex-1));
        ArrayList<JRadioButton> paginationButtons = new ArrayList<JRadioButton>();

        int startPageIndex = currentPageIndex-LR_PAGE_SIZE;
        if(startPageIndex<=0) startPageIndex = 1;

        int rowCount = model.getRowCount();
        int v = rowCount%itemsPerPage==0 ? 0 : 1;
        int maxPageIndex = rowCount/itemsPerPage + v;
        int endPageIndex = currentPageIndex+LR_PAGE_SIZE-1;
        if(endPageIndex>maxPageIndex) endPageIndex = maxPageIndex;

        if(currentPageIndex>1) {
          paginationButtons.add(makePNRadioButton(itemsPerPage, currentPageIndex-1, "Prev"));
        }
        
        if(startPageIndex<endPageIndex) {
            for(int i=startPageIndex;i<=endPageIndex;i++) {
                paginationButtons.add(makeRadioButton(itemsPerPage, currentPageIndex, i-1));
            }
        }
        if(currentPageIndex<maxPageIndex) {
            paginationButtons.add(makePNRadioButton(itemsPerPage, currentPageIndex+1, "Next"));
        }

        box.removeAll();
        ButtonGroup bg = new ButtonGroup();
        box.add(Box.createHorizontalGlue());
        for(JRadioButton r:paginationButtons) {
            box.add(r); bg.add(r);
        }
        box.add(Box.createHorizontalGlue());
        box.revalidate();
        box.repaint();
        paginationButtons.clear();
    }
    protected static JRadioButton makeRadioButton(final int itemsPerPage, final int current, final int target) {
        JRadioButton radio = new JRadioButton(String.valueOf(target+1)) {
            @Override protected void fireStateChanged() {
                ButtonModel model = getModel();
                if(!model.isEnabled()) {
                    setForeground(Color.GRAY);
                }else if(model.isPressed() && model.isArmed()) {
                    setForeground(Color.GREEN);
                }else if(model.isSelected()) {
                    setForeground(Color.RED);
                }
                super.fireStateChanged();
            }
        };
        radio.setForeground(Color.BLUE);
        radio.setUI(ui);
        if(target+1==current) {
            radio.setSelected(true);
        }
        radio.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                initLinkBox(itemsPerPage, target+1);
            }
        });
        return radio;
    }
    protected static JRadioButton makePNRadioButton(final int itemsPerPage, final int target, String title) {
        JRadioButton radio = new JRadioButton(title);
        radio.setForeground(Color.BLUE);
        radio.setUI(ui);
        radio.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                initLinkBox(itemsPerPage, target);
            }
        });
        return radio;
    }
    protected static RowFilter<TableModel,Integer> makeRowFilter(final int itemsPerPage, final int target) {
        return new RowFilter<TableModel,Integer>() {
            @Override public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                int ei = entry.getIdentifier();
                return target*itemsPerPage<=ei && ei<target*itemsPerPage+itemsPerPage;
            }
        };
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jxTable = new org.jdesktop.swingx.JXTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbCountManager = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        //txtSearch = new org.jdesktop.swingx.JXSearchField();

        setBackground(new java.awt.Color(255, 255, 255));
        //setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //jxTable.setRowSorter(sorter);
        jxTable.setModel(model);
        jxTable.setToolTipText("Double click to Add");
        jxTable.setColumnControlVisible(true);
        jxTable.setHighlighters(simpleStripHL);
        jxTable.setShowGrid(false);
        jxTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jxTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jxTable);

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Search Simple");

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Total :");

        lbCountManager.setForeground(new java.awt.Color(255, 0, 51));
        lbCountManager.setText("4");

        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });


        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        btnExit.setText("Exit");
        btnExit.setMargin(new java.awt.Insets(2, 8, 2, 8));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
                .addComponent(box)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnInsert)
                .addGap(66, 66, 66)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addGap(122, 122, 122))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(getTxtSearch2(), javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE)//524
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCountManager)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
        );//758
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbCountManager)
                        .addComponent(jLabel3))
                    .addComponent(getTxtSearch2(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(box)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert)
                    .addComponent(btnExit)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void jxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jxTableMouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() >= 2 && jxTable.getSelectedRow() != -1) {
            AddManager();
        }
    }//GEN-LAST:event_jxTableMouseClicked

    //<editor-fold defaultstate="collapsed" desc="Actions on button click">            
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        setVisible(true);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        AddManager();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        if (CustomMessageDialog.STATUS == CustomMessageDialog.CANCEL) {
        	insertManager();
        }
    }//GEN-LAST:event_btnInsertActionPerformed
    //</editor-fold>

    private void insertManager() {
        new AddNewPerson();
    }
    
    // this method handles button Add action and click event on table
    private void AddManager() {
        int selected = jxTable.convertRowIndexToModel(jxTable.getSelectedRow());
        String selectedID = (String) jxTable.getModel().getValueAt(selected, 0);
        AddNewMovie.reloadDirectorTable(selectedID);    
        new CustomMessageDialog(null, true, "Done!", "Add sucessfully!", CustomMessageDialog.DONE);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public static org.jdesktop.swingx.JXTable jxTable;
    public static javax.swing.JLabel lbCountManager;
    //private org.jdesktop.swingx.JXSearchField txtSearch;
    protected JTextField txtSearch2;
    protected static Box box = Box.createHorizontalBox();
    // End of variables declaration//GEN-END:variables
}
