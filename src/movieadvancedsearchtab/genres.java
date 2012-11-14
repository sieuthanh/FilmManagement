
package movieadvancedsearchtab;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

/**
 *
 * @author Admin
 */
public class genres extends JPanel{
    public genres(){
        GridLayout gl = new GridLayout(4,5);
        setLayout(gl);
        this.setPreferredSize(new Dimension(600,100));
        this.add(new JCheckBox("Comedy"));
        this.add(new JCheckBox("Drama"));
        this.add(new JCheckBox("Romance"));
        this.add(new JCheckBox("Adventure"));
        this.add(new JCheckBox("Fantasy"));
        this.add(new JCheckBox("Action"));
        this.add(new JCheckBox("Crime"));
        this.add(new JCheckBox("Thriller"));
        this.add(new JCheckBox("Sci-Fi"));
        this.add(new JCheckBox("Horror"));
        this.add(new JCheckBox("Family"));
        this.add(new JCheckBox("History"));
        this.add(new JCheckBox("Mystery"));
        this.add(new JCheckBox("War"));
        this.add(new JCheckBox("Sport"));
        this.add(new JCheckBox("Western"));
        this.add(new JCheckBox("Animation"));
        this.add(new JCheckBox("Music"));
        this.add(new JCheckBox("Biography"));
        this.add(new JCheckBox("Musical"));
    }

    public void clear(){
        Component [] com = this.getComponents();
        for (int i=0;i<com.length;i++){
            JCheckBox cb=(JCheckBox)com[i];
            if(cb.isSelected()) cb.setSelected(false);
        }
    }
}
