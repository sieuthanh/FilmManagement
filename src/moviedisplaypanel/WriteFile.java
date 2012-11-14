package moviedisplaypanel;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class WriteFile {
    private String filepatch;
    private String text;
    public WriteFile(String filepatch, String text){
        this.filepatch=filepatch;
        this.text=text;
        File file = new File(filepatch);
        FileWriter fw;
        try{
            fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(text);
            out.close();
            //JOptionPane.showMessageDialog(null, "Save File Successfully !","Save File",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"ERROR ",JOptionPane.ERROR_MESSAGE);
        }
    }
}
