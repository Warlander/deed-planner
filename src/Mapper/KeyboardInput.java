package Mapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class KeyboardInput {
    
    public boolean[] pressed = new boolean[256];
    public boolean[] hold = new boolean[256];
    public boolean[] released = new boolean[256];
    
    protected KeyboardInput() {
        try {
            Keyboard.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(KeyboardInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void update() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                pressed[Keyboard.getEventKey()]=true;
                hold[Keyboard.getEventKey()]=true;
            }
            else {
                released[Keyboard.getEventKey()]=true;
                hold[Keyboard.getEventKey()]=false;
            }
        }
    }
    
    protected void reset() {
        for (int i=0; i<pressed.length; i++) {
            pressed[i]=false;
            released[i]=false;
        }
    }
    
}
