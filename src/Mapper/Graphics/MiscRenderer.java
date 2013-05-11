package Mapper.Graphics;

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
                renderGrid();
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
                case 0: 
                    GL11.glColor3f(1, 1, 1);
                    break;
                case 1:
                    GL11.glColor3f(1-0.4f, 1-0.4f, 1-0.4f);
                    break;
                case 2:
                    GL11.glColor3f(1-0.75f, 1-0.75f, 1-0.75f);
                    break;
            }
            for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
                for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                    renderGroundTile(i, i2);
                }
            }
        }
        else if (fpsView) {
            GL11.glColor3f(1, 1, 1);
                for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
                    for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                        renderGroundTile(i, i2);
                    }
                }
        }
    }
    
    private void renderGroundTile(int x, int y) {
        if (y>=Mapper.height || y<0 || x>=Mapper.width || x<0) {
            return;
        }
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Mapper.ground[x][y].tex.ID);
        GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(x*4,-y*4-4);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(x*4+4,-y*4-4);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(x*4+4,-y*4);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(x*4,-y*4-4);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(x*4,-y*4);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(x*4+4,-y*4);
        GL11.glEnd();
    }
    
    private void renderGrid() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            for (int i=Camera.visibleDownY; i<Camera.visibleUpY; i++) {
                for (int i2=Camera.visibleDownX; i2<Camera.visibleUpX; i2++) {
                    if (i<0 || i2<0 || i>Mapper.width-1 || i2>Mapper.height-1) {}
                    else {
                        if (i2>0 && (Mapper.ground[i][i2-1].writ==null ^ Mapper.ground[i][i2].writ==null)) {
                            if ((Mapper.ground[i][i2-1].writ!=null && Mapper.ground[i][i2-1].writ==Mapper.wData) || (Mapper.ground[i][i2].writ!=null && Mapper.ground[i][i2].writ==Mapper.wData)) {
                                GL11.glColor3f(1, 1, 1);
                            }
                            else {
                                GL11.glColor3f(0.8f, 0.8f, 0.8f);
                            }
                        }
                        else {
                            GL11.glColor3f(0.4f, 0.4f, 0.4f);
                        }
                        GL11.glBegin(GL11.GL_LINES);
                            GL11.glVertex3f(i*4,-i2*4, -99);
                            GL11.glVertex3f(i*4+4,-i2*4, -99);
                        GL11.glEnd();
                        
                        if (i>0 && (Mapper.ground[i-1][i2].writ==null ^ Mapper.ground[i][i2].writ==null)) {
                            if ((Mapper.ground[i-1][i2].writ!=null && Mapper.ground[i-1][i2].writ==Mapper.wData) || (Mapper.ground[i][i2].writ!=null && Mapper.ground[i][i2].writ==Mapper.wData)) {
                                GL11.glColor3f(1, 1, 1);
                            }
                            else {
                                GL11.glColor3f(0.8f, 0.8f, 0.8f);
                            }
                        }
                        else {
                            GL11.glColor3f(0.4f, 0.4f, 0.4f);
                        }
                        GL11.glBegin(GL11.GL_LINES);
                            GL11.glVertex3f(i*4,-i2*4, -99);
                            GL11.glVertex3f(i*4,-i2*4-4, -99);
                        GL11.glEnd();
                    }
                }
            }
        GL11.glColor4f(1, 1, 1, 1);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Skybox rendering code">
    private void renderSkybox() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, D.skybox.ID);
        GL11.glBegin(GL11.GL_TRIANGLES);
            //up
            GL11.glTexCoord2f(0.25f, 0);
            GL11.glVertex3f(-150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,-150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 0);
            GL11.glVertex3f(150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 0);
            GL11.glVertex3f(-150+FPPCamera.posz,150-FPPCamera.posx,-149-FPPCamera.posy);

            //down
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1);
            GL11.glVertex3f(-150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1);
            GL11.glVertex3f(150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 1);
            GL11.glVertex3f(150+FPPCamera.posz,-150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,150-FPPCamera.posx, 149-FPPCamera.posy);

            //front
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,-149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,-149-FPPCamera.posx, -150-FPPCamera.posy);

            //back
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 2f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(1, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(1, 2f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,149-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(1, 1f/3f);
            GL11.glVertex3f(-150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3f(150+FPPCamera.posz,149-FPPCamera.posx, -150-FPPCamera.posy);

            //left
            GL11.glTexCoord2f(0, 1f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0, 2f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.25f, 2f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.25f, 1f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,-150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0, 1f/3f);
            GL11.glVertex3f(-149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);

            //right
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 2f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);

            GL11.glTexCoord2f(0.5f, 2f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,-150-FPPCamera.posx, 150-FPPCamera.posy);
            GL11.glTexCoord2f(0.5f, 1f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,-150-FPPCamera.posx, -150-FPPCamera.posy);
            GL11.glTexCoord2f(0.75f, 1f/3f);
            GL11.glVertex3f(149+FPPCamera.posz,150-FPPCamera.posx, -150-FPPCamera.posy);
        GL11.glEnd();
    }
    //</editor-fold>
    
    private void renderWater() {
        GL11.glColor4f(1, 1, 1, 0.5f);
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
            GL11.glVertex3f(x*4,-y*4-4, 0.01f);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(x*4+4,-y*4-4, 0.01f);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.01f);

            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(x*4,-y*4-4, 0.01f);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(x*4,-y*4, 0.01f);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(x*4+4,-y*4, 0.01f);
        GL11.glEnd();
    }
    
}
