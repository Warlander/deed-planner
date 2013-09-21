package Mapper;

import Mapper.Input.MouseInput;
import Form.HouseCalc;
import Lib.Utils.MatrixTools;
import Mapper.Input.Keybindings;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public final class FPPCamera {
    
    public static boolean fixedHeight = false;
    
    private static int pastMousex;
    private static int pastMousey;
    
    public static float fraction = 0.2f;
    public static float rotate = 0.015f;
    
    public static float shiftMod = 5;
    public static float controlMod = 0.2f;
    
    public static double posx=0;
    public static double posy=2;
    public static double posz=0;
    
    private static float dirx=1;
    private static float diry=0;
    private static float dirz=0;
    
    private static float anglex=-(float)Math.PI/4;
    private static float angley=(float)Math.PI/2;
    
    private static double stickedHeight = 2;
    
    private static final FloatBuffer matrix;
    
    private FPPCamera() {}
    
    static {
        matrix = BufferUtils.createFloatBuffer(16);
        matrix.put(new float[] {
                                1, 0, 0, 0,
                                0, 1, 0, 0,
                                0, 0,-1, 0,
                                0, 0, 0, 1
        }).flip();
    }
    
    public static void setSpeed(float speed, float rotation) {
        fraction=speed;
        rotate=rotation;
    }
    
    public static void update() {
        float fraction = FPPCamera.fraction;
        if (Keybindings.hold(Keybindings.FPP_SPEED_MOD1)) {
            fraction*=shiftMod;
        }
        else if (Keybindings.hold(Keybindings.FPP_SPEED_MOD2)) {
            fraction*=controlMod;
        }
        
        double xDivine = posx/4d;
        double zDivine = posz/4d;
        
        int currX = (int)xDivine;
        int currZ = (int)zDivine;
        Camera.visibleDownX = currX-65;
        Camera.visibleUpX = currX+65;
        Camera.visibleDownY = -currZ-65;
        Camera.visibleUpY = -currZ+65;
        
        if (MouseInput.pressed.left) {
            Mouse.setGrabbed(true);
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (MouseInput.hold.left) {
            float diffx = MouseInput.x - pastMousex;
            float diffy = MouseInput.y - pastMousey;
            
            anglex += diffx*rotate;
            
            angley += diffy*rotate;
            
            Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
            pastMousex = Display.getWidth()/2;
            pastMousey = Display.getHeight()/2;
        }
        else if (MouseInput.released.left) {
            MouseInput.setMouseGrabbed(false);
        }
        
        if (Keybindings.hold(Keybindings.FPP_MOVE_RIGHT)) {
            posx += (float)(Math.cos(anglex+Math.PI/2))*fraction;
            posz += (float)(Math.sin(anglex+Math.PI/2))*fraction;
        }
        if (Keybindings.hold(Keybindings.FPP_MOVE_LEFT)) {
            posx += (float)(Math.cos(anglex-Math.PI/2))*fraction;
            posz += (float)(Math.sin(anglex-Math.PI/2))*fraction;
        }
        if (Keybindings.hold(Keybindings.FPP_MOVE_UP)) {
            posx += dirx*fraction;
            posy += diry*fraction;
            posz += dirz*fraction;
        }
        if (Keybindings.hold(Keybindings.FPP_MOVE_DOWN)) {
            posx -= dirx*fraction;
            posy -= diry*fraction;
            posz -= dirz*fraction;
        }
        
        if (Keybindings.hold(Keybindings.FPP_CAMERA_LEFT) || Keybindings.hold(Keybindings.FPP_CAMERA_LEFT_ALT)) {
            anglex -= 5*rotate;
        }
        if (Keybindings.hold(Keybindings.FPP_CAMERA_RIGHT) || Keybindings.hold(Keybindings.FPP_CAMERA_RIGHT_ALT)) {
            anglex += 5*rotate;
        }
        if (Keybindings.hold(Keybindings.FPP_CAMERA_UP) || Keybindings.hold(Keybindings.FPP_CAMERA_UP_ALT)) {
            angley += 5*rotate;
        }
        if (Keybindings.hold(Keybindings.FPP_CAMERA_DOWN) || Keybindings.hold(Keybindings.FPP_CAMERA_DOWN_ALT)) {
            angley -= 5*rotate;
        }
        
        if (!fixedHeight) {
            if (Keybindings.hold(Keybindings.FPP_ELEVATION_UP)) {
                posy += fraction;
            }
            else if (Keybindings.hold(Keybindings.FPP_ELEVATION_DOWN)) {
                posy -= fraction;
            }
        }
        
        if (fixedHeight) {
            if (Keybindings.hold(Keybindings.FPP_SPEED_MOD1)) {
                if (MouseInput.scrollUp) {
                    stickedHeight+=3;
                }
                else if (MouseInput.scrollDown && stickedHeight-3>1) {
                    stickedHeight-=3;
                }
            }
            else if (Keybindings.hold(Keybindings.FPP_SPEED_MOD2)) {
                if (MouseInput.scrollUp) {
                    stickedHeight+=0.3;
                }
                else if (MouseInput.scrollDown && stickedHeight-0.3f>1) {
                    stickedHeight-=0.3;
                }
            }
            
            if (posx<=4.1) {
                posx=4.1;
            }
            else if (posx>=Mapper.width*4-4.1) {
                posx=Mapper.width*4-4.1;
            }
            
            if (posz>=-4.1) {
                posz=-4.1;
            }
            else if (posz<=-(Mapper.height*4-4.1)) {
                posz=-(Mapper.height*4-4.1);
            }
            
            double xRatio = (posx%4d)/4d;
            double zRatio = (Math.abs(posz)%4d)/4d;
            
            xDivine = posx/4d;
            zDivine = posz/4d;
        
            currX = (int)xDivine;
            currZ = -(int)zDivine;
            
            double v00 = Mapper.heightmap[currX][currZ]/35f*3f;
            double v10 = Mapper.heightmap[currX+1][currZ]/35f*3f;
            double v01 = Mapper.heightmap[currX][currZ+1]/35f*3f;
            double v11 = Mapper.heightmap[currX+1][currZ+1]/35f*3f;
            
            double intX0 = v00*(1d-xRatio)+v10*xRatio;
            double intX1 = v01*(1d-xRatio)+v11*xRatio;
            double intY = intX0*(1d-zRatio)+intX1*zRatio;
            
            double height=intY;
            height+=stickedHeight;
            posy = height;
        }
        
        if (anglex>Math.PI*2) {
            anglex-=Math.PI*2;
        }
        else if (anglex<0) {
            anglex+=Math.PI*2;
        }

        if (angley>Math.PI-0.01f) {
            angley=(float)Math.PI-0.01f;
        }
        else if (angley<0.01f) {
            angley=0.01f;
        }
        
        dirx = (float)(Math.cos(anglex)*Math.sin(angley));
        dirz = (float)(Math.sin(anglex)*Math.sin(angley));
        diry = (float)-Math.cos(angley);
    }
    
    public static void set() {
        GL11.glViewport(0, 0, HouseCalc.programFrame.getWidth(), HouseCalc.programFrame.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float width = HouseCalc.programFrame.getWidth();
        float height = HouseCalc.programFrame.getHeight();
        GLU.gluPerspective(70, width/height, 0.1f, 600.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GLU.gluLookAt((float)posx, (float)posy, (float)posz, (float)posx+dirx, (float)posy+diry, (float)posz+dirz, 0, 1, 0);
        MatrixTools.multMatrix(matrix);
    }
    
    public static void reset() {
        posx=0;
        posy=2;
        posz=0;
    }
    
}
