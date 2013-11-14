package Mapper.Logic;

import Form.HouseCalc;
import Lib.Files.Properties;
import Mapper.Mapper;
import Mapper.Input.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public final class WallUpdater {
    
    private static int lockedVal = 0;
    private static boolean lockX = false;
    private static boolean lockY = false;
    
    static void update() {
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)Properties.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)Properties.scale);

        int tileX = (int) (((double)MouseInput.x+(double)UpCamera.x*realSize)/((double)Display.getWidth()/(double)Properties.scale/tileScaler))+1;
        int tileY = (int) (((double)MouseInput.y+(double)UpCamera.y*realSize)/((double)Display.getHeight()/(double)Properties.scale));
        
        if (tileX<2 || tileY<1 || tileX>=Mapper.width || tileY>=Mapper.height-1) {
            return;
        }

        double x = MouseInput.x+UpCamera.x*realSize;
        double y = MouseInput.y+UpCamera.y*realSize;

        double xClip = x%tileSize;
        double yClip = y%tileSize;
        
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            if (yClip<(tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockX = true;
                    lockedVal = tileY;
                }
                if (!lockY) {
                    if (MouseInput.hold.left) horizontalBorder(tileX, tileY);
                    else if (MouseInput.hold.right) horizontalDelete(tileX, tileY);
                }
            }
            else if (yClip>(tileSize-tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockX = true;
                    lockedVal = tileY+1;
                }
                if (!lockY) {
                    if (MouseInput.hold.left) horizontalBorder(tileX, tileY+1);
                    else if (MouseInput.hold.right) horizontalDelete(tileX, tileY+1);
                }
            }
            else if (xClip<(tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockY = true;
                    lockedVal = tileX-1;
                }
                if (!lockX) {
                    if (MouseInput.hold.left) verticalBorder(tileX-1, tileY);
                    else if (MouseInput.hold.right) verticalDelete(tileX-1, tileY);
                }
            }
            else if (xClip>(tileSize-tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockY = true;
                    lockedVal = tileX;
                }
                if (!lockX) {
                    if (MouseInput.hold.left) verticalBorder(tileX, tileY);
                    else if (MouseInput.hold.right) verticalDelete(tileX, tileY);
                }
            }
        }
        else {
            lockX = false;
            lockY = false;
        }
        
        if (yClip<(tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
            horizontalSelect(tileX, tileY);
        }
        else if (yClip>(tileSize-tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
            horizontalSelect(tileX, tileY+1);
        }
        else if (xClip<(tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
            verticalSelect(tileX-1, tileY);
        }
        else if (xClip>(tileSize-tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
            verticalSelect(tileX, tileY);
        }
    }
    
    private static void verticalSelect(int tileX, int tileY) {
        HouseCalc.statusBar.setData(Mapper.bordersy[tileX][Mapper.getFloor()][tileY]);
    }
    
    private static void verticalBorder(int tileX, int tileY) {
        if (lockY) {
            tileX = lockedVal;
        }
        if (!Mapper.deleting) {
            Mapper.bordersy[tileX][Mapper.getFloor()][tileY] = Mapper.data.copy();
        }
        else {
            Mapper.bordersy[tileX][Mapper.getFloor()][tileY] = null;
        }
    }
    
    private static void verticalDelete(int tileX, int tileY) {
        if (lockY) {
            tileX = lockedVal;
        }
        Mapper.bordersy[tileX][Mapper.getFloor()][tileY] = null;
    }
    
    private static void horizontalSelect(int tileX, int tileY) {
        HouseCalc.statusBar.setData(Mapper.bordersx[tileX][Mapper.getFloor()][tileY]);
    }
    
    private static void horizontalBorder(int tileX, int tileY) {
        if (lockX) {
            tileY = lockedVal;
        }
        if (!Mapper.deleting) {
            Mapper.bordersx[tileX][Mapper.getFloor()][tileY] = Mapper.data.copy();
        }
        else {
            Mapper.bordersx[tileX][Mapper.getFloor()][tileY] = null;
        }
    }
    
    private static void horizontalDelete(int tileX, int tileY) {
        if (lockX) {
            tileY = lockedVal;
        }
        Mapper.bordersx[tileX][Mapper.getFloor()][tileY] = null;
    }
    
}
