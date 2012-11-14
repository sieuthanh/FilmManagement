package companydisplaypanel;

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

/**
 *
 * @author uNstOppAbLe
 */
public class CompanyDisplay extends JPanel {
    private DatabaseManager databaseManager = new DatabaseManager();
    private Company company;
    private Dimension d;
    private NameLabel nameLabel;
    private MoviePanel moviePanel;
    private int n;
    private Border border = BorderFactory.createLineBorder(Color.lightGray);
    private Border border1 = BorderFactory.createTitledBorder(border, "Movies",
            TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
            Font.BOLD,14), Color.black);
    
    
    public CompanyDisplay (String id)
    {
        super();
        setLayout(null);
        setBackground(Color.white);
        Insets insets = getInsets();
        company = databaseManager.getCompanyInfo(id);

        //Add name label
        nameLabel = new NameLabel(company.getName());
        add(nameLabel);
        d = nameLabel.getPreferredSize();
        nameLabel.setBounds(insets.left, insets.top, d.width, d.height);

        //Add list movie by company
        moviePanel = new MoviePanel(id);
        add(moviePanel);
        d = moviePanel.getPreferredSize();
        moviePanel.setBounds(insets.left, insets.top+80, d.width, d.height);
        moviePanel.setBorder(border1);
        n = moviePanel.getHeight();
        setPreferredSize(new Dimension(615,80+n));
    }

    public List getNameLabelList() {
        return moviePanel.getNameLabelList();
    }
}
