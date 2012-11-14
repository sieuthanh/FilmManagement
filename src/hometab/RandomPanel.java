package hometab;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import databasemanager.*;
import java.util.LinkedList;
import java.util.List;

public class RandomPanel extends JPanel{

   private Movierandomdisplay[] m = new Movierandomdisplay[10];
   private int k;
   private Dimension d;
   private Insets i;
   private List randomlist;
   private DatabaseManager database = new DatabaseManager();
   private List movielist = database.getMovieList();
   private boolean[] checked = new boolean[10000];
   
   public RandomPanel(){
        super();
        i=getInsets();
        setLayout(null);
        this.setPreferredSize(new Dimension(560, 370));
        Border border = BorderFactory.createTitledBorder("Random Movies");
        setBorder(border);
        setBackground(Color.white);        
        //add random results
        for (int j = 0; j < movielist.size(); j ++) checked[j] = true;
        randomlist = new LinkedList();
        for (int j = 0; j < 10; j ++) {
            long x = Math.round(Math.random() * 10000);
            if (!checked[(int)x % movielist.size()])
                while (!checked[(int)x % movielist.size()]) {
                    x = Math.round(Math.random() * 10000);
                    break;
                }
            checked[(int)x % movielist.size()] = false;
            randomlist.add((Movie)movielist.get((int)(x % movielist.size())));
        }
        for(k=0;k<=4;k++){
            m[k]=new Movierandomdisplay((Movie)randomlist.get(k));
            d = m[k].getPreferredSize();
            add(m[k]);
            m[k].setBounds(i.left+15+(d.width+7)*k, i.top+25, d.width, d.height);
        }
        for(k=5;k<=9;k++){
            m[k]=new Movierandomdisplay((Movie)randomlist.get(k));
            d = m[k].getPreferredSize();
            add(m[k]);
            m[k].setBounds(i.left+15+(d.width+7)*(k-5), i.top+190, d.width, d.height);
        }
    }

    public void tabChanged() {
        for (int j = 0; j < 10; j ++) remove(m[j]);        
        for (int j = 0; j < movielist.size(); j ++) checked[j] = true;
        randomlist = new LinkedList();
        for (int j = 0; j < 10; j ++) {
            long x = Math.round(Math.random() * 10000);
            if (!checked[(int)x % movielist.size()])
                while (!checked[(int)x % movielist.size()]) {
                    x = Math.round(Math.random() * 10000);
                    break;
                }
            checked[(int)x % movielist.size()] = false;
            randomlist.add((Movie)movielist.get((int)(x % movielist.size())));
        }
        for (int j = 0; j < 10; j ++) {
            long x = Math.round(Math.random() * 10000);
            randomlist.add((Movie)movielist.get((int)(x % movielist.size())));
        }
        for(k=0;k<=4;k++){
            m[k]=new Movierandomdisplay((Movie)randomlist.get(k));
            d = m[k].getPreferredSize();
            add(m[k]);
            m[k].setBounds(i.left+15+(d.width+7)*k, i.top+25, d.width, d.height);
        }
        for(k=5;k<=9;k++){
            m[k]=new Movierandomdisplay((Movie)randomlist.get(k));
            d = m[k].getPreferredSize();
            add(m[k]);
            m[k].setBounds(i.left+15+(d.width+7)*(k-5), i.top+190, d.width, d.height);
        }
        repaint();
        revalidate();
    }

   public Movierandomdisplay[] getMovierandomdisplay() {
       return m;
   }
}
