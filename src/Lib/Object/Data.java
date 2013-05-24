package Lib.Object;

import Form.HouseCalc;
import Lib.Graphics.Tex;
import Mapper.Mapper;
import org.lwjgl.opengl.GL11;

public class Data {
    
    private static final double floor1 = 0.4;
    private static final double floor2 = 0.75;
    
    public float renderMultiplier=1;
    public int facing=up;
    
    public static final int up=0;
    public static final int right=1;
    public static final int down=2;
    public static final int left=3;
    
    public String name;
    public String shortName;
    public Tex texture;
    public ObjectData object;
    public Materials materials;
    
    public Type type;
    
    public double rPaint=1;
    public double gPaint=1;
    public double bPaint=1;
    
    public double r;
    public double g;
    public double b;
    
    public byte modX=1;
    public byte modY=1;
    
    public int roofLevel;
    
    public Data(String name, String shortName, Type type, Tex texture, ObjectData object) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        this.texture = texture;
        this.object = object;
    }
    
    public Data copy() {
        Data data = new Data(name, shortName, type, texture, object);
        data.materials=materials;
        data.r = r;
        data.g = g;
        data.b = b;
        data.renderMultiplier = renderMultiplier;
        if (type==Type.wall) {
            data.rPaint = HouseCalc.r;
            data.gPaint = HouseCalc.g;
            data.bPaint = HouseCalc.b;
        }
        return data;
    }
    
    public void render(int sx, int sy, int sz, Rotation rotation) {
        if (Mapper.fpsView || ((sz-Mapper.z)<=0 && (sz-Mapper.z)>-3)) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.ID);
            if (type==Type.wall && !Mapper.fpsView) {
                double showR = 0;
                double showG = 0;
                double showB = 0;
                switch (sz-Mapper.z) {
                    case 0:
                        showR = r*Mapper.tick+rPaint*(1-Mapper.tick);
                        showG = g*Mapper.tick+gPaint*(1-Mapper.tick);
                        showB = b*Mapper.tick+bPaint*(1-Mapper.tick);
                        break;
                    case -1:
                        showR = (r-(floor1*r))*Mapper.tick+(rPaint-(floor1*rPaint))*(1-Mapper.tick);
                        showG = (g-(floor1*g))*Mapper.tick+(gPaint-(floor1*gPaint))*(1-Mapper.tick);
                        showB = (b-(floor1*b))*Mapper.tick+(bPaint-(floor1*bPaint))*(1-Mapper.tick);
                        break;
                    case -2:
                        showR = (r-(floor2*r))*Mapper.tick+(rPaint-(floor2*rPaint))*(1-Mapper.tick);
                        showG = (g-(floor2*g))*Mapper.tick+(gPaint-(floor2*gPaint))*(1-Mapper.tick);
                        showB = (b-(floor2*b))*Mapper.tick+(bPaint-(floor2*bPaint))*(1-Mapper.tick);
                        break;
                }
                GL11.glColor3d(showR, showG, showB);
            }
            else if (!Mapper.fpsView) {
                switch (sz-Mapper.z) {
                    case 0:
                        GL11.glColor3d(1, 1, 1);
                        break;
                    case -1:
                        GL11.glColor3d(1-floor1, 1-floor1, 1-floor1);
                        break;
                    case -2:
                        GL11.glColor3d(1-floor2, 1-floor2, 1-floor2);
                        break;
                }
            }
            else {
                GL11.glColor3d(rPaint, gPaint, bPaint);
            }
            
            float height=0;
            if (type==Type.floor || type==Type.roof) {
                height = (Mapper.heightmap[-sx-1][sy]+Mapper.heightmap[-sx-1][sy+1]+Mapper.heightmap[-sx][sy]+Mapper.heightmap[-sx][sy+1])/4f/35f*3f;
            }
            
            GL11.glBegin(GL11.GL_TRIANGLES);
                for (int i=0; i<object.size; i++) {
                    GL11.glTexCoord2f(object.texU[object.coord[i]], 1-object.texV[object.coord[i]]);
                    if (type==Type.wall && !Mapper.fpsView) {
                        if (rotation==Rotation.horizontal) {
                            float ratio = -object.x[object.vert[i]]/4;
                            float addHeight = (Mapper.heightmap[-sx-1][sy]*ratio+Mapper.heightmap[-sx][sy]*(1-ratio))/35*3;
                            GL11.glVertex3f(object.x[object.vert[i]]-sx*4, object.y[object.vert[i]]+object.y[object.vert[i]]*renderMultiplier-sy*4, object.z[object.vert[i]]-sz*3-addHeight);
                        }
                        else if (rotation==Rotation.vertical) {
                            float ratio = -object.x[object.vert[i]]/4;
                            float addHeight = (Mapper.heightmap[sy][sx+1]*ratio+Mapper.heightmap[sy][sx]*(1-ratio))/35*3;
                            GL11.glVertex3f(object.x[object.vert[i]]-sx*4, object.y[object.vert[i]]+object.y[object.vert[i]]*renderMultiplier-sy*4, object.z[object.vert[i]]-sz*3-addHeight);
                        }
                    }
                    else if (type==Type.wall) {
                        if (rotation==Rotation.horizontal) {
                            float ratio = -object.x[object.vert[i]]/4;
                            float addHeight = (Mapper.heightmap[-sx-1][sy]*ratio+Mapper.heightmap[-sx][sy]*(1-ratio))/35*3;
                            GL11.glVertex3f(object.x[object.vert[i]]-sx*4, object.y[object.vert[i]]-sy*4, object.z[object.vert[i]]-sz*3-addHeight);
                        }
                        else if (rotation==Rotation.vertical) {
                            float ratio = -object.x[object.vert[i]]/4;
                            float addHeight = (Mapper.heightmap[sy][sx+1]*ratio+Mapper.heightmap[sy][sx]*(1-ratio))/35*3;
                            GL11.glVertex3f(object.x[object.vert[i]]-sx*4, object.y[object.vert[i]]-sy*4, object.z[object.vert[i]]-sz*3-addHeight);
                        }
                    }
                    else if (type==Type.roof) {
                        switch (facing) {
                            case up:
                                GL11.glVertex3f(object.x[object.vert[i]]*modX-sx*4-2, object.y[object.vert[i]]*modY-sy*4-2, object.z[object.vert[i]]-sz*3-(roofLevel-1)*3.5f-height);
                                break;
                            case right:
                                GL11.glVertex3f(object.y[object.vert[i]]*modX-sx*4-2, -object.x[object.vert[i]]*modY-sy*4-2, object.z[object.vert[i]]-sz*3-(roofLevel-1)*3.5f-height);
                                break;
                            case down:
                                GL11.glVertex3f(-object.x[object.vert[i]]*modX-sx*4-2, -object.y[object.vert[i]]*modY-sy*4-2, object.z[object.vert[i]]-sz*3-(roofLevel-1)*3.5f-height);
                                break;
                            case left:
                                GL11.glVertex3f(-object.y[object.vert[i]]*modX-sx*4-2, object.x[object.vert[i]]*modY-sy*4-2, object.z[object.vert[i]]-sz*3-(roofLevel-1)*3.5f-height);
                                break;
                        }
                    }
                    else {
                        GL11.glVertex3f(object.x[object.vert[i]]-sx*4, object.y[object.vert[i]]-sy*4, object.z[object.vert[i]]-sz*3-height);
                    }
                }
            GL11.glEnd();
        }
    }
    
    public String toString() {
        return name;
    }
    
}
