package moviedisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListPanel extends JPanel {
    int size;
    private LinkedList listlabel = new LinkedList();
    public ListPanel (String list[],String listid[],int linesize){
        //if type = 0 : listmovie, type = 1 liststar, type = 2 listcompany
        this.size = list.length;
        LinkedLabel [] linkedlabel = new LinkedLabel[size];
        setLayout(null);
        setBackground(Color.white); 
        setPreferredSize(new Dimension(200,size*linesize + 5));
        Insets insets = getInsets();
        Dimension d;
        for (int i=0;i<size;i++){
            linkedlabel[i] = new LinkedLabel(list[i],listid[i], 12);
            d = linkedlabel[i].getPreferredSize();
            linkedlabel[i].setBounds(insets.left, insets.top + i*linesize, d.width, d.height);
            add(linkedlabel[i]);
            listlabel.add(linkedlabel[i]);
        }
    }

    public LinkedList getListLabel(){
        return listlabel;
    }
}
