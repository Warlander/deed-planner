package Lib.Graphics;

import org.lwjgl.opengl.GL11;

public class Tex {
    
    private static int currentID = 0;
    
    public int loadID;
    private int ID;
    
    public Tex(int ID) {
        this.ID = ID;
    }
    
    public void use() {
        if (currentID!=ID) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
            currentID = ID;
        }
    }
}
