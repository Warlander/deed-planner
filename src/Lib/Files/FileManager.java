package Lib.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    
    private static final String slash = System.getProperty("file.separator");
    private static final String br = System.getProperty("line.separator");
    private static final String home = System.getProperty("user.home");
    
    public static void saveToFile(String path, String value) {
        path = path.replace("/", slash).replace("\\", slash);
        String sav = home+slash+"HouseCalc"+slash+path;
        sav = sav.substring(0, sav.lastIndexOf(slash));
        File file = new File(sav);
        file.mkdirs();
        path = home+slash+"HouseCalc"+slash+path;
        file = new File(path);
        char[] arr = value.toCharArray();
        try {
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            for (int i=0; i<arr.length; i++) {
                out.write((byte)arr[i]);
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createDirectory(String path) {
        path = path.replace("/", slash).replace("\\", slash);
        path = home+slash+"HouseCalc"+slash+path;
        new File(path).mkdirs();
    }
    
    public static File getFile(String path) {
        path = path.replace("/", slash).replace("\\", slash);
        path = home+slash+"HouseCalc"+slash+path;
        return new File(path);
    }
    
    public static void deleteFile(String path) {
        path = path.replace("/", slash).replace("\\", slash);
        path = home+slash+"HouseCalc"+slash+path;
        new File(path).delete();
    }
    
    public static boolean fileExists(String path) {
        path = path.replace("/", slash).replace("\\", slash);
        path = home+slash+"HouseCalc"+slash+path;
        return new File(path).exists();
    }
    
}
