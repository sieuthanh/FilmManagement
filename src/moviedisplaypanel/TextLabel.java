package moviedisplaypanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class TextLabel {

    public JLabel NormalTextLabel(String label,int size,int x,int y){
        JLabel textlabel = new JLabel(label);
        Dimension d = textlabel.getPreferredSize();
        textlabel.setBounds(x,y,d.width,d.height);
        return (textlabel);
    }

    public JLabel BoldTextLabel(String label,int size,int x,int y){
        if (label.length()>55 && label.length()<65) size = size - 1;
        if (label.length()>=65) size = size - 3;
        final JLabel bold = new JLabel(label);
        bold.setFont(new Font("Tahoma", Font.BOLD, size));
        Dimension d = bold.getPreferredSize();
        bold.setBounds(x,y,d.width,d.height);
        return bold;
    }

    public JLabel ActionLabel (String oldlabel,final int size){
        String label = "<html><u>"+oldlabel+"</u></html>";
        final JLabel actionlabel = new JLabel(label);
        actionlabel.setForeground(Color.blue);

        actionlabel.addMouseListener(new MouseAdapter() {
            @Override
        public void mouseEntered(MouseEvent e) {
            actionlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
            @Override
        public void mouseExited(MouseEvent e) {
            }
        });
        return actionlabel;
    }

    public JLabel BoldItalicTextLabel(String label,int size,int x,int y){
        JLabel bold = new JLabel(label);
        bold.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,size));
        Dimension d = bold.getPreferredSize();
        bold.setBounds(x,y,d.width,d.height);
        return bold;
    }
}
