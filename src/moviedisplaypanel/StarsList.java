

package moviedisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class StarsList extends JPanel {
    ListPanel left,right;
    StarsIconPanel leftpanel,rightpanel;
    Border border = BorderFactory.createLineBorder(Color.lightGray);
    Border starborder = BorderFactory.createTitledBorder(border, "Casting",
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
            Font.BOLD,14), Color.black);
    LinkedList liststarslabel = new LinkedList();
    LinkedList listlabel = new LinkedList();


    public StarsList (String list[],String listid[], int size){
        String patch = "Data/People/";
        setLayout(null);
        setBackground(Color.white);
        setBorder(starborder);
        int x = list.length;
        int l,r;
        if (x%2 == 1) l=x/2+1; else l = x/2;
        r = x-l;
        String leftstring[] = new String[l];
        String leftid[] = new String[l];
        String rightstring[] = new String[r];
        String rightid[] = new String[r];

        Dimension d;
        Insets insets = getInsets();
        System.arraycopy(list, 0, leftstring, 0, l);
        System.arraycopy(listid, 0, leftid, 0, l);
        System.arraycopy(list, l, rightstring, 0, r);
        System.arraycopy(listid, l, rightid, 0, r);

        left = new ListPanel(leftstring,leftid,size);
        d = left.getPreferredSize();
        setPreferredSize(new Dimension(587,d.height+30));
        left.setBounds(insets.left + 50,insets.top + 5,d.width,d.height);
        add(left);
        leftpanel = new StarsIconPanel(leftid,50,patch);
        leftpanel.setBounds(insets.left + 10,insets.top + 5,d.width,d.height);
        add(leftpanel);
        listlabel = left.getListLabel();
        for (int i=0;i<listlabel.size();i++){
            liststarslabel.add(listlabel.get(i));
        }

        right = new ListPanel(rightstring,rightid,size);
        d = right.getPreferredSize();
        right.setBounds(insets.left + 330,insets.top + 5,d.width,d.height);
        add(right);
        rightpanel = new StarsIconPanel(rightid,50,patch);
        rightpanel.setBounds(insets.left + 290,insets.top + 5,d.width,d.height);
        add(rightpanel);
        listlabel = right.getListLabel();
        for (int i=0;i<listlabel.size();i++){
            liststarslabel.add(listlabel.get(i));
        }
    }

    public LinkedList getStarsLabel(){
        return liststarslabel;
    }
}
