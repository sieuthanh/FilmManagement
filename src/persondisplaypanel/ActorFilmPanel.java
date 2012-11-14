package persondisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import databasemanager.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 *
 * @author uNstOppAbLe
 */
public class ActorFilmPanel extends JPanel {

    private Insets insets = getInsets();
    private Dimension d;
    private MovieNameLabel label;
    private JLabel thumblabel;
    private List moviebystar = new LinkedList();
    private int n = 0, row, col, year, tmp;
    private String name, shortname;
    private String path = "data/films/", imagepath;
    private ImageIcon thumb;
    private Movie movie;
    private List namelabellist = new LinkedList();


    public ActorFilmPanel (String id)
    {
        setLayout(null);
        moviebystar = new DatabaseManager().getMovieByStar(id);
        n = moviebystar.size();

        if (!moviebystar.isEmpty())
        {
            for (int i=0; i<n; i++)
            {
                movie = (Movie)moviebystar.get(i);
                
                //Xu ly do dai ten film
                name = movie.getName();
                year = movie.getYear();
                if (name.length() >= 33) shortname = name.substring(0, 30) + "... (" + year + ")";
                else shortname = name + " (" + year + ")";

                //Add film poster
                row = (int)(i/4);
                col = i%4;  
                imagepath = path + movie.getId() + "/0.jpg";
                thumblabel = new PosterLabel(imagepath, name);
                add(thumblabel);
                d = thumblabel.getPreferredSize();
                thumblabel.setBounds(insets.left + 20 + col*150, insets.top + 20 + row*240, d.width, d.height);
                thumblabel.setBorder(BorderFactory.createLineBorder(Color.gray));

                //Add text
                name = movie.getName() + " (" + year + ")";
                label = new MovieNameLabel(shortname, name, movie.getId());
                add(label);
                namelabellist.add(label);
                label.setPreferredSize(new Dimension(120,40));
                d = label.getPreferredSize();
                label.setBounds(insets.left + 20 + col*150, insets.top + 200 + row*240, d.width, d.height);
            }
            if (n%4==0) tmp = n/4-1;
            else tmp = (int)(n/4);
            setPreferredSize(new Dimension(615,250 + tmp*240));
            setBackground(Color.white);
        }
        
    }


    //Add film title from list
    public JLabel TitleDisplay (String title)
    {
        title = "<html><u><b><center>" + title + "</center></b></u></html>";
        JLabel filmlabel = new JLabel(title);
        filmlabel.setHorizontalAlignment(JLabel.CENTER);
        filmlabel.setForeground(Color.BLUE);
        return filmlabel;
    }

    public int getHeight()
    {
        d = getPreferredSize();
        return d.height;
    }

    public List getNameLabelList()
    {
        return namelabellist;
    }
}