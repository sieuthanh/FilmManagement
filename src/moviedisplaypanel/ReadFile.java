package moviedisplaypanel;

import java.io.*;
import java.util.*;

public final class ReadFile {
    private String content="";
    private FileInputStream is;
    private String filedir;
    public Scanner input;
    public ReadFile(String filedir){
            this.filedir = filedir;
            if (CheckFile())
            {
                try{
                this.is=new FileInputStream(filedir);
                this.input=new Scanner (this.is);}
                catch (Exception e) {}
            }
            
    }

    public boolean CheckFile () {
        File file = new File(filedir);
        if (!file.exists()) return false;
        return true;
    }

    public void Close(){
        try {
                is.close();
        } catch (Exception e) {
          }
        input.close();
    }

    public String ReadAllFile() {
        if (CheckFile()) {
        while (this.input.hasNextLine()){
              content+=this.input.nextLine()+"\n";
            }
        }
        return content;
     }

    public String ReadOneLine(){
        if (CheckFile()) {
        if (this.input.hasNextLine()) return (this.input.nextLine());
        else return null;
        }
        return "";
    }
}
