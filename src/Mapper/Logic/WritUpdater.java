package Mapper.Logic;

import Mapper.Mapper;
import Form.HouseCalc;
import Form.SaveWindow;
import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.Structure;
import Lib.Object.Writ;
import Mapper.MouseInput;
import Mapper.UpCamera;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.lwjgl.opengl.Display;

public class WritUpdater {
    
    public static final DefaultListModel model;
    private static Ground[][] ground;
    private static Structure[][][] objects;
    private static Data[][][] tiles;
    private static Data[][][] bordersx;
    private static Data[][][] bordersy;
    
    static {
        model = new DefaultListModel();
        HouseCalc.writsList.setModel(model);
    }
    
    public static void deleteWrit(Writ writ) {
        model.removeElement(writ);
        for (Ground g : writ.tiles) {
            g.writ=null;
            for (int level=0; level<15; level++) {
                Mapper.tiles[g.x+1][level][g.y] = null;
                for (int i=0; i<4; i++) {
                    for (int i2=0; i2<4; i2++) {
                        Mapper.objects[(g.x+1)*4+i][level][g.y*4+i2]=null;
                    }
                }
                if (Mapper.ground[g.x][g.y+1].writ==null) {
                    Mapper.bordersx[g.x+1][level][g.y+1] = null;
                }
                if (Mapper.ground[g.x][g.y-1].writ==null) {
                    Mapper.bordersx[g.x+1][level][g.y] = null;
                }
                if (Mapper.ground[g.x-1][g.y].writ==null) {
                    Mapper.bordersy[g.x][level][g.y] = null;
                }
                if (Mapper.ground[g.x+1][g.y].writ==null) {
                    Mapper.bordersy[g.x+1][level][g.y] = null;
                }
            }
        }
    }
    
    private static void copyMapData() {
        ground = new Ground[Mapper.width][Mapper.height];
        tiles = new Data[Mapper.width][15][Mapper.height];
        objects = new Structure[Mapper.width*4][15][Mapper.height*4];
        bordersx = new Data[Mapper.width][15][Mapper.height];
        bordersy = new Data[Mapper.width][15][Mapper.height];
        for(int i=0; i<Mapper.width; i++) {
            for(int i2=0; i2<Mapper.height; i2++) {
                ground[i][i2] = Mapper.ground[i][i2];
                for (int i3 = 0; i3<15; i3++) {
                    for (int i4=0; i4<4; i4++) {
                        for (int i5=0; i5<4; i5++) {
                            objects[i*4+i4][i3][i2*4+i5]=Mapper.objects[i*4+i4][i3][i2*4+i5];
                        }
                    }
                    tiles[i][i3][i2] = Mapper.tiles[i][i3][i2];
                    bordersx[i][i3][i2] = Mapper.bordersx[i][i3][i2];
                    bordersy[i][i3][i2] = Mapper.bordersy[i][i3][i2];
                }
            }
        }
    }
    
    private static void mergeMapData() {
        Mapper.ground = ground;
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
    }
    
    private static void deleteTempWrit(int x, int y) {
        Writ writ = (Writ)HouseCalc.writsList.getSelectedValue();
        for (Ground g : writ.tiles) {
            for (int level=0; level<15; level++) {
                tiles[g.x+1][level][g.y] = null;
                tiles[g.x+1+x][level][g.y+y] = null;
                for (int i=0; i<4; i++) {
                    for (int i2=0; i2<4; i2++) {
                        objects[(g.x+1)*4+i][level][g.y*4+i2]=null;
                        objects[(g.x+1+x)*4+i][level][(g.y+y)*4+i2]=null;
                    }
                }
                bordersx[g.x+1][level][g.y+1] = null;
                bordersx[g.x+1+x][level][g.y+1+y] = null;
                bordersx[g.x+1][level][g.y] = null;
                bordersx[g.x+1+x][level][g.y+y] = null;
                bordersy[g.x][level][g.y] = null;
                bordersy[g.x+x][level][g.y+y] = null;
                bordersy[g.x+1][level][g.y] = null;
                bordersy[g.x+1+x][level][g.y+y] = null;
            }
        }
    }
    
