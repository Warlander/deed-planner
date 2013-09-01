package Mapper.Logic;

import Lib.Object.Structure;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class StructureUpdater {
    
    private static Structure currStruct;
    private static float xPos;
    private static float yPos;
    
    static void update() {
        int scale = UpCamera.scale*4;
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/scale;
        int tileX = (int) ((MouseInput.x+UpCamera.x*tileSize)/((float)Display.getWidth()/scale/tileScaler))+1;
        int tileY = (int) ((MouseInput.y+UpCamera.y*tileSize)/((float)Display.getHeight()/scale));
        
        if (MouseInput.pressed.left ^ MouseInput.pressed.right) {
            if (tileX<2 || tileY<1 || tileX>Mapper.width*4-1 || tileY>Mapper.height*4-1) {}
            else if (Mapper.deleting && MouseInput.pressed.left) Mapper.objects[tileX][Mapper.y][tileY]=null;
            else if (MouseInput.pressed.left) {
                currStruct = Mapper.sData.copy();
                Mapper.objects[tileX][Mapper.y][tileY]=currStruct;
                xPos = MouseInput.x;
                yPos = MouseInput.y;
            }
            else if (MouseInput.pressed.right) Mapper.objects[tileX][Mapper.y][tileY]=null;
        }
        else if (MouseInput.released.left ^ MouseInput.released.right) {
            currStruct = null;
        }
        else if (MouseInput.hold.left ^ MouseInput.hold.right) {
            if (currStruct!=null) {
                float deltaY = MouseInput.y - yPos;
                float deltaX = MouseInput.x - xPos;
                currStruct.rotation = -(int)((Math.atan2(deltaY, deltaX)+Math.PI/2)*180/Math.PI);
            }
        }
    }
                
    
}
