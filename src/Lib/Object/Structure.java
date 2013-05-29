package Lib.Object;

import Form.HouseCalc;
import Lib.Graphics.Tex;
import Mapper.Mapper;
import org.lwjgl.opengl.GL11;

public class Structure {
    
    private static final double floor1 = 0.4;
    private static final double floor2 = 0.75;
    
    public String name;
    public String shortName;
    public Tex texture;
    public ObjectData object;
    public Materials materials;
    
    public double rPaint=1;
    public double gPaint=1;
    public double bPaint=1;
    
    public int rotation=0;
    
    public Structure(String name, String shortName, Tex texture, ObjectData object) {
        this.name = name;
        this.shortName = shortName;
        this.texture = texture;
        this.object = object;
    }
    
    public Structure copy() {
        Structure data = new Structure(name, shortName, texture, object);
        data.materials=materials;
        data.rPaint = HouseCalc.r;
        data.gPaint = HouseCalc.g;
        data.bPaint = HouseCalc.b;
        return data;
    }
    
    public void render(int sx, int sy, int sz) {
        if (Mapper.fpsView || ((sz-Mapper.z)<=0 && (sz-Mapper.z)>-3)) {
            int mapX = (int)(sx/4f);
            int mapY = (int)(sy/4f);
            float xRatio = (sx%4f)/4f;
            float zRatio = (sy%4f)/4f;
            float v00 = Mapper.heightmap[mapX][mapY]/35f*3f;
            float v10 = Mapper.heightmap[mapX+1][mapY]/35f*3f;
            float v01 = Mapper.heightmap[mapX][mapY+1]/35f*3f;
            float v11 = Mapper.heightmap[mapX+1][mapY+1]/35f*3f;

            float intX0 = v00*(1-xRatio)+v10*xRatio;
            float intX1 = v01*(1-xRatio)+v11*xRatio;
            float height = intX0*(1-zRatio)+intX1*zRatio;
            
            GL11.glPushMatrix();
                GL11.glTranslatef(sy, sz*3+height, sx);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glRotatef(90, 0, 0, 1);
                GL11.glRotatef(rotation, 0, 0, 1);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.ID);
                if (!Mapper.fpsView) {
                    double showR = 0;
                    double showG = 0;
                    double showB = 0;
                    switch (sz-Mapper.z) {
                        case 0:
                            showR = rPaint;
                            showG = gPaint;
                            showB = bPaint;
                            break;
                        case -1:
                            showR = rPaint-(floor1*rPaint);
                            showG = gPaint-(floor1*gPaint);
                            showB = bPaint-(floor1*bPaint);
                            break;
                        case -2:
                            showR = rPaint-(floor2*rPaint);
                            showG = gPaint-(floor2*gPaint);
                            showB = bPaint-(floor2*bPaint);
                            break;
                    }
                    GL11.glColor3d(showR, showG, showB);
                }
                else {
                    GL11.glColor3d(rPaint, gPaint, bPaint);
                }

                GL11.glBegin(GL11.GL_TRIANGLES);
                    for (int i=0; i<object.size; i++) {
                        GL11.glTexCoord2f(object.texU[object.coord[i]], 1-object.texV[object.coord[i]]);
                        GL11.glVertex3f(object.x[object.vert[i]], object.y[object.vert[i]], object.z[object.vert[i]]);
                    }
                GL11.glEnd();
            GL11.glPopMatrix();
        }
    }
    
    public String toString() {
        return name;
    }
    
}
