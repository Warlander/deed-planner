package Lib.Entities;

import Form.HouseCalc;
import Lib.Graphics.Tex;
import Lib.Object.Materials;
import Lib.Object.ObjectData;
import Lib.Object.Rotation;
import Lib.Object.Type;
import Lib.Utils.MatrixTools;
import Mapper.Mapper;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Data {
    
    protected static final double floor1 = 0.4;
    protected static final double floor2 = 0.75;
    
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
    
    public int roofLevel;
    
    private static final FloatBuffer upMatrix;
    private static final FloatBuffer rightMatrix;
    private static final FloatBuffer downMatrix;
    private static final FloatBuffer leftMatrix;
    
    static {
        upMatrix = BufferUtils.createFloatBuffer(16);
        rightMatrix = BufferUtils.createFloatBuffer(16);
        downMatrix = BufferUtils.createFloatBuffer(16);
        leftMatrix = BufferUtils.createFloatBuffer(16);
        
        upMatrix.put(new float[] {
            1, 0, 0, 0,
            0,-1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        rightMatrix.put(new float[] {
            0, 1, 0, 0,
            1, 0, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        downMatrix.put(new float[] {
           -1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        leftMatrix.put(new float[] {
            0,-1, 0, 0,
           -1, 0, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
    }
    
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
        if (Mapper.fpsView || ((sy-Mapper.getFloor())<=0 && (sy-Mapper.getFloor())>-3)) {
            texture.use();
            if (type==Type.wall && !Mapper.fpsView) {
                double showR = 0;
                double showG = 0;
                double showB = 0;
                switch (sy-Mapper.getFloor()) {
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
                switch (sy-Mapper.getFloor()) {
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
                height = (Mapper.heightmap[sx-1][sz]+Mapper.heightmap[sx-1][sz+1]+Mapper.heightmap[sx][sz]+Mapper.heightmap[sx][sz+1])/4f/35f*3f;
            }
            if (type==Type.roof) {
                height += roofLevel*4-4;
            }
            
            if (type==Type.wall) {
                if (rotation==Rotation.horizontal) {
                    height = Math.min(Mapper.heightmap[sx-1][sz], Mapper.heightmap[sx][sz])/(35f/3f);
                }
                else if (rotation==Rotation.vertical) {
                    height = Math.min(Mapper.heightmap[sx][sz+1], Mapper.heightmap[sx][sz])/(35f/3f);
                }
            }
            
            GL11.glPushMatrix();
            
                GL11.glTranslatef(sx*4, sy*3+height, sz*4);
                if (type==Type.roof) {
                    GL11.glTranslatef(-2, 0, -2);
                }
                GL11.glRotatef(90, 1, 0, 0);
                if (rotation==Rotation.vertical) {
                    GL11.glRotatef(270, 0, 0, 1);
                }

                if (type==Type.wall && !Mapper.fpsView) {
                    GL11.glScalef(1, renderMultiplier, 1);
                }
                else if (type==Type.floor || type==Type.roof) {
                    GL11.glTranslatef(0, 4, 0);
                }
                
                if (type==Type.wall) {
                    if (rotation==Rotation.horizontal) {
                        float relativeHeight = Mapper.heightmap[sx-1][sz]-Mapper.heightmap[sx][sz];
                        relativeHeight/=47f;
                        if (relativeHeight<0) {
                            GL11.glTranslatef(0, 0, relativeHeight*4f);
                        }
                        MatrixTools.deform(relativeHeight);
                    }
                    else if (rotation==Rotation.vertical) {
                        float relativeHeight = Mapper.heightmap[sx][sz+1]-Mapper.heightmap[sx][sz];
                        relativeHeight/=47f;
                        if (relativeHeight<0) {
                            GL11.glTranslatef(0, 0, relativeHeight*4f);
                        }
                        MatrixTools.deform(relativeHeight);
                    }
                }
                else if (type==Type.roof) {
                    switch (facing) {
                        case up:
                                MatrixTools.multMatrix(upMatrix);
                                break;
                            case right:
                                MatrixTools.multMatrix(rightMatrix);
                                break;
                            case down:
                                MatrixTools.multMatrix(downMatrix);
                                break;
                            case left:
                                MatrixTools.multMatrix(leftMatrix);
                                break;
                    }
                }
                
                object.render();
            GL11.glPopMatrix();
        }
    }
    
    public String toString() {
        return name;
    }
    
}
