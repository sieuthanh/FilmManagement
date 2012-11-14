package moviedisplaypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class CheckLink implements PropertyChangeListener{

    private JPanel textpanel = new JPanel();
    private String get,link;
    private JTextArea linkarea = new JTextArea();
    private String path;
    private JProgressBar progressbar;
    private Task task;
    private String s = "";
    private JFrame progressframe = new JFrame("Progress");
    private JPanel progresspanel = new JPanel();
    private JButton checklink;

    public CheckLink(String path,JButton checklink){
        this.path = path;
        this.checklink = checklink;
        linkarea.setEditable(false);
        textpanel.setBackground(Color.white);
        
        progressbar = new JProgressBar(0, 100);
        progressbar.setPreferredSize(new Dimension(600,40));
        progressbar.setValue(0);
        progressbar.setStringPainted(true);
        progressbar.setIndeterminate(true);
        progresspanel.add(progressbar);
        progressframe.add(progresspanel);
        progressframe.setSize(650,90);
        progressframe.setIconImage(new ImageIcon("resources/img/logo.png").getImage());
        progressframe.setLocationRelativeTo(null);
        progressframe.setAlwaysOnTop(true);
        progressframe.setVisible(true);
        task = new Task();
        task.addPropertyChangeListener(this);
        PropertyChangeEvent evt = null;
        task.execute();

        progressframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                task.cancel(true);
          }
        });
    }

    public void propertyChange(PropertyChangeEvent evt) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            progressbar.setIndeterminate(false);
            progressbar.setValue(progress);
        }
    }

    private class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            float progress = 0;
            int i = 0;
            int line = 0;
            setProgress(0);
            ReadFile rf = new ReadFile(path+"Link.txt");
            while((get=rf.ReadOneLine())!=null)
                if (get.indexOf("http://megashare.vn")!=-1 || get.indexOf("http://share.megaplus.vn")!=-1){
                    line ++;
                }
            rf.Close();
            rf = new ReadFile(path+"Link.txt");
            LinkChecker lc;
            while((get=rf.ReadOneLine())!=null){
                i++;
                link=get;
                if (link.indexOf("http://megashare.vn")==-1 && link.indexOf("http://share.megaplus.vn")==-1) {
                    s+=(link+"\n");
                    continue;
                }
                lc = new LinkChecker(link);
                s+=(lc.Check()+"\n");
                progress = 100*i/line;
                setProgress((int)(progress));
            }
            rf.Close();

            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            progressframe.dispose();
            JFrame f=new JFrame("LINK");
            linkarea.setAutoscrolls(true);
            linkarea.setText(s);
            JScrollPane sp = new JScrollPane(linkarea);
            textpanel.setLayout(null);
            sp.setBounds(0,0,585,372);
            sp.getVerticalScrollBar().setUnitIncrement(16);
            sp.getHorizontalScrollBar().setUnitIncrement(16);
            textpanel.add(sp);
            f.setIconImage(new ImageIcon("resources/img/logo.png").getImage());
            f.add(textpanel);
            f.setSize(600,410);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            checklink.setEnabled(true);
        }
    }
    
    private class LinkChecker{
        private String s;
        private String link;

        public LinkChecker(String link) {
            this.link = link;
        }

        public String getinfo() {
            String info = new String ();
            int count;
            for (count=0;count<s.length();count++ ){
                if (s.charAt(count)=='<') break;
            }
            info=s.substring(0,count);
            return info;
        }

        public String getname(){
            String mark="\"content_tx\">";
            int check=s.indexOf(mark)+13;
            s=s.substring(check,s.length());
            String name=getinfo();
            return name;
        }

        public String getcap(){
            String mark="\"content_tx\">";
            int check=s.indexOf(mark)+13;
            s=s.substring(check,s.length());
            String cap=getinfo();
            return cap;
        }

        public String Check(){
        try {
        	
            URL u = new URL(link);
            BufferedReader in=new BufferedReader(new InputStreamReader(u.openStream()));
            String line;
            this.s = "";
            while((line=in.readLine())!=null){
                s=s+line;
            }
          
            String mark=("file:</td>");
            int check=s.indexOf(mark);
            if (check==-1) return (link+"\tThe File has been removed or deleted !");
            else return (link+"\t"+getname()+"\t"+getcap());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Internet Connection Error !",
		   "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        return null;
        }
    }
}
