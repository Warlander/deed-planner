package Mapper.Logic;

import Form.HouseCalc;
import Form.LabelWindow;
import Mapper.KeyboardInput;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

public class MapUpdater {
    
    public boolean labelMode = false;
    
    private GroundUpdater groundUpdater;
    private CaveUpdater caveUpdater;
    public HeightUpdater heightUpdater;
    public WritUpdater writUpdater;
    private FloorUpdater floorUpdater;
    private WallUpdater wallUpdater;
    public RoofUpdater roofUpdater;
    private StructureUpdater structureUpdater;
    
    public MapUpdater() {
        groundUpdater = new GroundUpdater();
        caveUpdater = new CaveUpdater();
        heightUpdater = new HeightUpdater();
        writUpdater = new WritUpdater();
        floorUpdater = new FloorUpdater();
        wallUpdater = new WallUpdater();
        roofUpdater = new RoofUpdater();
        structureUpdater = new StructureUpdater();
    }
    
    public void update(KeyboardInput keyboard, MouseInput mouse) {
        if (!labelMode) {
            switch (Mapper.currType) {
                case ground:
                    if (Mapper.z>=0) groundUpdater.update(mouse);
                    else caveUpdater.update(mouse);
                    break;
                case elevation:
                    if (Mapper.z>=0) heightUpdater.update(keyboard, mouse);
                    break;
                case writ:
                    if (Mapper.z>=0) writUpdater.update(mouse);
                    break;
                case floor:
                    if (Mapper.z>=0) floorUpdater.update(mouse);
                    break;
                case wall:
                    if (Mapper.z>=0) wallUpdater.update(mouse);
                    break;
                case roof:
                    if (Mapper.z>=0) roofUpdater.update(mouse);
                    break;
                case structure:
                    if (Mapper.z>=0) structureUpdater.update(mouse);
            }
        }
        
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)UpCamera.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)UpCamera.scale);

        int tileX = (int) (((double)mouse.x+(double)UpCamera.y*realSize)/((double)Display.getWidth()/(double)UpCamera.scale/tileScaler))+1;
        int tileY = (int) (((double)mouse.y+(double)UpCamera.x*realSize)/((double)Display.getHeight()/(double)UpCamera.scale));
        
        if (tileX<0 || tileY<0 || tileX>Mapper.width || tileY>Mapper.height) {
            return;
        }
        
        if (labelMode && mouse.pressed.left) {
            LabelWindow.x = tileX;
            LabelWindow.y = tileY;
            LabelWindow.main(null);
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
