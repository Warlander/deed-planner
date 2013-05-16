package Mapper.Logic;

import Form.HouseCalc;
import Mapper.KeyboardInput;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

public class MapUpdater {
    
    private GroundUpdater groundUpdater;
    private HeightUpdater heightUpdater;
    public WritUpdater writUpdater;
    private FloorUpdater floorUpdater;
    private WallUpdater wallUpdater;
    public RoofUpdater roofUpdater;
    
    public MapUpdater() {
        groundUpdater = new GroundUpdater();
        heightUpdater = new HeightUpdater();
        writUpdater = new WritUpdater();
        floorUpdater = new FloorUpdater();
        wallUpdater = new WallUpdater();
        roofUpdater = new RoofUpdater();
    }
    
    public void update(KeyboardInput keyboard, MouseInput mouse) {
        switch (Mapper.currType) {
            case ground:
                groundUpdater.update(mouse);
                break;
            case elevation:
                heightUpdater.update(keyboard, mouse);
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
        
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)UpCamera.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)UpCamera.scale);

        int tileX = (int) (((double)mouse.x+(double)UpCamera.y*realSize)/((double)Display.getWidth()/(double)UpCamera.scale/tileScaler))+1;
        int tileY = (int) (((double)mouse.y+(double)UpCamera.x*realSize)/((double)Display.getHeight()/(double)UpCamera.scale));

        if (tileX<0 || tileY<0 || tileX>Mapper.width || tileY>Mapper.height) {
            return;
        }

        double x = mouse.x+UpCamera.y*realSize;
        double y = mouse.y+UpCamera.x*realSize;

        double xClip = x%tileSize;
        double yClip = y%tileSize;
        
        Point[] points = heightUpdater.selectedPoint(tileX, tileY, xClip, yClip, tileSize);
        
        if (points!=null) {
            HouseCalc.statusBar.setPoint(points[0]);
        }
        if (tileX<0 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        HouseCalc.statusBar.setGround(Mapper.ground[tileX-1][tileY]);
    }
    
}
