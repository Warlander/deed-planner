package Mapper.Logic;

import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class StructureUpdater {
    
    protected void update(MouseInput mouse) {
        int scale = UpCamera.scale*4;
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/scale;
        int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/scale/tileScaler))+1;
        int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/scale));
        
        if (mouse.hold.left ^ mouse.hold.right) {
            if (tileX<2 || tileY<1 || tileX>Mapper.width*4-1 || tileY>Mapper.height*4-1) {}
            else if (Mapper.deleting && mouse.hold.left) Mapper.objects[tileX][tileY][Mapper.z]=null;
            else if (mouse.hold.left) Mapper.objects[tileX][tileY][Mapper.z]=Mapper.sData.copy();
            else if (mouse.hold.right) Mapper.objects[tileX][tileY][Mapper.z]=null;
        }
    }
                
    
}
