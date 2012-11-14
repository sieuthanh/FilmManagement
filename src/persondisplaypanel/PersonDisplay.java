package persondisplaypanel;

import databasemanager.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class PersonDisplay extends JPanel {
    private String imagepath = "data/people/";
    private Dimension d = null;
    private PictureLabel pictureLabel;
    private NameLabel nameLabel;
    private RoleLabel roleLabel;
    private SexLabel sexLabel;
    private BornLabel bornLabel;
    private BirthplaceLabel birthplaceLabel;
    private Border border = BorderFactory.createLineBorder(Color.lightGray);
    private Border border1 = BorderFactory.createTitledBorder(border, "Filmography",
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
            Font.BOLD,14), Color.black);
    private Border border2 = BorderFactory.createTitledBorder(border, "Director",
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
            Font.BOLD,14), Color.black);
    private Integer n;
    private ActorFilmPanel actorFilmPanel;
    private DirectorFilmPanel directorFilmPanel;

    private Person person;
    private DatabaseManager databaseManager = new DatabaseManager();

    public PersonDisplay (String id)
    {
        super();
        setLayout(null);
        setBackground(Color.white);
        Insets insets = getInsets();
        imagepath = imagepath + id + "/1.jpg";
        person = databaseManager.getPersonInfo(id);        
        //Add picture label
        pictureLabel = new PictureLabel(imagepath, person.getName());
        add(pictureLabel);
        d = pictureLabel.getPreferredSize();
        pictureLabel.setBounds(insets.left, insets.top, d.width, d.height);

        //Add name label        
        nameLabel = new NameLabel(person.getName());
        add(nameLabel);
        d = nameLabel.getPreferredSize();
        nameLabel.setBounds(insets.left + 230, insets.top, d.width, d.height);

        //Add role label
        roleLabel = new RoleLabel(person.getIsdirector(),person.getIsstar(),person.getSex());
        add(roleLabel);
        d = roleLabel.getPreferredSize();
        roleLabel.setBounds(insets.left + 230, insets.top + 40, d.width, d.height);

        //Add sex label
        sexLabel = new SexLabel(person.getSex());
        add(sexLabel);
        d = sexLabel.getPreferredSize();
        sexLabel.setBounds(insets.left + 230, insets.top + 80, d.width, d.height);

        //Add born label
        bornLabel = new BornLabel(person.getDay(),person.getYear());
        add(bornLabel);
        d = bornLabel.getPreferredSize();
        bornLabel.setBounds(insets.left + 230, insets.top + 100, d.width, d.height);

        //Add birthplace label
        birthplaceLabel = new BirthplaceLabel(person.getBorn());
        add(birthplaceLabel);
        d = birthplaceLabel.getPreferredSize();
        birthplaceLabel.setBounds(insets.left + 230, insets.top + 120, d.width, d.height);

        //Add Actor Film Panel
        actorFilmPanel = new ActorFilmPanel(id);
        add(actorFilmPanel);
        d = actorFilmPanel.getPreferredSize();
        actorFilmPanel.setBounds(insets.left + 0, insets.top + 325, d.width, d.height);
        actorFilmPanel.setBorder(border1);
        n = actorFilmPanel.getHeight();

        //Add Director Film Panel
        directorFilmPanel = new DirectorFilmPanel(id);
        add(directorFilmPanel);
        d = directorFilmPanel.getPreferredSize();
        directorFilmPanel.setBounds(insets.left + 0, insets.top + 340 + n, d.width, d.height);
        directorFilmPanel.setBorder(border2);

        //Resize Main panel
        n = n + directorFilmPanel.getHeight();
        
        setPreferredSize(new Dimension(615,340+n));
    }

    public List getNameLabelList ()
    {
        List directorlist = directorFilmPanel.getNameLabelList();
        List list = actorFilmPanel.getNameLabelList();
        for (int i = 0; i < directorlist.size(); i ++)
            list.add(directorlist.get(i));
        return list;
    }
}
