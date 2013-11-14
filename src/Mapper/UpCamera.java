package Mapper;

import Mapper.Input.MouseInput;
import Form.HouseCalc;
import Lib.Files.Properties;
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
    
    private static int pastMousex;
    private static int pastMousey;
    
    private static final FloatBuffer matrix;
    
    static {
        matrix = BufferUtils.createFloatBuffer(16);
        matrix.put(new float[] {1, 0, 0, 0,
                                0, 0, 1, 0,
                                0, 1, 0, 0,
                                0, 0, 0, 1}).flip();
    }
    
    static void update() {
        double keyboardFraction = Properties.keyboardFractionUp;
        if (Keybindings.hold(Keybindings.UP_SPEED_MOD1)) {
            keyboardFraction*=Properties.mod1Fpp;
        }
        else if (Keybindings.hold(Keybindings.UP_SPEED_MOD2)) {
            keyboardFraction*=Properties.mod2Fpp;
        }
        
        int currX = (int)(x/4);
        int currZ = (int)(y/4);
        Camera.visibleDownX = currX-5;
        Camera.visibleUpX = currX+Properties.scale*2;
        Camera.visibleDownY = currZ-5;
        Camera.visibleUpY = currZ+Properties.scale*2;
        
        if (Keybindings.pressed(Keybindings.UP_SCALE_MORE)) {
            if (Properties.scale>5) {
                Properties.scale--;
            }
        }
        else if (Keybindings.pressed(Keybindings.UP_SCALE_LESS)) {
            if (Properties.scale<40) {
                Properties.scale++;
            }
        }
        
        if (Properties.allowWheel) {
            if (MouseInput.scrollUp) {
                if (Properties.scale>5) {
                    Properties.scale--;
                }
            }
            else if (MouseInput.scrollDown) {
                if (Properties.scale<40) {
                    Properties.scale++;
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
            
            x-=diffx*Properties.mouseFractionUp;
            y-=diffy*Properties.mouseFractionUp;
            
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
        
        if (y>4*(Mapper.height-Properties.scale)) {
            y=4*(Mapper.height-Properties.scale);
        }
        if (x>4*(Mapper.width-Properties.scale)) {
            x=4*(Mapper.width-Properties.scale);
        }
    }
    
    public static void set() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Properties.scale*4*Display.getWidth()/Display.getHeight(), 0, Properties.scale*4, 0.001f, 600);
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
