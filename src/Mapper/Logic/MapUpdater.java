package Mapper.Logic;

import Form.HouseCalc;
import Form.LabelWindow;
import Mapper.Mapper;
import Mapper.Input.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

public final class MapUpdater {
    
    public static boolean labelMode = false;
    
    private MapUpdater() {}
    
    public static void update() {
        if (!labelMode) {
            switch (Mapper.currType) {
                case ground:
                    if (Mapper.getFloor()>=0) GroundUpdater.update();
                    else CaveUpdater.update();
                    break;
                case elevation:
                    if (Mapper.getFloor()>=0) HeightUpdater.update();
                    break;
                case writ:
                    if (Mapper.getFloor()>=0) WritUpdater.update();
                    break;
                case floor:
                    if (Mapper.getFloor()>=0) FloorUpdater.update();
                    break;
                case wall:
                    if (Mapper.getFloor()>=0) WallUpdater.update();
                    break;
                case roof:
                    if (Mapper.getFloor()>=0) RoofUpdater.update();
                    break;
                case structure:
                    if (Mapper.getFloor()>=0) StructureUpdater.update();
            }
        }
        
        final double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        final double realSize = (double)Display.getHeight()/(double)UpCamera.scale/4d;
        final double tileSize = ((double)Display.getHeight()/(double)UpCamera.scale);

        final int tileX = (int) (((double)MouseInput.x+(double)UpCamera.x*realSize)/((double)Display.getWidth()/(double)UpCamera.scale/tileScaler))+1;
        final int tileY = (int) (((double)MouseInput.y+(double)UpCamera.y*realSize)/((double)Display.getHeight()/(double)UpCamera.scale));
        
        if (tileX<0 || tileY<0 || tileX>Mapper.width || tileY>Mapper.height) {
            return;
        }
        
        if (labelMode && MouseInput.pressed.left) {
            LabelWindow.x = tileX;
            LabelWindow.y = tileY;
            LabelWindow.main(null);
        }

        final double x = MouseInput.x+UpCamera.x*realSize;
        final double y = MouseInput.y+UpCamera.y*realSize;

        final double xClip = x%tileSize;
        final double yClip = y%tileSize;
        
        Point[] points = HeightUpdater.selectedPoint(tileX, tileY, xClip, yClip, tileSize);
        
        if (points!=null) {
            HouseCalc.statusBar.setPoint(points[0]);
        }
        if (tileX<1 || tileY<0 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {
            return;
        }
        HouseCalc.statusBar.setGround(Mapper.ground[tileX-1][tileY]);
    }
    
}
