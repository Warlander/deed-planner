package Lib.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebTools {
    
    public static BufferedReader siteToReader(String url) {
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return in;
        } catch (IOException ex) {
            Logger.getLogger(WebTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
