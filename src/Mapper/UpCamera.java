package Mapper;

import Form.HouseCalc;
import static Form.HouseCalc.floorLabel;
import Lib.Object.DataLoader;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class UpCamera {
    
    public static float x=0;
    public static float y=0;
    
    public static int scale=10;
    
    private int pastMousex;
    private int pastMousey;
    
    public static float mouseFraction = 0.2f;
    public static float keyboardFraction = 1;
    public static boolean showGrid=true;
    public static boolean allowWheel=true;
    
    public void update(KeyboardInput keyboard, MouseInput mouse) {
        float keyboardFraction = this.keyboardFraction;
        if (keyboard.hold[Keyboard.KEY_LSHIFT]) {
            keyboardFraction*=FPPCamera.shiftMod;
        }
        else if (keyboard.hold[Keyboard.KEY_LCONTROL]) {
            keyboardFraction*=FPPCamera.controlMod;
        }
        
        int currX = (int)(x/4);
        int currZ = (int)(y/4);
        Camera.visibleDownX = currX-5;
        Camera.visibleUpX = currX+scale*2;
        Camera.visibleDownY = currZ-5;
        Camera.visibleUpY = currZ+scale*2;
        
        if (allowWheel) {
            if (mouse.scrollUp) {
                if (scale>5) {
                    scale--;
                }
            }
            else if (mouse.scrollDown) {
                if (scale<20) {
                    scale++;
                }
            }
        }
        else {
            if ((mouse.scrollUp ^ mouse.scrollDown) && !(keyboard.hold[Keyboard.KEY_LSHIFT] || keyboard.hold[Keyboard.KEY_LCONTROL])) {
                JScrollPane pane = (JScrollPane)HouseCalc.contextPane.getSelectedComponent();
                JList list = (JList)((JViewport)pane.getComponent(0)).getView();
                if (mouse.scrollUp) {
                    list.setSelectedIndex(list.getSelectedIndex()-1);
                }
                else if (mouse.scrollDown) {
                    list.setSelectedIndex(list.getSelectedIndex()+1);
                }
            }
        }
        if (keyboard.pressed[Keyboard.KEY_Q]) {
            if (Mapper.z>0) {
                Mapper.z--;
            }
            if (Mapper.z==-1) {
                HouseCalc.groundsList.setModel(DataLoader.caveGrounds);
                HouseCalc.groundsList.setSelectedIndex(0);
            }
            floorLabel.setText("Floor "+(Mapper.z+1));
        }
        else if (keyboard.pressed[Keyboard.KEY_E]) {
            if (Mapper.z<15-1) {
                Mapper.z++;
            }
            if (Mapper.z==0) {
                HouseCalc.groundsList.setModel(DataLoader.grounds);
                HouseCalc.groundsList.setSelectedIndex(0);
            }
            floorLabel.setText("Floor "+(Mapper.z+1));
        }
        
        if (mouse.pressed.middle) {
            Mouse.setGrabbed(true);
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (mouse.hold.middle) {
            float diffx = mouse.x - pastMousex;
            float diffy = mouse.y - pastMousey;
            
            y-=diffx*mouseFraction;
            x-=diffy*mouseFraction;
            
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (mouse.released.middle && !mouse.hold.middle) {
            mouse.setMouseGrabbed(false);
        }
        
        if (keyboard.hold[Keyboard.KEY_W]) {
            x+=keyboardFraction;
        }
        if (keyboard.hold[Keyboard.KEY_S]) {
            x-=keyboardFraction;
        }
        
        if (keyboard.hold[Keyboard.KEY_D]) {
            y+=keyboardFraction;
        }
        if (keyboard.hold[Keyboard.KEY_A]) {
            y-=keyboardFraction;
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
    
    public void set() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, scale*4*Display.getWidth()/Display.getHeight(), 0, scale*4, 0.001f, 600);
        GL11.glRotatef(120, 1, 1, 1);
        GL11.glTranslatef(-x, -300, -y);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public static void reset() {
        x=0;
        y=0;
    }
    
}
