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
import org.lwjgl.opengl.GL11;

public class MiscRenderer {
    
    public void update() {
        GL11.glPushMatrix();
            GL11.glRotatef(90, 1, 0, 0);
            GL11.glRotatef(90, 0, 0, 1);
            
            renderGround();
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
    
    private void renderGroundTile(Ground[][] source, int x, int y) {
        if (y>=Mapper.height || y<0 || x>=Mapper.width || x<0) {
            return;
        }
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, source[x][y].tex.ID);
        GL11.glBegin(GL11.GL_TRIANGLES);  
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, -Mapper.heightmap[x][y+1]/(35f/3f));
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(x*4+4,-y*4-4, -Mapper.heightmap[x+1][y+1]/(35f/3f));
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, -Mapper.heightmap[x+1][y]/(35f/3f));

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, -Mapper.heightmap[x][y+1]/(35f/3f));
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(x*4,-y*4, -Mapper.heightmap[x][y]/(35f/3f));
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, -Mapper.heightmap[x+1][y]/(35f/3f));
        GL11.glEnd();
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
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, 0.05f);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(x*4+4,-y*4-4, 0.05f);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.05f);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, 0.05f);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(x*4,-y*4, 0.05f);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.05f);
        GL11.glEnd();
    }
    
}
