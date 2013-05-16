package Mapper.Logic;

import Mapper.Mapper;
import Form.HouseCalc;
import Lib.Graphics.Ground;
import Lib.Object.Writ;
import Mapper.MouseInput;
import Mapper.UpCamera;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import org.lwjgl.opengl.Display;

public class WritUpdater {
    
    public DefaultListModel model;
    
    public WritUpdater() {
        model = new DefaultListModel();
        HouseCalc.writsList.setModel(model);
    }
    
    public void deleteWrit() {
        Writ writ = (Writ)HouseCalc.writsList.getSelectedValue();
        model.removeElement(writ);
        for (Ground g : writ.tiles) {
            g.writ=null;
            for (int z=0; z<15; z++) {
                Mapper.tiles[g.x+1][g.y][z] = null;
                if (Mapper.ground[g.x][g.y+1].writ==null) {
                    Mapper.bordersx[g.x+1][g.y+1][z] = null;
                }
                if (Mapper.ground[g.x][g.y-1].writ==null) {
                    Mapper.bordersx[g.x+1][g.y][z] = null;
                }
                if (Mapper.ground[g.x-1][g.y].writ==null) {
                    Mapper.bordersy[g.x][g.y][z] = null;
                }
                if (Mapper.ground[g.x+1][g.y].writ==null) {
                    Mapper.bordersy[g.x+1][g.y][z] = null;
                }
            }
        }
    }
    
    public void update(MouseInput mouse) {
        if (mouse.hold.left ^ mouse.hold.right) {
            float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
            float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
            int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler));
            int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/UpCamera.scale));
            
            if (tileX<1 || tileY<1 || tileX>Mapper.width-2 || tileY>Mapper.height-2) {
                return;
            }
            else if (Mapper.wData==null) {
                return;
            }
            
            switch (HouseCalc.writMode) {
                case add:
                    if (mouse.hold.left) {
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
    
    private void add(int x, int y) {
        Ground ground = Mapper.ground[x][y];
        Mapper.wData.tiles.add(ground);
        ground.writ = Mapper.wData;
    }
    
    private void delete(int x, int y) {
        Ground ground = Mapper.ground[x][y];
        if (Mapper.wData.tiles.contains(ground)) {
            Mapper.wData.tiles.remove(ground);
            ground.writ=null;
            for (int z=0; z<15; z++) {
                Mapper.tiles[x+1][y][z] = null;
                if (Mapper.ground[x][y+1].writ==null) {
                    Mapper.bordersx[x+1][y+1][z] = null;
                }
                if (Mapper.ground[x][y-1].writ==null) {
                    Mapper.bordersx[x+1][y][z] = null;
                }
                if (Mapper.ground[x-1][y].writ==null) {
                    Mapper.bordersy[x][y][z] = null;
                }
                if (Mapper.ground[x+1][y].writ==null) {
                    Mapper.bordersy[x+1][y][z] = null;
                }
            }
        }
    }
    
    private void checkAndAdd(int x, int y) {
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
    
    private boolean isFlat(int x, int y) {
        if (Mapper.heightmap[x][y]!=Mapper.heightmap[x][y+1]) {return false;}
        else if (Mapper.heightmap[x][y]!=Mapper.heightmap[x+1][y]) {return false;}
        else if (Mapper.heightmap[x][y]!=Mapper.heightmap[x+1][y+1]) {return false;}
        return true;
    }
    
    private void checkAndDelete(int x, int y) {
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
    
    private boolean fillInfect(ArrayList<Ground> tiles, Ground g) {
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
    
    private boolean checkAlien(int x, int y) {
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
