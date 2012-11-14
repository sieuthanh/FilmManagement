/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wom;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import moviedisplaypanel.WriteFile;

/**
 *
 * @author Admin
 */
public class ThemeLabel extends JLabel {
    private ImageIcon image;
    private String name;
    private String path = "resources/theme/";
    
    public ThemeLabel(String x) {
        name = x;
        image= new ImageIcon(path + name + "/WoM.png");
        image = new ImageIcon(resize(image.getImage(), 170, 120));
        setIcon(image);
        //
        setToolTipText(name);
        setPreferredSize(new Dimension(170,120));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public String getThemeName() {
        return name;
    }

    public final BufferedImage resize (Image fullsize, int width, int height)
    {
        BufferedImage resized = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(fullsize, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }
}
