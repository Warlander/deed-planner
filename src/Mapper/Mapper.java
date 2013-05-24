package Mapper;

import Form.HouseCalc;
import Form.LoadWindow;
import Form.StatusBar;
import Lib.Files.Properties;
import Lib.Graphics.GLInit;
import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.DataLoader;
import Lib.Object.Rotation;
import Lib.Object.Structure;
import Lib.Object.Type;
import Lib.Object.Writ;
import Lib.Utils.Screenshot;
import Mapper.Data.D;
import Mapper.Graphics.MiscRenderer;
import Mapper.Logic.MapUpdater;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Mapper {
    
    private int tickDelay=0;
    public static double tick = 0;
    private boolean tickUp = true;
    
    private boolean reset=false;
    
    public static Ground gData=null;
    public static Writ wData=null;
    public static String eAction=null;
    public static Ground cData=null;
    public static Structure sData=null;
    public static Data data=null;
    public static boolean deleting=false;
    public static Type currType=null;
    
    private boolean resizeRequested = false;
    public static boolean fpsView = false;
    
    private MouseInput mouse;
    private KeyboardInput keyboard;
    private FPPCamera fpscamera;
    private UpCamera upcamera;
    public static MapUpdater updater;
    private MiscRenderer miscRenderer;
    
    public static int width = 25;
    public static int height = 25;
    
    public static Ground[][] caveGround=new Ground[25][25];
    public static Ground[][] ground=new Ground[25][25];
    public static float[][] heightmap = new float[25+1][25+1];
    public static float minElevation = 0;
    public static float maxElevation = 0;
    public static float diffElevation = 0;
    public static Data[][][] tiles=new Data[25][25][15];
    public static Structure[][][] objects = new Structure[100][100][15];
    public static Data[][][] bordersx=new Data[25][25][15];
    public static Data[][][] bordersy=new Data[25][25][15];
    
    public static int z = 0;
    
    public Mapper() {}
    
    public void run() {
        Thread engine = new Thread() {
            public void run() {
                if (Properties.getProperty("antialiasing")!=null) {
                    GLInit.initDisplay((int)((float)Properties.getProperty("antialiasing")));
                }
                else {
                    GLInit.initDisplay(16);
                }
                GLInit.initOpenGL();
                System.out.println("Initializing mouse input");
                mouse = new MouseInput();
                System.out.println("Done!");
                System.out.println("Initializing keyboard input");
                keyboard = new KeyboardInput();
                System.out.println("Done!");
                System.out.println("Initializing 3d and 2d view camera");
                fpscamera = new FPPCamera();
                upcamera = new UpCamera();
                System.out.println("Done!");
                System.out.println("Initializing program logic engine");
                updater = new MapUpdater();
                System.out.println("Done!");
                System.out.println("Initializing program rendering engine");
                miscRenderer = new MiscRenderer();
                System.out.println("Done!");
                try {
                    System.out.println("Loading data from \"Objects.txt\"");
                    DataLoader.load();
                    System.out.println("Done!");
                    System.out.println("Preparing initial map");
                    for (int i=0; i<width; i++) {
                        for (int i2=0; i2<height; i2++) {
                            ground[i][i2] = D.grounds.get(0).copy(i, i2);
                            caveGround[i][i2] = D.caveGrounds.get(0).copy(i, i2);
                        }
                    }
                    System.out.println("Done!");
                } catch (IOException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Mapper core initialized");
                System.out.println("Initializing program loop");
                loop();
            }
        };
        engine.start();
    }
    
    long previousNano = System.nanoTime();
    int fps = 0;
    long fpsToReset = 1000000000;
    
    private void loop() {
        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            resize();
            mouse.update();
            keyboard.update();
            logic();
            draw();
            keyboard.reset();
            Display.update();
            HouseCalc.statusBar.display();
            Display.sync(30);
            fpsToReset-=System.nanoTime()-previousNano;
            previousNano = System.nanoTime();
            fps++;
            if (fpsToReset<0) {
                fpsToReset = 1000000000-fpsToReset;
                System.out.println("FPS: "+(fps-1));
                fps=0;
            }
            StatusBar.tipLabel1.tick();
            
            if (reset) {
                try {
                    Display.setParent(HouseCalc.programFrame);
                } catch (LWJGLException ex) {
                    Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
                }
                reset=false;
            }
        }
    }
    
    private void logic() {
        if (HouseCalc.mapStr!=null) {
            try {
                BufferedReader read = new BufferedReader(new FileReader(HouseCalc.mapStr));
                LoadWindow.loadManager(read.readLine());
                HouseCalc.mapStr=null;
            } catch (IOException ex) {
                Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        switch (HouseCalc.paintMode) {
            case type:
                tick=1;
                break;
            case paint:
                tick=0;
                break;
            case cycle:
                if (tickDelay>0) {
                    tickDelay--;
                }
                else if (tickUp) {
                    tick+=0.04;
                    if (tick>=1) {
                        tick=1;
                        tickDelay=30;
                        tickUp=false;
                    }
                }
                else {
                    tick-=0.04;
                    if (tick<=0) {
                        tick=0;
                        tickDelay=30;
                        tickUp=true;
                    }
                }
                break;
        }
        
        if (fpsView) {
            fpscamera.update(keyboard, mouse);
        }
        else {
            upcamera.update(keyboard, mouse);
            updater.update(keyboard, mouse);
        }
    }
    
    private void draw() {
        GL11.glLoadIdentity();
        if (fpsView) {
            fpscamera.set();
        }
        else {
            upcamera.set();
        }
        
        miscRenderer.update();
        
        GL11.glPushMatrix();
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glRotatef(180, 0, 0, 1);
            for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
                for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                    for (int i3=0; i3<15; i3++) {
                        if (i>=0 && i<width && i2>=0 && i2<height) {
                            if (bordersy[i][i2][i3]!=null) {
                                bordersy[i][i2][i3].render(i2, i, i3, Rotation.vertical);
                            }
                        }
                    }
                }
            }
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glRotatef(90, 0, 0, 1);
            for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
                for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                    for (int i3=0; i3<15; i3++) {
                        if (i>=0 && i<width && i2>=0 && i2<height) {
                            if (tiles[i][i2][i3]!=null && tiles[i][i2][i3].object!=null) {
                                tiles[i][i2][i3].render(-i, i2, i3, null);
                            }
                            if (bordersx[i][i2][i3]!=null) {
                                bordersx[i][i2][i3].render(-i, i2, i3, Rotation.horizontal);
                            }
                        }
                        for (int i4=0; i4<4; i4++) {
                            for (int i5=0; i5<4; i5++) {
                                if (i*4+i4>=0 && i*4+i4<width*4 && i2*4+i5>=0 && i2*4+i5<height*4 && objects[i*4+i4][i2*4+i5][i3]!=null) {
                                    objects[i*4+i4][i2*4+i5][i3].render(-(i*4+i4), i2*4+i5, i3);
                                }
                            }
                        }
                    }
                }
            }
        GL11.glPopMatrix();
        if (keyboard.pressed[Keyboard.KEY_F11]) {
            Screenshot.takeScreenshot();
        }
    }
    
    public void resizeRequest() {
        resizeRequested=true;
    }
    
    private void resize() {
        if (resizeRequested) {
            GLInit.refit();
            resizeRequested=false;
        }
    }
    
}
