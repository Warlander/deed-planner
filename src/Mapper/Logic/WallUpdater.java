package Mapper.Logic;

import Form.HouseCalc;
import Form.SaveWindow;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.Server;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class WallUpdater {
    
    private int lockedVal = 0;
    private boolean lockX = false;
    private boolean lockY = false;
    
    protected void update(MouseInput mouse) {
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)UpCamera.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)UpCamera.scale);

        int tileX = (int) (((double)mouse.x+(double)UpCamera.y*realSize)/((double)Display.getWidth()/(double)UpCamera.scale/tileScaler))+1;
        int tileY = (int) (((double)mouse.y+(double)UpCamera.x*realSize)/((double)Display.getHeight()/(double)UpCamera.scale));
        
        if (tileX<2 || tileY<1 || tileX>=Mapper.width || tileY>=Mapper.height-1) {
            return;
        }

        double x = mouse.x+UpCamera.y*realSize;
        double y = mouse.y+UpCamera.x*realSize;

        double xClip = x%tileSize;
        double yClip = y%tileSize;
        
        if (mouse.hold.left ^ mouse.hold.right) {
            if (yClip<(tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockX = true;
                    lockedVal = tileY;
                }
                if (!lockY) {
                    if (mouse.hold.left) horizontalBorder(tileX, tileY);
                    else if (mouse.hold.right) horizontalDelete(tileX, tileY);
                }
            }
            else if (yClip>(tileSize-tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockX = true;
                    lockedVal = tileY+1;
                }
                if (!lockY) {
                    if (mouse.hold.left) horizontalBorder(tileX, tileY+1);
                    else if (mouse.hold.right) horizontalDelete(tileX, tileY+1);
                }
            }
            else if (xClip<(tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockY = true;
                    lockedVal = tileX-1;
                }
                if (!lockX) {
                    if (mouse.hold.left) verticalBorder(tileX-1, tileY);
                    else if (mouse.hold.right) verticalDelete(tileX-1, tileY);
                }
            }
            else if (xClip>(tileSize-tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
                if (HouseCalc.lockAxis && !lockX && !lockY) {
                    lockY = true;
                    lockedVal = tileX;
                }
                if (!lockX) {
                    if (mouse.hold.left) verticalBorder(tileX, tileY);
                    else if (mouse.hold.right) verticalDelete(tileX, tileY);
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
    
    private void verticalSelect(int tileX, int tileY) {
        HouseCalc.statusBar.setData(Mapper.bordersy[tileX][tileY][Mapper.z]);
    }
    
    private void verticalBorder(int tileX, int tileY) {
        if (lockY) {
            tileX = lockedVal;
        }
        if (!Mapper.deleting) {
            Mapper.bordersy[tileX][tileY][Mapper.z] = Mapper.data.copy();
        }
        else {
            Mapper.bordersy[tileX][tileY][Mapper.z] = null;
        }
        
        if (Server.running) {
            SaveWindow.saveBorderY(Server.builder, tileX, tileY, Mapper.z);
        }
    }
    
    private void verticalDelete(int tileX, int tileY) {
        if (lockY) {
            tileX = lockedVal;
        }
        Mapper.bordersy[tileX][tileY][Mapper.z] = null;
        
        if (Server.running) {
            SaveWindow.saveBorderY(Server.builder, tileX, tileY, Mapper.z);
        }
    }
    
    private void horizontalSelect(int tileX, int tileY) {
        HouseCalc.statusBar.setData(Mapper.bordersx[tileX][tileY][Mapper.z]);
    }
    
    private void horizontalBorder(int tileX, int tileY) {
        if (lockX) {
            tileY = lockedVal;
        }
        if (!Mapper.deleting) {
            Mapper.bordersx[tileX][tileY][Mapper.z] = Mapper.data.copy();
        }
        else {
            Mapper.bordersx[tileX][tileY][Mapper.z] = null;
        }
        
        if (Server.running) {
            SaveWindow.saveBorderX(Server.builder, tileX, tileY, Mapper.z);
        }
    }
    
    private void horizontalDelete(int tileX, int tileY) {
        if (lockX) {
            tileY = lockedVal;
        }
        Mapper.bordersx[tileX][tileY][Mapper.z] = null;
        
        if (Server.running) {
            SaveWindow.saveBorderX(Server.builder, tileX, tileY, Mapper.z);
        }
    }
    
}
