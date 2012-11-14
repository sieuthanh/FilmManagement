package hometab;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import databasemanager.*;

public class Movierandomdisplay extends JPanel {
    
    private Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
    private Insets i = getInsets();
    private Dimension d = new Dimension();
    private Cursor hand = new Cursor(Cursor.HAND_CURSOR);
    private JLabel namelabel = new JLabel();
    private Image pic;    
    //private DatabaseManager dtb = new DatabaseManager();
    private String link = "data/Films/", id, name;
    private int year;
    
    public Movierandomdisplay(Movie movie) {
        super();
        setCursor(hand);
        setLayout(null);
        setPreferredSize(new Dimension(100, 160));
        setBorder(border);
        setBackground(Color.white);
        //add poster
        id = movie.getId();
        pic = new ImageIcon(link + id + "/0.jpg").getImage();
        //add name
        name = movie.getName();
        year = movie.getYear();
        namelabel.setText(name);
        namelabel.setHorizontalAlignment(JLabel.CENTER);
        namelabel.setPreferredSize(new Dimension(90,25));
        d = namelabel.getPreferredSize();
        namelabel.setBounds(i.left+5, i.top+135, d.width, d.height);
        add(namelabel);
        setToolTipText(namelabel.getText()+" ("+year+")");
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.fillRect(3, 3, this.getWidth()-6, this.getHeight()-31);
        g2.drawImage(pic, 5, 5, this.getWidth()-10, this.getHeight()-35, null);
    }

    public String getID() {
        return id;
    }
        
}
