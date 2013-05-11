package Mapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

public class MouseInput {
    
    public pressed pressed = new pressed();
    public hold hold = new hold();
    public released released = new released();
    
    public boolean scrollUp=false;
    public boolean scrollDown=false;
    
    public float x = 0;
    public float y = 0;
    
    public class pressed {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public class hold {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public class released {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    protected MouseInput() {
        try {
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(MouseInput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void update() {
        x = Mouse.getX();
        y = Mouse.getY();
        left();
        right();
        middle();
        wheel();
    }
    
    public void setMouseGrabbed(boolean set) {
        Mouse.setGrabbed(set);
    }
    
    public boolean isMouseGrabbed() {
        return Mouse.isGrabbed();
    }
    
    private void left() {
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
    
    private void right() {
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
    
    private void middle() {
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
    
    private void wheel() {
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
