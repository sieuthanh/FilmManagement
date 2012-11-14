package moviedisplaypanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
public class ImageLabel extends JLabel {
    private String imagedir;
    private int w,h;
    Border border = BorderFactory.createLineBorder(Color.black);
    public ImageLabel (String path, int w, int h){
        this.w=w;
        this.h=h;
        File file = new File(path);
        if (!file.exists()) path = "resources/img/noimage.jpg";
        this.imagedir=path;
        setIcon(GetIcon());
        //setBorder(border);
        setToolTipText("Click to view full size");
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                ViewFullImage();
            }
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    //Resize Image
    private Image getScaledImage(Image img){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    //Return Icon With Optional Size
    private ImageIcon GetIcon(){
    ImageIcon icon=new ImageIcon(imagedir);
    ImageIcon newicon = new ImageIcon(getScaledImage(icon.getImage()));
    return newicon;
    }

    //Create Full Image Panel
    private class ImagePanel extends JPanel {
        private Image img;
        public int w, h;
        //JLabel j=new JLabel();

        public ImagePanel() throws IOException {
            img = ImageIO.read(new File(imagedir));
            w = img.getWidth(this);
            h = img.getHeight(this) + 38;
        }

        @Override
        public void paint(Graphics g) {
            if (img != null) {
                g.drawImage(img, 0, 0, this);
            }
        }
    }

    //Create Full Image Window
    public void ViewFullImage(){
        ImagePanel ip = null;
        try{
             ip=new ImagePanel();
        } catch (IOException e){
        }
        final JFrame f=new JFrame("Full-Size Image "+ip.w+" x "+(ip.h-38));
        f.setIconImage(new ImageIcon("resources/img/logo.png").getImage());
        ip.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                f.dispose();
            }
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }
        });
        f.add(ip,BorderLayout.CENTER);
        f.setSize(ip.w,ip.h);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
