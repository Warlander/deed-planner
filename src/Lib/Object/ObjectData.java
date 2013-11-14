package Lib.Object;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

public class ObjectData {
    
    private final String modelLocation;
    private boolean initialized = false;
    
    public final int loadID;
    private int listID;
    
    ObjectData(String modelLocation) {
        this(0, modelLocation);
    }
    
    ObjectData(int loadID, String modelLocation) {
        this.modelLocation = modelLocation;
        this.loadID = loadID;
    }
    
    /**
     * Renders this model - GL11.glBegin() calls are unnecessary, but proper transformations are.
     */
    public void render() {
        if (!initialized) {
            try {
                listID = ReadObject.read(modelLocation);
            } catch (IOException ex) {
                Logger.getLogger(ObjectData.class.getName()).log(Level.SEVERE, null, ex);
            }
            initialized = true;
        }
        
        GL11.glCallList(listID);
    }
    
}
