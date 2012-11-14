
package movieadvancedsearchtab;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class Fromto extends JPanel {
    private JLabel From = new JLabel("From");
    private JLabel To = new JLabel("To");
    private JComboBox valuefrom = new JComboBox();
    private JComboBox valueto = new JComboBox();
    private int length;

    public Fromto(String lb, String[] list){
        setBackground(Color.white);
        length = list.length;
        JLabel label = new JLabel(lb);
        label.setFont(new Font("Tahoma",Font.BOLD,12));
        label.setPreferredSize(new Dimension(60,15));
        setSize(new Dimension(200,25));
        add(label);

        add(From);

        valuefrom = new JComboBox(list);
        valuefrom.setSelectedIndex(0);
        valuefrom.setPreferredSize(new Dimension(50,20));
        add(valuefrom);

        add(To);

        valueto = new JComboBox(list);
        valueto.setPreferredSize(new Dimension(50,20));
        valueto.setSelectedIndex(length-1);
        add(valueto);
    }

    public String valuefrom(){
        return (String)valuefrom.getSelectedItem();
    }

    public String valueto(){
        return (String)valueto.getSelectedItem();
    }

    public void clear(){
        valuefrom.setSelectedIndex(0);
        valueto.setSelectedIndex(length-1);
    }
}
