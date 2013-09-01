package Lib.Object;

import org.lwjgl.opengl.GL11;

public class ObjectData {
    
    public final int loadID;
    private final int listID;
    
    ObjectData(int listID) {
        this(0, listID);
    }
    
    ObjectData(int loadID, int listID) {
        this.loadID = loadID;
        this.listID = listID;
    }
    
    /**
     * Renders this model - GL11.glBegin() calls are unnecessary, but proper transformations are.
     */
    public void render() {
        GL11.glCallList(listID);
    }
    
}
