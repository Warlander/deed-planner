package Lib.Graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

public class Tex {
    
    private static int currentID = 0;
    
    private String texLocation;
    private boolean initialized = false;
    
    public int loadID;
    private int ID;
    
    public Tex(String texLocation) {
        this.texLocation = texLocation;
    }
    
    public void load() {
        if (!initialized) {
            try {
                ID = TextureLoader.loadPNGTexture(texLocation);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tex.class.getName()).log(Level.SEVERE, null, ex);
            }
            initialized = true;
        }
    }
    
    public void use() {
        load();
        
        if (currentID!=ID) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
            currentID = ID;
        }
    }
}
