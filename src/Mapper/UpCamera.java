package Mapper;

import Form.HouseCalc;
import static Form.HouseCalc.floorLabel;
import Lib.Object.DataLoader;
import Lib.Utils.MatrixTools;
import java.nio.FloatBuffer;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class UpCamera {
    
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
        if (KeyboardInput.hold[Keyboard.KEY_LSHIFT]) {
            keyboardFraction*=FPPCamera.shiftMod;
        }
        else if (KeyboardInput.hold[Keyboard.KEY_LCONTROL]) {
            keyboardFraction*=FPPCamera.controlMod;
        }
        
        int currX = (int)(x/4);
        int currZ = (int)(y/4);
        Camera.visibleDownX = currX-5;
        Camera.visibleUpX = currX+scale*2;
        Camera.visibleDownY = currZ-5;
        Camera.visibleUpY = currZ+scale*2;
        
        if (KeyboardInput.pressed[Keyboard.KEY_R]) {
            if (scale>5) {
                scale--;
            }
        }
        else if (KeyboardInput.pressed[Keyboard.KEY_F]) {
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
            if ((MouseInput.scrollUp ^ MouseInput.scrollDown) && !(KeyboardInput.hold[Keyboard.KEY_LSHIFT] || KeyboardInput.hold[Keyboard.KEY_LCONTROL])) {
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
        if (KeyboardInput.pressed[Keyboard.KEY_Q]) {
            if (Mapper.y>0) {
                Mapper.y--;
            }
            if (Mapper.y==-1) {
                HouseCalc.groundsList.setModel(DataLoader.caveGrounds);
                HouseCalc.groundsList.setSelectedIndex(0);
            }
            floorLabel.setText("Floor "+(Mapper.y+1));
        }
        else if (KeyboardInput.pressed[Keyboard.KEY_E]) {
            if (Mapper.y<15-1) {
                Mapper.y++;
            }
            if (Mapper.y==0) {
                HouseCalc.groundsList.setModel(DataLoader.grounds);
                HouseCalc.groundsList.setSelectedIndex(0);
            }
            floorLabel.setText("Floor "+(Mapper.y+1));
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
        
        if (KeyboardInput.hold[Keyboard.KEY_W]) {
            y+=keyboardFraction;
        }
        if (KeyboardInput.hold[Keyboard.KEY_S]) {
            y-=keyboardFraction;
        }
        
        if (KeyboardInput.hold[Keyboard.KEY_D]) {
            x+=keyboardFraction;
        }
        if (KeyboardInput.hold[Keyboard.KEY_A]) {
            x-=keyboardFraction;
        }
        
        if (y<0) {
            y=0;
        }
        if (x<0) {
            x=0;
        }
        
        if (y>4*(Mapper.width-scale)) {
            y=4*(Mapper.width-scale);
        }
        if (x>4*(Mapper.height-scale)) {
            x=4*(Mapper.height-scale);
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
