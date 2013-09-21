package Mapper;

import Mapper.Input.MouseInput;
import Mapper.Input.KeyboardInput;
import Form.HouseCalc;
import static Form.HouseCalc.floorLabel;
import Form.LoadWindow;
import static Form.SaveWindow.saveToFile;
import Form.StatusBar;
import Lib.Files.Properties;
import Lib.Graphics.GLInit;
import Lib.Graphics.Ground;
import Lib.Entities.Data;
import Lib.Object.DataLoader;
import Lib.Entities.Label;
import Lib.Object.Rotation;
import Lib.Entities.Structure;
import Lib.Object.Type;
import Lib.Entities.Writ;
import Lib.Files.FileManager;
import Lib.Utils.Repeater;
import Lib.Utils.Screenshot;
import Mapper.Data.D;
import Mapper.Graphics.MiscRenderer;
import Mapper.Input.Keybindings;
import Mapper.Logic.MapUpdater;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public final class Mapper {
    
    private static final String slash = System.getProperty("file.separator");
    
    private static int tickDelay=0;
    public static double tick = 0;
    private static boolean tickUp = true;
    
    private static boolean reset=false;
    
    public static Ground gData=null;
    public static Writ wData=null;
    public static String eAction=null;
    public static Ground cData=null;
    public static Structure sData=null;
    public static Data data=null;
    public static boolean deleting=false;
    public static Type currType=null;
    
    private static boolean resizeRequested = false;
    public static boolean fpsView = false;
    
    public static int width = 25;
    public static int height = 25;
    
    public static Ground[][] caveGround=new Ground[25][25];
    public static Ground[][] ground=new Ground[25][25];
    public static float[][] heightmap = new float[25+1][25+1];
    public static float minElevation = 0;
    public static float maxElevation = 0;
    public static float diffElevation = 0;
    public static Data[][][] tiles=new Data[25][15][25];
    public static Structure[][][] objects = new Structure[100][15][100];
    public static Label[][] labels = new Label[25][25];
    public static Label[][] caveLabels = new Label[25][25];
    public static Data[][][] bordersx=new Data[25][15][25];
    public static Data[][][] bordersy=new Data[25][15][25];
    
    private static Repeater autosave;
    
    private static long previousNano = System.nanoTime();
    private static int fps = 0;
    private static long fpsToReset = 1000000000;
    
    private static int y = 0;
    
    private Mapper() {}
    
    public static void run() {
        Thread engine = new Thread() {
            public void run() {
                if (Properties.getProperty("antialiasing")!=null) {
                    GLInit.initDisplay((int)((float)Properties.getProperty("antialiasing")));
                }
                else {
                    GLInit.initDisplay(4);
                }
                GLInit.initOpenGL();
                System.out.println("Initializing program rendering engine");
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
                
                autosave = new Repeater(300000) {
                    
                    protected void action() {
                        saveToFile(FileManager.getFile("Saves"+slash+"Autosave"+".MAP"));
                        System.out.println("Map autosaved!");
                    }
                    
                };
                
                System.out.println("Initializing program loop");
                loop();
            }
        };
        engine.start();
    }
    
    private static void loop() {
        while (true) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            resize();
            MouseInput.update();
            KeyboardInput.update();
            logic();
            draw();
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
            autosave.update();
            
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
    
    private static void logic() {
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
            FPPCamera.update();
        }
        else {
            UpCamera.update();
            MapUpdater.update();
        }
    }
    
    private static void draw() {
        if (fpsView) {
            FPPCamera.set();
        }
        else {
            UpCamera.set();
        }
        
        MiscRenderer.update();
        
        for (int i=Camera.visibleDownX; i<Camera.visibleUpX; i++) {
            for (int i2=Camera.visibleDownY; i2<Camera.visibleUpY; i2++) {
                for (int i3=0; i3<15; i3++) {
                    if (i>=0 && i<width && i2>=0 && i2<height) {
                        if (bordersy[i][i3][i2]!=null) {
                            try {
                                bordersy[i][i3][i2].render(i, i3, i2, Rotation.vertical);
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                System.out.println(i+" "+i3+" "+i2);
                            }
                            
                        }
                    }
                }
            }
        }
        
        for (int i=Camera.visibleDownX; i<Camera.visibleUpX; i++) {
            for (int i2=Camera.visibleDownY; i2<Camera.visibleUpY; i2++) {
                for (int i3=0; i3<15; i3++) {
                    if (i>=0 && i<width && i2>=0 && i2<height) {
                        if (tiles[i][i3][i2]!=null && tiles[i][i3][i2].object!=null) {
                            tiles[i][i3][i2].render(i, i3, i2, null);
                        }
                        if (bordersx[i][i3][i2]!=null) {
                            bordersx[i][i3][i2].render(i, i3, i2, Rotation.horizontal);
                        }
                    }
                }
            }
        }
        
        for (int i=Camera.visibleDownX; i<Camera.visibleUpX; i++) {
            for (int i2=Camera.visibleDownY; i2<Camera.visibleUpY; i2++) {
                if (i>=0 && i<width && i2>=0 && i2<height) {
                    if (y>=0) {
                        if (labels[i][i2]!=null) {
                            labels[i][i2].render(i, i2);
                        }
                    }
                    else {
                        if (caveLabels[i][i2]!=null) {
                            caveLabels[i][i2].render(i, i2);
                        }
                    }
                }
                for (int i3=0; i3<15; i3++) {
                    for (int i4=0; i4<4; i4++) {
                        for (int i5=0; i5<4; i5++) {
                            if (i*4+i4>=0 && i*4+i4<width*4 && i2*4+i5>=0 && i2*4+i5<height*4 && objects[i*4+i4][i3][i2*4+i5]!=null) {
                                objects[i*4+i4][i3][i2*4+i5].render(i*4+i4, i3, i2*4+i5);
                            }
                        }
                    }
                }
            }
        }
        
        if (Keybindings.pressed(Keybindings.OTHER_SCREENSHOT)) {
            Screenshot.takeScreenshot();
        }
    }
    
    public static void resizeRequest() {
        resizeRequested=true;
    }
    
    private static void resize() {
        if (resizeRequested) {
            GLInit.refit();
            resizeRequested=false;
        }
    }
    
    public static int getFloor() {
        return Mapper.y;
    }
    
    public static void floorUp() {
        if (Mapper.y<15-1) {
            Mapper.y++;
        }
        if (Mapper.y==0) {
            HouseCalc.groundsList.setModel(DataLoader.grounds);
            HouseCalc.groundsList.setSelectedIndex(0);
        }
        floorLabel.setText("Floor "+(Mapper.y+1));
    }
    
    public static void floorDown() {
        if (Mapper.y>=0) {
            Mapper.y--;
        }
        if (Mapper.y==-1) {
            HouseCalc.groundsList.setModel(DataLoader.caveGrounds);
            HouseCalc.groundsList.setSelectedIndex(0);
        }
        floorLabel.setText("Floor "+(Mapper.y+1));
    }
    
}
