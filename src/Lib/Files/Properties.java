package Lib.Files;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Properties {
    
    public static boolean allowWheel = false;
    public static boolean checkVersion = true;
    public static boolean showGrid = true;
    public static boolean useMipmaps = true;
    
    public static int scale = 15;
    public static int antialiasing = 4;
    
    public static double cameraRotationFpp = 0.015;
    public static double keyboardFractionUp = 1;
    public static double mod1Fpp = 5;
    public static double mod2Fpp = 0.2;
    public static double mouseFractionUp = 0.2;
    public static double mouseFractionFpp = 0.2;
    
    private static final transient String br = System.getProperty("line.separator");
    
    static {
        try {
            loadProperties();
            saveProperties();
        } catch (FileNotFoundException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void wake() {}
    
    private static void loadProperties() throws FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        if (FileManager.fileExists("Properties/Config.prop")) {
            Scanner scan = new Scanner(new FileReader(FileManager.getFile("Properties/Config.prop")));
            while (scan.hasNext()) {
                String type = scan.next();
                if (!scan.hasNext()) {
                    System.err.println("Warning: corrupted config file");
                    return;
                }
                String param = scan.next();
                if (!scan.hasNext()) {
                    System.err.println("Warning: corrupted config file");
                    return;
                }
                scan.next();
                if (!scan.hasNext()) {
                    System.err.println("Warning: corrupted config file");
                    return;
                }
                String value = scan.next();
                
                for (Field f : Properties.class.getDeclaredFields()) {
                    f.setAccessible(true);
                    
                    if (f.getName().equals(param)) {
                        switch (type) {
                            case "boolean":
                                f.setBoolean(null, Boolean.parseBoolean(value));
                                break;
                            case "byte":
                                f.setByte(null, Byte.parseByte(value));
                                break;
                            case "short":
                                f.setShort(null, Short.parseShort(value));
                                break;
                            case "int":
                                f.setInt(null, Integer.parseInt(value));
                                break;
                            case "long":
                                f.setLong(null, Long.parseLong(value));
                                break;
                            case "double":
                                f.setDouble(null, Double.parseDouble(value));
                                break;
                            case "float":
                                f.setFloat(null, Float.parseFloat(value));
                                break;
                            default:
                                f.set(null, value);
                                break;
                        }
                        break;
                    }
                }
            }
            scan.close();
        }
    }
    
    public static void saveProperties() {
        StringBuilder build = new StringBuilder();
        for (Field f : Properties.class.getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (Modifier.isTransient(f.getModifiers())) continue;
                build.append(f.getType().getCanonicalName()).append(" ").append(f.getName()).append(" = ").append(f.get(null));
                build.append(br);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FileManager.saveToFile("Properties/Config.prop", build.toString());
    }
    
}
