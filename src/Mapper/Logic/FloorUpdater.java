package Mapper.Logic;

import Form.DrawMode;
import Form.HouseCalc;
import Lib.Object.Data;
import Lib.Object.Type;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class FloorUpdater {
    
    static void update() {
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
        int tileX = (int) ((MouseInput.x+UpCamera.x*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler))+1;
        int tileY = (int) ((MouseInput.y+UpCamera.y*tileSize)/((float)Display.getHeight()/UpCamera.scale));
        
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            switch (HouseCalc.drawMode) {
                case pencil:
                    pencil(tileX, tileY);
                    break;
                case fill:
                    fill(tileX, tileY);
                    break;
                case select:
                    select(tileX, tileY);
                    break;
            }
        }
        
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        HouseCalc.statusBar.setData(Mapper.tiles[tileX][Mapper.y][tileY]);
    }
    
    private static void pencil(int tileX, int tileY) {
        int realX;
        int realY;
        
        for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
            for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                realX = tileX+i;
                realY = tileY+i2;
                if (realX<2 || realY<1 || realX>Mapper.width-1 || tileY>Mapper.height-1) {}
                else if (Mapper.ground[realX-1][realY].writ==null) {}
                else if (Mapper.tiles[tileX][Mapper.y][tileY]!=null && Mapper.tiles[tileX][Mapper.y][tileY].type!=Type.floor) {}
                else if (Mapper.deleting && MouseInput.hold.left) Mapper.tiles[tileX][Mapper.y][tileY]=null;
                else if (MouseInput.hold.left) Mapper.tiles[realX][Mapper.y][realY]=Mapper.data.copy();
                else if (MouseInput.hold.right) Mapper.tiles[realX][Mapper.y][realY]=null;
            }
        }
    }
    
    private static void fill(int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        fillInfect(Mapper.tiles[tileX][Mapper.y][tileY], tileX, tileY);
    }
    
    private static void fillInfect(Data data, int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        if (Mapper.ground[tileX-1][tileY].writ==null) {
            return;
        }
        if (Mapper.tiles[tileX][Mapper.y][tileY]==null && (Mapper.deleting || MouseInput.hold.right)) {
            return;
        }
        
        if (Mapper.tiles[tileX][Mapper.y][tileY]!=null && !Mapper.deleting && data!=null && !Mapper.tiles[tileX][Mapper.y][tileY].shortName.equals(data.shortName)) return;
        if (Mapper.tiles[tileX][Mapper.y][tileY]!=null && !Mapper.deleting && MouseInput.hold.left && Mapper.tiles[tileX][Mapper.y][tileY].shortName.equals(Mapper.data.shortName)) return;
        if (Mapper.tiles[tileX][Mapper.y][tileY]!=null && !Mapper.deleting && data==null && MouseInput.hold.left) return;
        if (Mapper.tiles[tileX][Mapper.y][tileY]==null && !Mapper.deleting && data!=null && MouseInput.hold.left) return;
        if (Mapper.tiles[tileX][Mapper.y][tileY]==null && !Mapper.deleting && MouseInput.hold.right) return;
        
        if (MouseInput.hold.left && !Mapper.deleting) {
            Mapper.tiles[tileX][Mapper.y][tileY] = Mapper.data.copy();
        }
        else {
            Mapper.tiles[tileX][Mapper.y][tileY] = null;
        }
        
        fillInfect(data, tileX-1, tileY);
        fillInfect(data, tileX+1, tileY);
        fillInfect(data, tileX, tileY-1);
        fillInfect(data, tileX, tileY+1);
    }
    
    private static void select(int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        if (Mapper.tiles[tileX][Mapper.y][tileY]==null) return;
        Data data = Mapper.tiles[tileX][Mapper.y][tileY];
        for (int i=1; i<HouseCalc.floorsList.getModel().getSize(); i++) {
            if (((Data)HouseCalc.floorsList.getModel().getElementAt(i)).name.equals(data.name)) {
                HouseCalc.floorsList.setSelectedIndex(i);
                break;
            }
        }
        Mapper.data = data;
        HouseCalc.drawMode = DrawMode.pencil;
        HouseCalc.pencilToggle.setSelected(true);
        HouseCalc.fillToggle.setSelected(false);
        HouseCalc.selectToggle.setSelected(false);
    }
    
}