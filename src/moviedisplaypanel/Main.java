package moviedisplaypanel;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
        }
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(630,490));
        MovieDisplay md = new MovieDisplay("0021749");
        Dimension d = md.getPreferredSize();
        Insets inset = jf.getInsets();
        md.setBounds(inset.left,inset.top,d.width,d.height);
        d=md.getPreferredSize();
        JScrollPane sp = new JScrollPane(md);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        jf.add(sp);
        jf.setVisible(true);
    }
}
    
