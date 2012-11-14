package persondisplaypanel;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
        }
        JFrame frame = new JFrame("Person Display");
        frame.setSize(650,490);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PersonDisplay mp = new PersonDisplay("0811583");
        JScrollPane sp = new JScrollPane(mp);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        frame.add(sp);
        frame.setVisible(true);
    }
}
    
