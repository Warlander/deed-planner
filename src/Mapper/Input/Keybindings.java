package Mapper.Input;

import Lib.Files.FileManager;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;

public final class Keybindings {

    private static final Map<String, Integer> keyToInt = new HashMap<>();
    private static final Map<Integer, String> intToKey = new HashMap<>();
    private static final Map<String, Integer> bindToInt = new HashMap<>();
    private static final Map<Integer, String> intToBind = new HashMap<>();
    private static final Map<Integer, Integer> defaults = new HashMap<>();
    private static final Map<Integer, Integer> bindToKey = new HashMap<>();
    
    public static final int FPP_MOVE_UP = 1,
                            FPP_MOVE_LEFT = 2,
                            FPP_MOVE_DOWN = 3,
                            FPP_MOVE_RIGHT = 4,
                            FPP_ELEVATION_UP = 5,
                            FPP_ELEVATION_DOWN = 6,
                            FPP_CAMERA_UP = 7,
                            FPP_CAMERA_UP_ALT = 8,
                            FPP_CAMERA_DOWN = 9,
                            FPP_CAMERA_DOWN_ALT = 10,
                            FPP_CAMERA_LEFT = 11,
                            FPP_CAMERA_LEFT_ALT = 12,
                            FPP_CAMERA_RIGHT = 13,
                            FPP_CAMERA_RIGHT_ALT = 14,
                            FPP_SPEED_MOD1 = 15,
                            FPP_SPEED_MOD2 = 16,
                            UP_MOVE_UP = 17,
                            UP_MOVE_LEFT = 18,
                            UP_MOVE_DOWN = 19,
                            UP_MOVE_RIGHT = 20,
                            UP_ELEVATION_UP = 21,
                            UP_ELEVATION_DOWN = 22,
                            UP_SCALE_MORE = 23,
                            UP_SCALE_LESS = 24,
                            UP_SPEED_MOD1 = 25,
                            UP_SPEED_MOD2 = 26,
                            OTHER_SCREENSHOT = 27;
    
    private Keybindings() {}
    
    static {
        prepareVKMap();
        prepareBindMap();
        prepareDefaults();
        prepareKeybindings();
        saveKeybindings();
    }
    
    public static boolean pressed(int key) {
        return KeyboardInput.pressed[bindToKey.get(key)];
    }
    
    public static boolean hold(int key) {
        return KeyboardInput.hold[bindToKey.get(key)];
    }
    
    public static boolean released(int key) {
        return KeyboardInput.released[bindToKey.get(key)];
    }
    
    private static void prepareVKMap() {
        Field[] fields = Keyboard.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.startsWith("KEY_")) {
                try {
                    keyToInt.put(name.substring("KEY_".length()).toUpperCase(), field.getInt(null));
                    intToKey.put(field.getInt(null), name.substring("KEY_".length()).toUpperCase());
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private static void prepareBindMap() {
        Field[] fields = Keybindings.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (field.getType()==int.class) {
                try {
                    bindToInt.put(name, field.getInt(null));
                    intToBind.put(field.getInt(null), name);
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private static void prepareDefaults() {
        defaults.put(FPP_MOVE_UP, Keyboard.KEY_W);
        defaults.put(FPP_MOVE_LEFT, Keyboard.KEY_A);
        defaults.put(FPP_MOVE_DOWN, Keyboard.KEY_S);
        defaults.put(FPP_MOVE_RIGHT, Keyboard.KEY_D);
        defaults.put(FPP_ELEVATION_UP, Keyboard.KEY_R);
        defaults.put(FPP_ELEVATION_DOWN, Keyboard.KEY_F);
        defaults.put(FPP_CAMERA_UP, Keyboard.KEY_I);
        defaults.put(FPP_CAMERA_UP_ALT, Keyboard.KEY_T);
        defaults.put(FPP_CAMERA_DOWN, Keyboard.KEY_K);
        defaults.put(FPP_CAMERA_DOWN_ALT, Keyboard.KEY_G);
        defaults.put(FPP_CAMERA_LEFT, Keyboard.KEY_J);
        defaults.put(FPP_CAMERA_LEFT_ALT, Keyboard.KEY_Q);
        defaults.put(FPP_CAMERA_RIGHT, Keyboard.KEY_L);
        defaults.put(FPP_CAMERA_RIGHT_ALT, Keyboard.KEY_E);
        defaults.put(FPP_SPEED_MOD1, Keyboard.KEY_LSHIFT);
        defaults.put(FPP_SPEED_MOD2, Keyboard.KEY_LCONTROL);
        defaults.put(UP_MOVE_UP, Keyboard.KEY_W);
        defaults.put(UP_MOVE_LEFT, Keyboard.KEY_A);
        defaults.put(UP_MOVE_DOWN, Keyboard.KEY_S);
        defaults.put(UP_MOVE_RIGHT, Keyboard.KEY_D);
        defaults.put(UP_ELEVATION_UP, Keyboard.KEY_E);
        defaults.put(UP_ELEVATION_DOWN, Keyboard.KEY_Q);
        defaults.put(UP_SCALE_MORE, Keyboard.KEY_R);
        defaults.put(UP_SCALE_LESS, Keyboard.KEY_F);
        defaults.put(UP_SPEED_MOD1, Keyboard.KEY_LSHIFT);
        defaults.put(UP_SPEED_MOD2, Keyboard.KEY_LCONTROL);
        defaults.put(OTHER_SCREENSHOT, Keyboard.KEY_F11);
    }
    
    private static void prepareKeybindings() {
        Scanner scan = null;
        
        try {
            File file = FileManager.getFile("Properties/Keybindings.prop");
            File path = FileManager.getFile("Properties");
            if (!file.exists()) {
                try {
                    path.mkdirs();
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            scan = new Scanner(new FileInputStream(file));
            
            while (scan.hasNext()) {
                int bind = bindToInt.get(scan.next());
                scan.next();
                int key = keyToInt.get(scan.next());
                bindToKey.put(bind, key);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            scan.close();
        }
        
        for (Map.Entry<Integer, Integer> entry : defaults.entrySet()) {
            if (!bindToKey.containsKey(entry.getKey())) {
                bindToKey.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    private static void saveKeybindings() {
        PrintStream out = null;
        try {
            out = new PrintStream(FileManager.getFile("Properties/Keybindings.prop"));
            for (Map.Entry<Integer, Integer> entry : bindToKey.entrySet()) {
                String key = intToBind.get(entry.getKey());
                String value = intToKey.get(entry.getValue());
                out.println(key+" = "+value);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
}
