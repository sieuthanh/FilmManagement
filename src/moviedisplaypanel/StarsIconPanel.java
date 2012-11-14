

package moviedisplaypanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


public class StarsIconPanel extends JPanel{
    final ImageLabel []img = new ImageLabel[20];
    public StarsIconPanel (String list[],int linesize,String patch){
        setLayout(null);
        setBackground(Color.white);
        int x = list.length;
        setPreferredSize(new Dimension(200,x*linesize + 5));
        Insets insets = getInsets();
        Dimension d;
        for (int i=0;i<x;i++){
            img[i] = new ImageLabel(patch+list[i]+"/1.jpg",30,40);
            d = img[i].getPreferredSize();
            img[i].setBounds(insets.left, insets.top + i*linesize, d.width, d.height);
            add(img[i]);
        }
    }
}

