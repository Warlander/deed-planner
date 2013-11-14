package Mapper.Logic;

import Form.DrawMode;
import Form.HouseCalc;
import Lib.Files.Properties;
import Lib.Graphics.Ground;
import Mapper.Data.D;
import Mapper.Mapper;
import Mapper.Input.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public final class CaveUpdater {
    
    static void update() {
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
            float tileSize = (float)Display.getHeight()/Properties.scale/4;
            int tileX = (int) ((MouseInput.x+UpCamera.x*tileSize)/((float)Display.getWidth()/Properties.scale/tileScaler));
            int tileY = (int) ((MouseInput.y+UpCamera.y*tileSize)/((float)Display.getHeight()/Properties.scale));
            
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
    }
    
    private static void groundChange(int realX, int realY) {
        Ground g;
        
        if (MouseInput.hold.left) g=Mapper.cData.copy(realX, realY);
        else g=D.caveGrounds.get(0).copy(realX, realY);
        
        Mapper.caveGround[realX][realY]=g;
    }
    
    private static void pencil(int tileX, int tileY) {
        int realX;
        int realY;
        
        for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
            for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                realX = tileX+i;
                realY = tileY+i2;
                if (realX<0 || realY<0 || realX>Mapper.width-1 || realY>Mapper.height-1) {}
                else groundChange(realX, realY);
            }
        }
    }
    
    private static void fill(int tileX, int tileY) {
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        fillInfect(Mapper.caveGround[tileX][tileY], tileX, tileY);
    }
    
    private static void fillInfect(Ground ground, int tileX, int tileY) {
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        if (!Mapper.caveGround[tileX][tileY].shortName.equals(ground.shortName)) return;
        if (MouseInput.hold.left && Mapper.caveGround[tileX][tileY].shortName.equals(Mapper.cData.shortName)) return;
        if (MouseInput.hold.right && Mapper.caveGround[tileX][tileY].shortName.equals(D.caveGrounds.get(0).shortName)) return;
        
        groundChange(tileX, tileY);
        fillInfect(ground, tileX-1, tileY);
        fillInfect(ground, tileX+1, tileY);
        fillInfect(ground, tileX, tileY-1);
        fillInfect(ground, tileX, tileY+1);
    }
    
    private static void select(int tileX, int tileY) {
        Ground ground = Mapper.caveGround[tileX][tileY];
        for (int i=0; i<HouseCalc.groundsList.getModel().getSize(); i++) {
            if (((Ground)HouseCalc.groundsList.getModel().getElementAt(i)).name.equals(ground.name)) {
                HouseCalc.groundsList.setSelectedIndex(i);
                break;
            }
        }
        Mapper.cData = ground;
        HouseCalc.drawMode = DrawMode.pencil;
        HouseCalc.pencilToggle.setSelected(true);
        HouseCalc.fillToggle.setSelected(false);
        HouseCalc.selectToggle.setSelected(false);
    }
    
}
