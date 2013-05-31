package Mapper.Graphics;

import Form.HouseCalc;
import Lib.Graphics.Ground;
import Lib.Object.Type;
import Mapper.Camera;
import Mapper.Mapper;
import Mapper.Data.D;
import Mapper.FPPCamera;
import static Mapper.Mapper.fpsView;
import Mapper.UpCamera;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class MiscRenderer {
    
    public void update() {
        GL11.glPushMatrix();
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glRotatef(90, 0, 0, 1);
            renderGround();
            GL11.glColor3f(1, 1, 1);
            if (!fpsView && UpCamera.showGrid) {
                if (Mapper.z>=0) {
                    renderGrid();
                }
            }
            else {
                renderSkybox();
            }
            renderWater();
        GL11.glPopMatrix();
    }
    
    private void renderGround() {
        if (!fpsView && Mapper.z<3) {
            switch (Mapper.z) {
                case -1: case 0: 
                    GL11.glColor3f(1, 1, 1);
                    break;
                case 1:
                    GL11.glColor3f(1-0.4f, 1-0.4f, 1-0.4f);
                    break;
                case 2:
                    GL11.glColor3f(1-0.75f, 1-0.75f, 1-0.75f);
                    break;
            }
        }
        else if (fpsView) {
            GL11.glColor3f(1, 1, 1);
        }
        else {
            return;
        }
        
        for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
            for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                if (Mapper.z>=0) {
                    renderGroundTile(Mapper.ground, i, i2);
                }
                else {
                    renderGroundTile(Mapper.caveGround, i, i2);
                }
            }
        }
    }
    
    public FloatBuffer floatBuffer(float a, float b, float c, float d) {
      float[] data = new float[]{a,b,c,d};
      FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
      fb.put(data);
      fb.flip();
      return fb;
    }
    
    public Vector3f SurfaceNormal(Vector3f c1, Vector3f c2, Vector3f c3) {
        Vector3f edge1 = new Vector3f(c2.x - c1.x, c2.y - c1.y, c2.z - c1.z);
        Vector3f edge2 = new Vector3f(c3.x - c1.x, c3.y - c1.y, c3.z - c1.z);
        
        Vector3f normal = Vector3f.cross(edge1, edge2, null);
        normal.normalise();
 
        return normal;
    }
    
    private float MapperAvgHeigh(float[] array) {
        return (array[0] + array[1] + array[2] + array[3]) / 4f;
    }
    
    private void renderGroundTile(Ground[][] source, int x, int y) {
        if (y>=Mapper.height || y<0 || x>=Mapper.width || x<0) {
            return;
        }
        
        if (!fpsView && Mapper.z<3) {
            switch (Mapper.z) {
                case 0: 
                    GL11.glColor4f(1, 1, 1, 1);
                    break;
                case 1:
                    GL11.glColor4f(1-0.4f, 1-0.4f, 1-0.4f, 1);
                    break;
                case 2:
                    GL11.glColor4f(1-0.75f, 1-0.75f, 1-0.75f, 1);
                    break;
            }
        }
        else if (fpsView) {
            GL11.glColor4f(1, 1, 1, 1);
        }
        else {
            return;
        }
        
        // setup light
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);  
        GL11.glLightModeli(GL11.GL_LIGHT_MODEL_LOCAL_VIEWER, GL11.GL_TRUE);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
        // distant point light with static attenuation, at the position of background "sun"
        // GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION, floatBuffer(0,25000000,50000000,1));
        // distant point light with static attenuation, at position directly above
        GL11.glLight(GL11.GL_LIGHT0,GL11.GL_POSITION, floatBuffer(0,0,50000000,1));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, source[x][y].tex.ID);
        
        // create tile corner vertices
        Vector3f TL = new Vector3f( (float)(x*4), (float)(-y*4-4), -Mapper.heightmap[x][y+1]/(35f/3f) );
        Vector3f TR = new Vector3f( (float)(x*4+4), (float)(-y*4-4), -Mapper.heightmap[x+1][y+1]/(35f/3f) );
        Vector3f BR = new Vector3f( (float)(x*4+4), (float)(-y*4), -Mapper.heightmap[x+1][y]/(35f/3f) );
        Vector3f BL = new Vector3f( (float)(x*4), (float)(-y*4), -Mapper.heightmap[x][y]/(35f/3f) );
        // create center vertice
        float[] heighVals = {
            -Mapper.heightmap[x][y+1]/(35f/3f),
            -Mapper.heightmap[x+1][y+1]/(35f/3f),
            -Mapper.heightmap[x+1][y]/(35f/3f),
            -Mapper.heightmap[x][y]/(35f/3f)
        };
        Vector3f C = new Vector3f( (float)(x*4+2), (float)-y*4-2, MapperAvgHeigh(heighVals));
        // create normals
        Vector3f nT = SurfaceNormal(TL, TR, C);
        Vector3f nR = SurfaceNormal(TR, BR, C);
        Vector3f nL = SurfaceNormal(TL,C, BL);
        Vector3f nB = SurfaceNormal(C, BR, BL);
        
        GL11.glBegin(GL11.GL_TRIANGLES);
            // render top triangle
            GL11.glNormal3f(nT.x, nT.y, nT.z);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(TL.x, TL.y, TL.z);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(TR.x, TR.y, TR.z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3f(C.x, C.y, C.z);
            // render right triangle
            GL11.glNormal3f(nR.x, nR.y, nR.z);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(TR.x, TR.y, TR.z);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(BR.x, BR.y, BR.z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3f(C.x, C.y, C.z);
            // render left triangle
            GL11.glNormal3f(nL.x, nL.y, nL.z);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(TL.x, TL.y, TL.z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3f(C.x, C.y, C.z);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(BL.x, BL.y, BL.z);
            // render bottom triangle
            GL11.glNormal3f(nB.x, nB.y, nB.z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3f(C.x, C.y, C.z);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(BR.x, BR.y, BR.z);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(BL.x, BL.y, BL.z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL11.glDisable(GL11.GL_LIGHT0);
        GL11.glDisable(GL11.GL_LIGHTING);
    }
    
    private void renderGrid() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        if (Mapper.currType==Type.elevation) {
            GL11.glPointSize(7);
        }
        else {
            GL11.glLineWidth(1);
        }
        
        for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
            for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                renderLines(i, i2);
                renderPoint(i, i2);
            }
        }
        
        GL11.glColor3f(1, 1, 1);
    }
    
    private void renderLines(int i, int i2) {
        if (i<0 || i2<0 || i>Mapper.width-1 || i2>Mapper.height-1) {}
        else {
            if (!HouseCalc.renderHeight && !(Mapper.currType==Type.elevation)) {
                if (i2>0 && (Mapper.ground[i][i2-1].writ==null ^ Mapper.ground[i][i2].writ==null)) {
                    if ((Mapper.ground[i][i2-1].writ!=null && Mapper.ground[i][i2-1].writ==Mapper.wData) || (Mapper.ground[i][i2].writ!=null && Mapper.ground[i][i2].writ==Mapper.wData)) {
                        GL11.glColor3f(0.1f, 1, 0.1f);
                    }
                    else {
                        GL11.glColor3f(0.8f, 0.8f, 0.8f);
                    }
                }
                else {
                    GL11.glColor3f(0.4f, 0.4f, 0.4f);
                }
            }
            
            if (!HouseCalc.renderHeight && !(Mapper.currType==Type.elevation)) {
                GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3f(i*4,-i2*4, -99);
                    GL11.glVertex3f(i*4+4,-i2*4, -99);
                GL11.glEnd();
            }
            else {
                GL11.glBegin(GL11.GL_LINES);
                    float color = (Mapper.heightmap[i][i2]-Mapper.minElevation)/Mapper.diffElevation;
                    GL11.glColor3f(color, 1-color, 0);
                    GL11.glVertex3f(i*4,-i2*4, -99);
                    color = (Mapper.heightmap[i+1][i2]-Mapper.minElevation)/Mapper.diffElevation;
                    GL11.glColor3f(color, 1-color, 0);
                    GL11.glVertex3f(i*4+4,-i2*4, -99);
                GL11.glEnd();
            }

            if (!HouseCalc.renderHeight && !(Mapper.currType==Type.elevation)) {
                if (i>0 && (Mapper.ground[i-1][i2].writ==null ^ Mapper.ground[i][i2].writ==null)) {
                    if ((Mapper.ground[i-1][i2].writ!=null && Mapper.ground[i-1][i2].writ==Mapper.wData) || (Mapper.ground[i][i2].writ!=null && Mapper.ground[i][i2].writ==Mapper.wData)) {
                        GL11.glColor3f(0.1f, 1, 0.1f);
                    }
                    else {
                        GL11.glColor3f(0.8f, 0.8f, 0.8f);
                    }
                }
                else {
                    GL11.glColor3f(0.4f, 0.4f, 0.4f);
                }
            }
            
            if (!HouseCalc.renderHeight && !(Mapper.currType==Type.elevation)) {
                GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3f(i*4,-i2*4, -99);
                    GL11.glVertex3f(i*4,-i2*4-4, -99);
                GL11.glEnd();
            }
            else {
                GL11.glLineWidth(3);
                GL11.glBegin(GL11.GL_LINES);
                    float color = (Mapper.heightmap[i][i2]-Mapper.minElevation)/Mapper.diffElevation;
                    GL11.glColor3f(color, 1-color, 0);
                    GL11.glVertex3f(i*4,-i2*4, -99);
                    color = (Mapper.heightmap[i][i2+1]-Mapper.minElevation)/Mapper.diffElevation;
                    GL11.glColor3f(color, 1-color, 0);
                    GL11.glVertex3f(i*4,-i2*4-4, -99);
                    GL11.glColor3f(1, 1, 1);
                GL11.glEnd();
            }
        }
    }
    
    private void renderPoint(int i, int i2) {
        if (i<0 || i2<0 || i>Mapper.width || i2>Mapper.height) {}
        else if (Mapper.currType==Type.elevation) {
            GL11.glColor3f(1, 1, 1);
            GL11.glBegin(GL11.GL_POINTS);
                GL11.glVertex3f(i*4,-i2*4, -99.5f);
            GL11.glEnd();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Skybox rendering code">
    private void renderSkybox() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, D.skybox.ID);
        GL11.glBegin(GL11.GL_TRIANGLES);
            //up
            GL11.glTexCoord2f(0.25f, 0);
            GL11.glVertex3d(-150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 0);
            GL11.glVertex3d(150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 0);
            GL11.glVertex3d(-150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);

            //down
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1);
            GL11.glVertex3d(-150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1);
            GL11.glVertex3d(150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 1);
            GL11.glVertex3d(150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);

            //front
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);

            //back
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 2f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(1, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(1, 2f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(1, 1f/3f);
            GL11.glVertex3d(-150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3d(150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);

            //left
            GL11.glTexCoord2f(0, 1f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0, 2f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,-150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0, 1f/3f);
            GL11.glVertex3d(-149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);

            //right
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 2f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,-150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3d(149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
        GL11.glEnd();
    }
    //</editor-fold>
    
    private void renderWater() {
        if (!fpsView && Mapper.z<3) {
            switch (Mapper.z) {
                case 0: 
                    GL11.glColor4f(1, 1, 1, 0.5f);
                    break;
                case 1:
                    GL11.glColor4f(1-0.4f, 1-0.4f, 1-0.4f, 0.5f);
                    break;
                case 2:
                    GL11.glColor4f(1-0.75f, 1-0.75f, 1-0.75f, 0.5f);
                    break;
            }
        }
        else if (fpsView) {
            GL11.glColor4f(1, 1, 1, 0.5f);
        }
        else {
            return;
        }
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, D.water.ID);
        for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
            for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                renderWaterTile(i, i2);
            }
        }
    }
    
    private void renderWaterTile(int x, int y) {
        if (y>=Mapper.height || y<0 || x>=Mapper.width || x<0) {
            return;
        }
        
        GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.05f);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(x*4+4,-y*4-4, 0.05f);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, 0.05f);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, 0.05f);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(x*4,-y*4, 0.05f);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.05f);
        GL11.glEnd();
    }
    
}
