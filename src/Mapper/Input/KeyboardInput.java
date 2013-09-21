package Mapper.Input;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public final class KeyboardInput {
    
    static final boolean[] pressed = new boolean[256];
    static final boolean[] hold = new boolean[256];
    static final boolean[] released = new boolean[256];
    
    static {
        try {
            Keyboard.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(KeyboardInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private KeyboardInput() {}
    
    public static void update() {
        for (int i=0; i<256; i++) {
            if (Keyboard.isKeyDown(i)) {
                if (!pressed[i] && !hold[i]) {
                    pressed[i] = true;
                    hold[i] = true;
                    released[i] = false;
                }
                else if (pressed[i] && hold[i]) {
                    pressed[i] = false;
                    hold[i] = true;
                    released[i] = false;
                }
            }
            else {
                if (hold[i]) {
                    pressed[i] = false;
                    hold[i] = false;
                    released[i] = true;
                }
                else {
                    pressed[i] = false;
                    hold[i] = false;
                    released[i] = false;
                }
            }
        }
    }
    
}
