package Mapper;

import Form.HouseCalc;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class FPPCamera {
    
    private int pastMousex;
    private int pastMousey;
    
    public static float fraction = 0.2f;
    public static float rotate = 0.015f;
    
    public static float shiftMod = 5;
    public static float controlMod = 0.2f;
    
    public static float posx=0;
    public static float posy=2;
    public static float posz=0;
    
    private float dirx=1;
    private float diry=0;
    private float dirz=0;
    
    private float anglex=(float)Math.PI/2;
    private float angley=0;
    
    public void setSpeed(float speed, float rotation) {
        fraction=speed;
        rotate=rotation;
    }
    
    public void update(KeyboardInput keyboard, MouseInput mouse) {
        float fraction = this.fraction;
        if (keyboard.hold[Keyboard.KEY_LSHIFT]) {
            fraction*=shiftMod;
        }
        else if (keyboard.hold[Keyboard.KEY_LCONTROL]) {
            fraction*=controlMod;
        }
        
        int currX = (int)(posx/4);
        int currZ = (int)(posz/4);
        Camera.visibleDownX = currX-40;
        Camera.visibleUpX = currX+40;
        Camera.visibleDownY = currZ-40;
        Camera.visibleUpY = currZ+40;
        
        if (mouse.pressed.left) {
            Mouse.setGrabbed(true);
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (mouse.hold.left) {
            float diffx = mouse.x - pastMousex;
            float diffy = mouse.y - pastMousey;
            
            angley += diffx*rotate;
            
            anglex += diffy*rotate;
            
            if (angley>Math.PI*2) {
                angley-=Math.PI*2;
            }
            else if (angley<0) {
                angley+=Math.PI*2;
            }
            
            if (anglex>Math.PI-0.01f) {
                anglex=(float)Math.PI-0.01f;
            }
            else if (anglex<0.01f) {
                anglex=0.01f;
            }
            
            dirx = (float)(Math.cos(angley)*Math.sin(anglex));
            dirz = (float)(Math.sin(angley)*Math.sin(anglex));
            diry = (float)-Math.cos(anglex);
            
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (mouse.released.left) {
            mouse.setMouseGrabbed(false);
        }
        if (keyboard.hold[Keyboard.KEY_D]) {
            posx += (float)(Math.cos(angley+Math.PI/2))*fraction;
            posz += (float)(Math.sin(angley+Math.PI/2))*fraction;
        }
        if (keyboard.hold[Keyboard.KEY_A]) {
            
            posx += (float)(Math.cos(angley-Math.PI/2))*fraction;
            posz += (float)(Math.sin(angley-Math.PI/2))*fraction;
        }
        if (keyboard.hold[Keyboard.KEY_W]) {
            posx += dirx * fraction;
            posz += dirz * fraction;
            posy += diry * fraction;
        }
        if (keyboard.hold[Keyboard.KEY_S]) {
            posx -= dirx * fraction;
            posz -= dirz * fraction;
            posy -= diry * fraction;
        }
    }
    
    public void set() {
        GL11.glViewport(0, 0, HouseCalc.programFrame.getWidth(), HouseCalc.programFrame.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float width = HouseCalc.programFrame.getWidth();
        float height = HouseCalc.programFrame.getHeight();
        GLU.gluPerspective(70, width/height, 0.1f, 260.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GLU.gluLookAt(posx, posy, posz, posx+dirx, posy+diry, posz+dirz, 0, 1, 0);
    }
    
    public static void reset() {
        posx=0;
        posy=2;
        posz=0;
    }
    
}
