/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wom;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class About extends JPanel {
    private Image img;
    
    public About() {
        super();
        setOpaque(false);
        setPreferredSize(new Dimension(400, 400));
        URL url = WindowBackgroundPanel.class.getResource("/aboutpanel.png");
        if (url == null) System.out.println("Image not found");
        img = new ImageIcon(url).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
