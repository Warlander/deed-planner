package Mapper.Logic;

import Form.HouseCalc;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import java.util.Scanner;
import org.lwjgl.opengl.Display;

public class MapUpdater {
    
    private GroundUpdater groundUpdater;
    public WritUpdater writUpdater;
    private FloorUpdater floorUpdater;
    private WallUpdater wallUpdater;
    public RoofUpdater roofUpdater;
    
    public MapUpdater() {
        groundUpdater = new GroundUpdater();
        writUpdater = new WritUpdater();
        floorUpdater = new FloorUpdater();
        wallUpdater = new WallUpdater();
        roofUpdater = new RoofUpdater();
    }
    
    public void update(MouseInput mouse) {
        switch (Mapper.currType) {
            case ground:
                groundUpdater.update(mouse);
                break;
            case writ:
                writUpdater.update(mouse);
                break;
            case floor:
                floorUpdater.update(mouse);
                break;
            case wall:
                wallUpdater.update(mouse);
                break;
            case roof:
                roofUpdater.update(mouse);
                break;
        }
        
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
        int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler));
        int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/UpCamera.scale));
        
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        HouseCalc.statusBar.setGround(Mapper.ground[tileX][tileY]);
    }
    
}
