package Mapper.Logic;

import Lib.Object.Structure;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class StructureUpdater {
    
    private Structure currStruct;
    private float xPos;
    private float yPos;
    
    protected void update(MouseInput mouse) {
        int scale = UpCamera.scale*4;
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/scale;
        int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/scale/tileScaler))+1;
        int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/scale));
        
        if (mouse.pressed.left ^ mouse.pressed.right) {
            if (tileX<2 || tileY<1 || tileX>Mapper.width*4-1 || tileY>Mapper.height*4-1) {}
            else if (Mapper.deleting && mouse.pressed.left) Mapper.objects[tileX][tileY][Mapper.z]=null;
            else if (mouse.pressed.left) {
                currStruct = Mapper.sData.copy();
                Mapper.objects[tileX][tileY][Mapper.z]=currStruct;
                xPos = mouse.x;
                yPos = mouse.y;
            }
            else if (mouse.pressed.right) Mapper.objects[tileX][tileY][Mapper.z]=null;
        }
        else if (mouse.released.left ^ mouse.released.right) {
            currStruct = null;
        }
        else if (mouse.hold.left ^ mouse.hold.right) {
            if (currStruct!=null) {
                float deltaY = mouse.y - yPos;
                float deltaX = mouse.x - xPos;
                currStruct.rotation = -(int)((Math.atan2(deltaY, deltaX)+Math.PI/2)*180/Math.PI);
            }
        }
    }
                
    
}
