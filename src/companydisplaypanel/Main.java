package companydisplaypanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 *
 * @author uNstOppAbLe
 */
public class Main {
    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
        }
        JFrame frame = new JFrame("Company Display");
        frame.setSize(650,490);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CompanyDisplay mp = new CompanyDisplay("0026840");
        JScrollPane sp = new JScrollPane(mp);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        frame.add(sp);
        frame.setVisible(true);
    }

}
