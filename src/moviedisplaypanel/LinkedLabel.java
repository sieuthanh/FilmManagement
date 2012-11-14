package moviedisplaypanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class LinkedLabel extends JLabel {
    private String id;

    public LinkedLabel (String oldlabel,String id,final int size){
        this.id = id;
        String label = "<html><u><b>"+oldlabel+"</b></u></html>";
        setText(label);
        setForeground(Color.blue);
        this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            });
        }

    public String getId(){
        return id;
    }
}

