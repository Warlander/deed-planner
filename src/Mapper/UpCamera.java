package Mapper;

import Mapper.Input.MouseInput;
import Form.HouseCalc;
import Lib.Utils.MatrixTools;
import Mapper.Input.Keybindings;
import java.nio.FloatBuffer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class UpCamera {
    
    public static float x=0;
    public static float y=0;
    
    public static int scale=10;
    
    private static int pastMousex;
    private static int pastMousey;
    
    public static float mouseFraction = 0.2f;
    public static float keyboardFraction = 1;
    public static boolean showGrid=true;
    public static boolean allowWheel=true;
    
    private static final FloatBuffer matrix;
    
    static {
        matrix = BufferUtils.createFloatBuffer(16);
        matrix.put(new float[] {1, 0, 0, 0,
                                0, 0, 1, 0,
                                0, 1, 0, 0,
                                0, 0, 0, 1}).flip();
    }
    
    static void update() {
        float keyboardFraction = UpCamera.keyboardFraction;
        if (Keybindings.hold(Keybindings.UP_SPEED_MOD1)) {
            keyboardFraction*=FPPCamera.shiftMod;
        }
        else if (Keybindings.hold(Keybindings.UP_SPEED_MOD2)) {
            keyboardFraction*=FPPCamera.controlMod;
        }
        
        int currX = (int)(x/4);
        int currZ = (int)(y/4);
        Camera.visibleDownX = currX-5;
        Camera.visibleUpX = currX+scale*2;
        Camera.visibleDownY = currZ-5;
        Camera.visibleUpY = currZ+scale*2;
        
        if (Keybindings.pressed(Keybindings.UP_SCALE_MORE)) {
            if (scale>5) {
                scale--;
            }
        }
        else if (Keybindings.pressed(Keybindings.UP_SCALE_LESS)) {
            if (scale<40) {
                scale++;
            }
        }
        
        if (allowWheel) {
            if (MouseInput.scrollUp) {
                if (scale>5) {
                    scale--;
                }
            }
            else if (MouseInput.scrollDown) {
                if (scale<40) {
                    scale++;
                }
            }
        }
        else {
            if ((MouseInput.scrollUp ^ MouseInput.scrollDown) && !(Keybindings.pressed(Keybindings.UP_SPEED_MOD1) || Keybindings.pressed(Keybindings.UP_SPEED_MOD2))) {
                JScrollPane pane = (JScrollPane)HouseCalc.contextPane.getSelectedComponent();
                JList list = (JList)((JViewport)pane.getComponent(0)).getView();
                if (MouseInput.scrollUp) {
                    list.setSelectedIndex(list.getSelectedIndex()-1);
                }
                else if (MouseInput.scrollDown) {
                    list.setSelectedIndex(list.getSelectedIndex()+1);
                }
            }
        }
        if (Keybindings.pressed(Keybindings.UP_ELEVATION_DOWN)) {
            Mapper.floorDown();
        }
        else if (Keybindings.pressed(Keybindings.UP_ELEVATION_UP)) {
            Mapper.floorUp();
        }
        
        if (MouseInput.pressed.middle) {
            Mouse.setGrabbed(true);
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (MouseInput.hold.middle) {
            float diffx = MouseInput.x - pastMousex;
            float diffy = MouseInput.y - pastMousey;
            
            x-=diffx*mouseFraction;
            y-=diffy*mouseFraction;
            
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (MouseInput.released.middle && !MouseInput.hold.middle) {
            MouseInput.setMouseGrabbed(false);
        }
        
        if (Keybindings.hold(Keybindings.UP_MOVE_UP)) {
            y+=keyboardFraction;
        }
        if (Keybindings.hold(Keybindings.UP_MOVE_DOWN)) {
            y-=keyboardFraction;
        }
        
        if (Keybindings.hold(Keybindings.UP_MOVE_RIGHT)) {
            x+=keyboardFraction;
        }
        if (Keybindings.hold(Keybindings.UP_MOVE_LEFT)) {
            x-=keyboardFraction;
        }
        
        if (y<0) {
            y=0;
        }
        if (x<0) {
            x=0;
        }
        
        if (y>4*(Mapper.height-scale)) {
            y=4*(Mapper.height-scale);
        }
        if (x>4*(Mapper.width-scale)) {
            x=4*(Mapper.width-scale);
        }
    }
    
    public static void set() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, scale*4*Display.getWidth()/Display.getHeight(), 0, scale*4, 0.001f, 600);
        MatrixTools.multMatrix(matrix);
        GL11.glTranslatef(-x, -300, -y);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public static void reset() {
        x=0;
        y=0;
    }
    
}
