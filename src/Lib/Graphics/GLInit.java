package Lib.Graphics;

import Form.HouseCalc;
import Lib.Files.Properties;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class GLInit {
    
    protected GLInit() {}
    
    public static void initDisplay(int antialias) {
        try {
            System.out.println("Trying to detect OpenGL capabilities");
            HouseCalc.programFrame.setIgnoreRepaint(true);
            HouseCalc.programFrame.setFocusable(true);
            
            Display.setParent(HouseCalc.programFrame);
            Display.setVSyncEnabled(true);
            Display.setResizable(true);
            
            Display.create();
            
            PixelFormat format;
            if (GLContext.getCapabilities().GL_ARB_multisample && antialias!=0) {
                System.out.println("Antialiasting is supported!");
                Display.destroy();
                format = new PixelFormat().withSamples(antialias);
                Display.create(format);
                System.out.println("Trying to create display");
            }
            else {
                System.out.println("Antialiasting is NOT supported!");
                Display.destroy();
                Display.create();
                System.out.println("Trying to create display");
            }
            System.out.println("Display created");
        } catch (LWJGLException ex) {
            int anti;
            if (antialias!=2) {
                anti=antialias/2;
            }
            else {
                anti=0;
            }
            System.out.println("Failed to create display!");
            System.err.println("Failed to create display!");
            if (anti==0) {
                System.err.println("DISPLAY CREATION FAILED");
                System.exit(-1);
            }
            System.out.println("Another try with lower antialiasing: "+anti+" samples");
            System.err.println("Another try with lower antyaliasing: "+anti+" samples");
            Properties.setProperty("antialiasing", anti);
            Properties.saveProperties();
            initDisplay(anti);
        }
    }
    
    public static void initOpenGL() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        
        GL11.glViewport(0, 0, HouseCalc.programFrame.getWidth(), HouseCalc.programFrame.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float width = HouseCalc.programFrame.getWidth();
        float height = HouseCalc.programFrame.getHeight();
        GLU.gluPerspective(70, width/height, 0.1f, 200.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public static void refit() {
        GL11.glViewport(0, 0, HouseCalc.programFrame.getWidth(), HouseCalc.programFrame.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        float width = HouseCalc.programFrame.getWidth();
        float height = HouseCalc.programFrame.getHeight();
        GLU.gluPerspective(70, width/height, 0.1f, 200.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
}
