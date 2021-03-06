package persondisplaypanel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author uNstOppAbLe
 */
public class PictureLabel extends JLabel {
    private ImageIcon fullsize;
    private String imagepath, name;
    private Dimension d;
    
    public PictureLabel(String imagepath, String name)
    {
        super();
        File file = new File(imagepath);
        if (!file.exists()) imagepath = "resources/img/noimage.jpg";
        this.imagepath = imagepath;
        this.name = name;
        fullsize = new ImageIcon(imagepath);
        fullsize = new ImageIcon(resize(fullsize.getImage(), 214, 314));
        setIcon(fullsize);
        setToolTipText(name);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                ShowFullSizeImage();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public void ShowFullSizeImage()
    {
        final JFrame frame = new JFrame("Full Size Image");
        ImageIcon img = new ImageIcon(imagepath);
        JLabel thumbLabel = new JLabel(img);
        d = thumbLabel.getPreferredSize();
        thumbLabel.setBounds(0 , 0, d.width, d.height);
        frame.setIconImage(new ImageIcon("resources/img/logo.png").getImage());
        thumbLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                frame.dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        frame.add(thumbLabel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage resize (Image fullsize, int width, int height)
    {
        BufferedImage resized = new BufferedImage(width, height,
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(fullsize, 0, 0, width, height, null);
        g.dispose();
        return resized;
    }
}
