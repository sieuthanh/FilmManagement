package moviedisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class FilmImagePanel extends JPanel {
    ImageLabel img1,img2,img3,img4;
    public FilmImagePanel(String patch){
        //setLayout(null);
        setPreferredSize(new Dimension(587,140));
        setBackground(Color.white);
        Dimension d;
        Insets insets = getInsets();
        img1 = new ImageLabel(patch+"1.jpg",135,100);
        add(img1);

        img2 = new ImageLabel(patch+"2.jpg",135,100);
        add(img2);

        img3 = new ImageLabel(patch+"3.jpg",135,100);
        add(img3);

        img4 = new ImageLabel(patch+"4.jpg",135,100);
        add(img4);
    }
}
