package Server;

import Entities.Data;
import Entities.Ground;
import Entities.Label;
import Entities.Structure;

public class MapUtils {
    
    protected static boolean resizeMap(int left, int right, int up, int down) {
        int width = Kernel.width+right+left;
        int height = Kernel.height+up+down;
        
        if (width<25 || width>9999) {
            System.out.println("Cannot resize map: width must be above or equal 25 and below or equal 9999");
            return false;
        }
        else if (height<25 || height>9999) {
            System.out.println("Cannot resize map: height must be above or equal 25 and below or equal 9999");
            return false;
        }
        
        Structure[][][] objects = new Structure[(Kernel.width+right+left)*4][(Kernel.height+up+down)*4][15];
        Data[][][] tiles = new Data[Kernel.width+right+left][Kernel.height+up+down][15];
        Data[][][] bordersx = new Data[Kernel.width+right+left][Kernel.height+up+down][15];
        Data[][][] bordersy = new Data[Kernel.width+right+left][Kernel.height+up+down][15];
        Ground[][] ground = new Ground[Kernel.width+right+left][Kernel.height+up+down];
        Ground[][] caveGround = new Ground[Kernel.width+right+left][Kernel.height+up+down];
        Label[][] labels = new Label[Kernel.width+right+left][Kernel.height+up+down];
        Label[][] caveLabels = new Label[Kernel.width+right+left][Kernel.height+up+down];
        float[][] heightmap = new float[Kernel.width+right+left+1][Kernel.height+up+down+1];
        
        int xReg = 0;
        int yReg = 0;
        
        for (int i=-left; i<Kernel.width+right; i++) {
            for (int i2=-down; i2<Kernel.height+up; i2++) {
                if (i>=0 && i<Kernel.width && i2>=0 && i2<Kernel.height) {
                    for (int i3=0; i3<15; i3++) {
                        for (int i4=0; i4<4; i4++) {
                            for (int i5=0; i5<4; i5++) {
                                objects[xReg*4+i4][yReg*4+i5][i3] = Kernel.objects[i*4+i4][i2*4+i5][i3];
                            }
                        }
                        tiles[xReg][yReg][i3] = Kernel.tiles[i][i2][i3];
                        bordersx[xReg][yReg][i3] = Kernel.bordersx[i][i2][i3];
                        bordersy[xReg][yReg][i3] = Kernel.bordersy[i][i2][i3];
                    }
                    labels[xReg][yReg] = Kernel.labels[i][i2];
                    caveLabels[xReg][yReg] = Kernel.caveLabels[i][i2];
                    ground[xReg][yReg] = Kernel.ground[i][i2];
                    caveGround[xReg][yReg] = Kernel.caveGround[i][i2];
                }
                yReg++;
            }
            xReg++;
            yReg=0;
        }
        
        xReg = 0;
        yReg = 0;
        
        for (int i=-left; i<Kernel.width+right+1; i++) {
            for (int i2=-down; i2<Kernel.height+up+1; i2++) {
                if (i>=0 && i<Kernel.width+1 && i2>=0 && i2<Kernel.height+1) {
                    heightmap[xReg][yReg] = Kernel.heightmap[i][i2];
                }
                yReg++;
            }
            xReg++;
            yReg=0;
        }
        
        for (int i=0; i<Kernel.width+right+left; i++) {
            for (int i2=0; i2<Kernel.height+up+down; i2++) {
                if (ground[i][i2]!=null) {
                    ground[i][i2].x = i;
                    ground[i][i2].y = i2;
                }
                if (caveGround[i][i2]!=null) {
                    caveGround[i][i2].x = i;
                    caveGround[i][i2].y = i2;
                }
            }
        }
        
        if (Kernel.width>width) {
            Kernel.width = width;
        }
        if (Kernel.height>height) {
            Kernel.height = height;
        }
        Kernel.heightmap = heightmap;
        Kernel.labels = labels;
        Kernel.caveLabels = caveLabels;
        Kernel.objects = objects;
        Kernel.tiles = tiles;
        Kernel.bordersx = bordersx;
        Kernel.bordersy = bordersy;
        Kernel.ground = ground;
        Kernel.caveGround = caveGround;
        Kernel.width = width;
        Kernel.height = height;
        
        return true;
    }
    
    protected static void reset() {
        Kernel.caveGround=new Ground[25][25];
        Kernel.ground=new Ground[25][25];
        Kernel.heightmap = new float[25+1][25+1];
        Kernel.tiles=new Data[25][25][15];
        Kernel.objects = new Structure[100][100][15];
        Kernel.labels = new Label[25][25];
        Kernel.caveLabels = new Label[25][25];
        Kernel.bordersx=new Data[25][25][15];
        Kernel.bordersy=new Data[25][25][15];
        Kernel.heightmap = new float[Kernel.width+1][Kernel.height+1];
        Kernel.writs.clear();
    }
    
}
