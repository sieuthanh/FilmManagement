
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
public class WindowBackgroundPanel extends JPanel {
    private Image img;
    private GetTheme getTheme = new GetTheme();
    private String name = getTheme.Theme();
    
    public WindowBackgroundPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(800, 600));
        if(name == null|| name == ""){
        	name = "default";
        }
        URL url = WindowBackgroundPanel.class.getResource("/resources/theme/" + name + "/WoM.png");
        if (url == null) System.out.println("Image not found");
        img = new ImageIcon(url).getImage();
    }

    public void changeTheme() {
        String newtheme = getTheme.Theme();
        URL url = WindowBackgroundPanel.class.getResource("/resources/theme/" + newtheme + "/WoM.png");
        if (url == null) System.out.println("Image not found");
        img = new ImageIcon(url).getImage();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