    public static void update() {
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
            float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
            int tileX = (int) ((MouseInput.x+UpCamera.x*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler));
            int tileY = (int) ((MouseInput.y+UpCamera.y*tileSize)/((float)Display.getHeight()/UpCamera.scale));
            
            if (tileX<1 || tileY<1 || tileX>Mapper.width-2 || tileY>Mapper.height-2) {
                return;
            }
            else if (Mapper.wData==null) {
                return;
            }
            
            switch (HouseCalc.writMode) {
                case add:
                    if (MouseInput.hold.left) {
                        checkAndAdd(tileX, tileY);
                    }
                    else {
                        checkAndDelete(tileX, tileY);
                    }
                    break;
                case delete:
                    checkAndDelete(tileX, tileY);
                    break;
            }
        }
    }
    
    private static void add(int x, int y) {
        Ground ground = Mapper.ground[x][y];
        Mapper.wData.tiles.add(ground);
        ground.writ = Mapper.wData;
    }
    
    private static void delete(int x, int y) {
        Ground ground = Mapper.ground[x][y];
        if (Mapper.wData.tiles.contains(ground)) {
            Mapper.wData.tiles.remove(ground);
            ground.writ=null;
            for (int level=0; level<15; level++) {
                Mapper.tiles[x+1][level][y] = null;
                for (int i=0; i<4; i++) {
                    for (int i2=0; i2<4; i2++) {
                        Mapper.objects[(x+1)*4+i][level][y*4+i2]=null;
                    }
                }
                if (Mapper.ground[x][y+1].writ==null) {
                    Mapper.bordersx[x+1][level][y+1] = null;
                }
                if (Mapper.ground[x][y-1].writ==null) {
                    Mapper.bordersx[x+1][level][y] = null;
                }
                if (Mapper.ground[x-1][y].writ==null) {
                    Mapper.bordersy[x][level][y] = null;
                }
                if (Mapper.ground[x+1][y].writ==null) {
                    Mapper.bordersy[x+1][level][y] = null;
                }
            }
        }
    }
    
    private static void checkAndAdd(int x, int y) {
        if (isFlat(x, y) && checkAlien(x, y)) {
            if (Mapper.wData.tiles.isEmpty()) {
                add(x, y);
                return;
            }
            if (Mapper.ground[x][y].writ!=null) {
                return;
            }
            add(x, y);
            ArrayList<Ground> tiles = new ArrayList<>();
            if (!fillInfect(tiles, Mapper.wData.tiles.get(0)) || tiles.size()!=Mapper.wData.tiles.size()) {
                delete(x, y);
            }
        }
    }
    
    public static void move(int xAxis, int yAxis) {
        if (movePossible(xAxis, yAxis)) {
            ArrayList<Ground> newGrounds = new ArrayList<>();
            copyMapData();
            deleteTempWrit(xAxis, yAxis);
            for (Ground g : Mapper.wData.tiles) {
                g.writ=null;
                newGrounds.add(ground[g.x+xAxis][g.y+yAxis]);
                for (int level=0; level<15; level++) {
                    if (Mapper.tiles[g.x+1][level][g.y]!=null) {
                        tiles[g.x+1+xAxis][level][g.y+yAxis] = Mapper.tiles[g.x+1][level][g.y].copy();
                        for (int i=0; i<4; i++) {
                            for (int i2=0; i2<4; i2++) {
                                if (Mapper.objects[(g.x+1)*4+i][level][g.y*4+i2]!=null) {
                                    Mapper.objects[(g.x+1)*4+i+xAxis][level][g.y*4+i2+yAxis]=Mapper.objects[(g.x+1)*4+i][level][g.y*4+i2].copy();
                                }
                            }
                        }
                    }
                    if (Mapper.bordersx[g.x+1][level][g.y+1]!=null) {
                        bordersx[g.x+1+xAxis][level][g.y+1+yAxis] = Mapper.bordersx[g.x+1][level][g.y+1].copy();
                    }
                    if (Mapper.bordersx[g.x+1][level][g.y]!=null) {
                        bordersx[g.x+1+xAxis][level][g.y+yAxis] = Mapper.bordersx[g.x+1][level][g.y].copy();
                    }
                    if (Mapper.bordersy[g.x][level][g.y]!=null) {
                        bordersy[g.x+xAxis][level][g.y+yAxis] = Mapper.bordersy[g.x][level][g.y].copy();
                    }
                    if (Mapper.bordersy[g.x+1][level][g.y]!=null) {
                        bordersy[g.x+1+xAxis][level][g.y+yAxis] = Mapper.bordersy[g.x+1][level][g.y].copy();
                    }
                }
            }
            
            for (Ground g : newGrounds) {
                g.writ = Mapper.wData;
            }
            
            Mapper.wData.tiles = newGrounds;
            mergeMapData();
            RoofUpdater.roofsRefit();
        }
    }
    
    private static ArrayList getDiff(ArrayList toCheck, ArrayList toRemove) {
        ArrayList remove = new ArrayList();
        for (Object g : toRemove) {
            if (!toCheck.contains(g)) {
                remove.add(g);
            }
        }
        return remove;
    }
    
    private static boolean movePossible(int xAxis, int yAxis) {
        for (Ground g : Mapper.wData.tiles) {
            if (g.x+xAxis<0 || g.x+xAxis>=Mapper.width || g.y+yAxis<0 || g.y+yAxis>=Mapper.height) {
                return false;
            }
            else if (!isFlat(g.x+xAxis, g.y+yAxis) || !checkAlien(g.x+xAxis, g.y+yAxis)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isFlat(int x, int y) {
        if (Mapper.heightmap[x][y]!=Mapper.heightmap[x][y+1]) {return false;}
        else if (Mapper.heightmap[x][y]!=Mapper.heightmap[x+1][y]) {return false;}
        else if (Mapper.heightmap[x][y]!=Mapper.heightmap[x+1][y+1]) {return false;}
        return true;
    }
    
    private static void checkAndDelete(int x, int y) {
        if (Mapper.wData.tiles.isEmpty()) {
            return;
        }
        if (Mapper.wData.tiles.size()==1) {
            if (Mapper.wData.tiles.get(0)==Mapper.ground[x][y]) {
                delete(x, y);
            }
            return;
        }
        delete(x, y);
        ArrayList<Ground> tiles = new ArrayList<>();
        fillInfect(tiles, Mapper.wData.tiles.get(0));
        if (tiles.size()!=Mapper.wData.tiles.size()) {
            add(x, y);
        }
    }
    
    private static boolean fillInfect(ArrayList<Ground> tiles, Ground g) {
        if (g.writ==Mapper.wData && !tiles.contains(g)) {
            boolean tru;
            tiles.add(g);
            tru = fillInfect(tiles, Mapper.ground[g.x-1][g.y]);
            if (!tru) {return false;}
            tru = fillInfect(tiles, Mapper.ground[g.x+1][g.y]);
            if (!tru) {return false;}
            tru = fillInfect(tiles, Mapper.ground[g.x][g.y-1]);
            if (!tru) {return false;}
            tru = fillInfect(tiles, Mapper.ground[g.x][g.y+1]);
            return tru;
        }
        else if (g.writ!=null && g.writ!=Mapper.wData) {
            return false;
        }
        return true;
    }
    
    private static boolean checkAlien(int x, int y) {
        if (Mapper.ground[x-1][y-1].writ!=null && Mapper.ground[x-1][y-1].writ!=Mapper.wData) {
            return false;
        }
        if (Mapper.ground[x+1][y-1].writ!=null && Mapper.ground[x+1][y-1].writ!=Mapper.wData) {
            return false;
        }
        if (Mapper.ground[x-1][y+1].writ!=null && Mapper.ground[x-1][y+1].writ!=Mapper.wData) {
            return false;
        }
        if (Mapper.ground[x+1][y+1].writ!=null && Mapper.ground[x+1][y+1].writ!=Mapper.wData) {
            return false;
        }
        return true;
    }
    
}
