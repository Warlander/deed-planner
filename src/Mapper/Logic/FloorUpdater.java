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
    
    protected void update(MouseInput mouse) {
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
        int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler))+1;
        int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/UpCamera.scale));
        
        if (mouse.hold.left ^ mouse.hold.right) {
            switch (HouseCalc.drawMode) {
                case pencil:
                    pencil(mouse, tileX, tileY);
                    break;
                case fill:
                    fill(mouse, tileX, tileY);
                    break;
                case select:
                    select(tileX, tileY);
                    break;
            }
        }
        
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        HouseCalc.statusBar.setData(Mapper.tiles[tileX][tileY][Mapper.z]);
    }
    
    private void pencil(MouseInput mouse, int tileX, int tileY) {
        int realX;
        int realY;
        
        for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
            for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                realX = tileX+i;
                realY = tileY+i2;
                if (realX<2 || realY<1 || realX>Mapper.width-1 || tileY>Mapper.height-1) {}
                else if (Mapper.ground[realX-1][realY].writ==null) {}
                else if (Mapper.tiles[tileX][tileY][Mapper.z]!=null && Mapper.tiles[tileX][tileY][Mapper.z].type!=Type.floor) {}
                else if (Mapper.deleting && mouse.hold.left) Mapper.tiles[tileX][tileY][Mapper.z]=null;
                else if (mouse.hold.left) Mapper.tiles[realX][realY][Mapper.z]=Mapper.data.copy();
                else if (mouse.hold.right) Mapper.tiles[realX][realY][Mapper.z]=null;
            }
        }
    }
    
    private void fill(MouseInput mouse, int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        fillInfect(mouse, Mapper.tiles[tileX][tileY][Mapper.z], tileX, tileY);
    }
    
    private void fillInfect(MouseInput mouse, Data data, int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        if (Mapper.ground[tileX-1][tileY].writ==null) {
            return;
        }
        if (Mapper.tiles[tileX][tileY][Mapper.z]==null && (Mapper.deleting || mouse.hold.right)) {
            return;
        }
        
        if (Mapper.tiles[tileX][tileY][Mapper.z]!=null && !Mapper.deleting && data!=null && !Mapper.tiles[tileX][tileY][Mapper.z].shortName.equals(data.shortName)) return;
        if (Mapper.tiles[tileX][tileY][Mapper.z]!=null && !Mapper.deleting && mouse.hold.left && Mapper.tiles[tileX][tileY][Mapper.z].shortName.equals(Mapper.data.shortName)) return;
        if (Mapper.tiles[tileX][tileY][Mapper.z]!=null && !Mapper.deleting && data==null && mouse.hold.left) return;
        if (Mapper.tiles[tileX][tileY][Mapper.z]==null && !Mapper.deleting && data!=null && mouse.hold.left) return;
        if (Mapper.tiles[tileX][tileY][Mapper.z]==null && !Mapper.deleting && mouse.hold.right) return;
        
        if (mouse.hold.left && !Mapper.deleting) {
            Mapper.tiles[tileX][tileY][Mapper.z] = Mapper.data.copy();
        }
        else {
            Mapper.tiles[tileX][tileY][Mapper.z] = null;
        }
        fillInfect(mouse, data, tileX-1, tileY);
        fillInfect(mouse, data, tileX+1, tileY);
        fillInfect(mouse, data, tileX, tileY-1);
        fillInfect(mouse, data, tileX, tileY+1);
    }
    
    private void select(int tileX, int tileY) {
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-2) {
            return;
        }
        if (Mapper.tiles[tileX][tileY][Mapper.z]==null) return;
        Data data = Mapper.tiles[tileX][tileY][Mapper.z];
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
