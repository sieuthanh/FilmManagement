package companydisplaypanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import moviedisplaypanel.*;

/**
 *
 * @author uNstOppAbLe
 */
public class MovieNameLabel extends JLabel {
    private String shortname, name, id;

    public MovieNameLabel (String shortname, final String name, final String id)
    {
        super();
        this.shortname = shortname;
        this.name = name;
        this.id = id;
        shortname = "<html><u><b><center>" + shortname + "</center></b></u></html>";
        setText(shortname);
        setHorizontalAlignment(JLabel.CENTER);
        setForeground(Color.BLUE);
        setToolTipText(name);
        addMouseListener(new MouseAdapter() {            
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    public void MovieDisplayFrame(String id, String name)
    {
        JFrame frame = new JFrame(name);
        MovieDisplay md = new MovieDisplay(id);
        frame.add(md);
        frame.setSize(650,490);
        JScrollPane sp = new JScrollPane(md);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        frame.add(sp);
        frame.setVisible(true);
    }

    public String getID() {
        return id;
    }
}
