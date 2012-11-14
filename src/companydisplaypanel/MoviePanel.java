package companydisplaypanel;

import databasemanager.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author uNstOppAbLe
 */
public class MoviePanel extends JPanel {
    private Insets insets = getInsets();
    private LinkedList moviebycompany = new LinkedList();
    private DatabaseManager databaseManager = new DatabaseManager();
    private int n, year, row, col, tmp;
    private Movie movie;
    private String name, shortname, path = "data/films/", imagepath;
    private PosterLabel thumblabel;
    private MovieNameLabel label;
    private Dimension d;
    private List namelabellist = new LinkedList();
    
    public MoviePanel (String id)
    {
        super();
        setLayout(null);
        moviebycompany = databaseManager.getMovieByCompany(id);
        n = moviebycompany.size();

        if (!moviebycompany.isEmpty())
        {
            for (int i=0; i<n; i++)
            {
                movie = (Movie)moviebycompany.get(i);

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
