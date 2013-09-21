package Mapper.Input;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class MouseInput {
    
    public static pressed pressed = new pressed();
    public static hold hold = new hold();
    public static released released = new released();
    
    public static boolean scrollUp=false;
    public static boolean scrollDown=false;
    
    public static float x = 0;
    public static float y = 0;
    
    public static class pressed {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public static class hold {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public static class released {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    static {
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(MouseInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private MouseInput() {}
    
    public static void update() {
        x = Mouse.getX();
        y = Mouse.getY();
        left();
        right();
        middle();
        wheel();
    }
    
    public static void setMouseGrabbed(boolean set) {
        Mouse.setGrabbed(set);
    }
    
    public static boolean isMouseGrabbed() {
        return Mouse.isGrabbed();
    }
    
    private static void left() {
        if (Mouse.isButtonDown(0)) {
            if (!pressed.left && !hold.left) {
                pressed.left = true;
                hold.left = true;
                released.left = false;
            }
            else if (pressed.left && hold.left) {
                pressed.left = false;
                hold.left = true;
                released.left = false;
            }
        }
        else {
            if (hold.left) {
                pressed.left = false;
                hold.left = false;
                released.left = true;
            }
            else {
                pressed.left = false;
                hold.left = false;
                released.left = false;
            }
        }
    }
    
    private static void right() {
        if (Mouse.isButtonDown(1)) {
            if (!pressed.right && !hold.right) {
                pressed.right = true;
                hold.right = true;
                released.right = false;
            }
            else if (pressed.right && hold.right) {
                pressed.right = false;
                hold.right = true;
                released.right = false;
            }
        }
        else {
            if (hold.right) {
                pressed.right = false;
                hold.right = false;
                released.right = true;
            }
            else {
                pressed.right = false;
                hold.right = false;
                released.right = false;
            }
        }
    }
    
    private static void middle() {
        if (Mouse.isButtonDown(2)) {
            if (!pressed.middle && !hold.middle) {
                pressed.middle = true;
                hold.middle = true;
                released.middle = false;
            }
            else if (pressed.middle && hold.middle) {
                pressed.middle = false;
                hold.middle = true;
                released.middle = false;
            }
        }
        else {
            if (hold.middle) {
                pressed.middle = false;
                hold.middle = false;
                released.middle = true;
            }
            else {
                pressed.middle = false;
                hold.middle = false;
                released.middle = false;
            }
        }
    }
    
    private static void wheel() {
        int mouse = Mouse.getDWheel();
        if (mouse>0) {
            scrollUp=true;
            scrollDown=false;
        }
        else if (mouse<0) {
            scrollUp=false;
            scrollDown=true;
        }
        else {
            scrollUp=false;
            scrollDown=false;
        }
    }
    
}
