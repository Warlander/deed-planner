package Mapper.Logic;

import Form.DrawMode;
import Form.HouseCalc;
import Lib.Graphics.Ground;
import Lib.Object.Writ;
import Mapper.Data.D;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class GroundUpdater {
    
    protected void update(MouseInput mouse) {
        if (mouse.hold.left ^ mouse.hold.right) {
            float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
            float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
            int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler));
            int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/UpCamera.scale));
            
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
    }
    
    private void groundChange(MouseInput mouse, int realX, int realY) {
        Ground g;
        
        if (mouse.hold.left) g=Mapper.gData.copy(realX, realY);
        else g=D.grounds.get(0).copy(realX, realY);
        
        if (Mapper.ground[realX][realY].writ==null) {
            Mapper.ground[realX][realY]=g;
        }
        else {
            Writ w = Mapper.ground[realX][realY].writ;
            w.tiles.remove(Mapper.ground[realX][realY]);
            g.writ = w;
            w.tiles.add(g);
            Mapper.ground[realX][realY] = g;
        }
    }
    
    private void pencil(MouseInput mouse, int tileX, int tileY) {
        int realX;
        int realY;
        
        for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
            for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                realX = tileX+i;
                realY = tileY+i2;
                if (realX<0 || realY<0 || realX>Mapper.width-1 || realY>Mapper.height-1) {}
                else groundChange(mouse, realX, realY);
            }
        }
    }
    
    private void fill(MouseInput mouse, int tileX, int tileY) {
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        fillInfect(mouse, Mapper.ground[tileX][tileY], tileX, tileY);
    }
    
    private void fillInfect(MouseInput mouse, Ground ground, int tileX, int tileY) {
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        if (!Mapper.ground[tileX][tileY].shortName.equals(ground.shortName)) return;
        if (mouse.hold.left && Mapper.ground[tileX][tileY].shortName.equals(Mapper.gData.shortName)) return;
        if (mouse.hold.right && Mapper.ground[tileX][tileY].shortName.equals(D.grounds.get(0).shortName)) return;
        
        groundChange(mouse, tileX, tileY);
        fillInfect(mouse, ground, tileX-1, tileY);
        fillInfect(mouse, ground, tileX+1, tileY);
        fillInfect(mouse, ground, tileX, tileY-1);
        fillInfect(mouse, ground, tileX, tileY+1);
    }
    
    private void select(int tileX, int tileY) {
        Ground ground = Mapper.ground[tileX][tileY];
        for (int i=0; i<HouseCalc.groundsList.getModel().getSize(); i++) {
            if (((Ground)HouseCalc.groundsList.getModel().getElementAt(i)).name.equals(ground.name)) {
                HouseCalc.groundsList.setSelectedIndex(i);
                break;
            }
        }
        Mapper.gData = ground;
        HouseCalc.drawMode = DrawMode.pencil;
        HouseCalc.pencilToggle.setSelected(true);
        HouseCalc.fillToggle.setSelected(false);
        HouseCalc.selectToggle.setSelected(false);
    }
    
}
