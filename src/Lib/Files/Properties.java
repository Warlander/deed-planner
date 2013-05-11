package Lib.Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * List of all properties:
 * 
 * >>>Booleans:
 * 
 * allowWheel
 * checkVersion
 * javaCompile
 * showGrid
 * useMipmaps
 * 
 * >>>Integers:
 * 
 * cameraRotationFpp
 * scale
 * keyboardFractionUp
 * mod1Fpp
 * mod2Fpp
 * mouseFractionUp
 * mouseFractionFpp
 * antialiasing
 * 
 * >>>Strings:
 * 
 * 
 */
public class Properties {
    
    private static HashMap<String, Object> properties = new HashMap<>();
    
    private static String br = System.getProperty("line.separator");
    
    public static void setProperty(String name, Object value) {
        properties.put(name, value);
    }
    
    public static Object getProperty(String name) {
        if (properties.containsKey(name)) {
            String property = (String) properties.get(name);
            if (property.equals("false") || property.equals("true")) {
                return Boolean.parseBoolean(property);
            }
            boolean isNumber=true;
            char[] prop = property.toCharArray();
            for (char c : prop) {
                switch (c) {
                    case '0': case '1': case '2': case '3': case'4': case '5': case '6': case '7': case '8': case '9': case',': case '.':
                        break;
                    default:
                        isNumber=false;
                        break;
                }
                if (!isNumber) {
                    break;
                }
            }
            if (isNumber) {
                return Float.parseFloat(property);
            }
            if (!property.isEmpty()) {
                return property;
            }
        }
        return null;
    }
    
    public static void loadProperties() {
        try {
            if (FileManager.fileExists("Properties/Properties.prop")) {
                BufferedReader read = new BufferedReader(new FileReader(FileManager.getFile("Properties/Properties.prop")));
                String line;
                properties = new HashMap<>();
                while ((line=read.readLine())!=null) {
                    properties.put(line.substring(0, line.indexOf('=')), line.substring(line.indexOf('=')+1));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveProperties() {
        String result="";
        for (Map.Entry mEntry : properties.entrySet()) {
            result += mEntry.getKey()+"="+mEntry.getValue()+br;
        }
        FileManager.saveToFile("Properties/Properties.prop", result);
    }
    
}
