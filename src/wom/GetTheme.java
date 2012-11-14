package wom;

import moviedisplaypanel.ReadFile;

/**
 *
 * @author Admin
 */
public class GetTheme {

    public GetTheme (){
        
    }

    public String Theme() {
        ReadFile theme = new ReadFile ("resources/theme/theme.cfg");
        String result = theme.ReadOneLine();
        return result;
    }
}
