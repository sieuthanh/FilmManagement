package moviedisplaypanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class DownloadPanel extends JPanel implements ActionListener {
   private JButton checklink = new JButton("Check Link");
   private CheckLink cl;
   private JButton editlink = new JButton("Edit Link");
   private Border border = BorderFactory.createLineBorder(Color.lightGray);
   private Border downloadborder = BorderFactory.createTitledBorder(border, "Download Link",
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
            Font.BOLD,14), Color.black);
   private JTextPane linkpane = new JTextPane();
   private String path;
   private int state = 1;
   private ReadFile rf;
 
   public DownloadPanel(String path)  {
        this.path = path;
        rf = new ReadFile(path+"Link.txt");
        setLayout(null);
        setBackground(Color.white);
        setPreferredSize(new Dimension(587,300));
        setBorder(downloadborder);
        Insets insets = getInsets();
        checklink.setPreferredSize(new Dimension(10,25));
        checklink.setBounds(insets.left + 200, insets.top, 100, 25);
        add(checklink);
        if (rf.CheckFile()) checklink.addActionListener(this);
        

        editlink.setPreferredSize(new Dimension(100,25));
        editlink.setBounds(insets.left + 320, insets.top, 100, 25);
        add(editlink);
        if (rf.CheckFile()) editlink.addActionListener(this);
        
        JScrollPane jsp = CreateScrollPane();
        Dimension d = jsp.getPreferredSize();
        jsp.setBounds(insets.left + 15, insets.top + 35, 545, 230);
        add(jsp);

   }

   private JScrollPane CreateScrollPane(){
        
        linkpane.setEditable(false);
        linkpane.setPreferredSize(new Dimension(570,300));
        linkpane.setText(rf.ReadAllFile());
        JScrollPane sp = new JScrollPane(linkpane);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return sp;
   }

    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (e.getSource() == checklink) {
            cl = new CheckLink(path,checklink);
            checklink.setEnabled(false);
        }
        if (e.getSource() == editlink) {
            switch (state) {
                case 1:
                    state = 2;
                    checklink.setEnabled(false);
                    linkpane.setEditable(true);
                    editlink.setText("Save Link");
                    break ;
                case 2:
                    state = 1;
                    checklink.setEnabled(true);
                    linkpane.setEditable(false);
                    editlink.setText("Edit Link");
                    WriteFile wf = new WriteFile(path+"Link.txt",linkpane.getText());
                    break;
            }
        }
    }
}
