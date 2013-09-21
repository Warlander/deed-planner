package Mapper.Logic;

import Form.HouseCalc;
import Lib.Entities.Data;
import Lib.Object.Type;
import Mapper.Data.D;
import Mapper.Mapper;
import Mapper.Input.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public final class RoofUpdater {
    
    private RoofUpdater() {}
    
    protected static void update() {
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
        int tileX = (int) ((MouseInput.x+UpCamera.x*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler))+1;
        int tileY = (int) ((MouseInput.y+UpCamera.y*tileSize)/((float)Display.getHeight()/UpCamera.scale));
        
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            if (Mapper.getFloor()==0) {
                return;
            }
            
            int realX;
            int realY;
            
            for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
                for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                    realX = tileX+i;
                    realY = tileY+i2;
                    if (realX<2 || realY<1 || realX>Mapper.width-2 || tileY>Mapper.height-1) {}
                    else if (Mapper.ground[realX-1][realY].writ==null) {}
                    else if (Mapper.deleting) Mapper.tiles[realX][Mapper.getFloor()][realY]=null;
                    else if (MouseInput.hold.left) Mapper.tiles[realX][Mapper.getFloor()][realY]=Mapper.data.copy();
                    else if (MouseInput.hold.right) Mapper.tiles[realX][Mapper.getFloor()][realY]=null;
                }
            }
        }

        roofsRefit();
        
        if (tileX<2 || tileY<1 || tileX>Mapper.width-2 || tileY>Mapper.height-1) {return;}
        HouseCalc.statusBar.setData(Mapper.tiles[tileX][Mapper.getFloor()][tileY]);
    }
    
    public static void roofsRefit() {
        int tVert=0;
        int tHorizon=0;
        int tPos=0;
        int tNeg=0;
        
        for (int i=1; i<Mapper.width-1; i++) {
            for (int i2=1; i2<Mapper.height-1; i2++) {
                for (int i3=0; i3<15; i3++) {
                    if (Mapper.tiles[i][i3][i2]!=null) {
                        if (Mapper.tiles[i][i3][i2].type==Type.roof) {
                            tVert=1;
                            while ((Mapper.tiles[i][i3][i2+tVert]!=null && Mapper.tiles[i][i3][i2+tVert].type==Type.roof) && (Mapper.tiles[i][i3][i2-tVert]!=null && Mapper.tiles[i][i3][i2-tVert].type==Type.roof)) {
                                tVert++;
                            }
                            tHorizon=1;
                            while ((Mapper.tiles[i+tHorizon][i3][i2]!=null && Mapper.tiles[i+tHorizon][i3][i2].type==Type.roof) && (Mapper.tiles[i-tHorizon][i3][i2]!=null && Mapper.tiles[i-tHorizon][i3][i2].type==Type.roof)) {
                                tHorizon++;
                            }
                            tPos=1;
                            while ((Mapper.tiles[i+tPos][i3][i2+tPos]!=null && Mapper.tiles[i+tPos][i3][i2+tPos].type==Type.roof) && (Mapper.tiles[i-tPos][i3][i2-tPos]!=null && Mapper.tiles[i-tPos][i3][i2-tPos].type==Type.roof)) {
                                tPos++;
                            }
                            tNeg=1;
                            while ((Mapper.tiles[i+tNeg][i3][i2-tNeg]!=null && Mapper.tiles[i+tNeg][i3][i2-tNeg].type==Type.roof) && (Mapper.tiles[i-tNeg][i3][i2+tNeg]!=null && Mapper.tiles[i-tNeg][i3][i2+tNeg].type==Type.roof)) {
                                tNeg++;
                            }
                            Mapper.tiles[i][i3][i2].roofLevel=Math.min(Math.min(tVert, tHorizon), Math.min(tPos, tNeg));
                        }
                        else {
                            Mapper.tiles[i][i3][i2].roofLevel=0;
                        }
                    }
                }
            }
        }
        
        for (int i=0; i<Mapper.width; i++) {
            for (int i2=1; i2<15; i2++) {
                for (int i3=0; i3<Mapper.height; i3++) {
                    if (Mapper.tiles[i][i2][i3]!=null && Mapper.tiles[i][i2][i3].type==Type.roof) {
                        Mapper.tiles[i][i2][i3].object = D.spineTip;
                        int val=0;
                        if ((val=allCheck(i, i2, i3, side))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.side;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, sideCorner))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.sideCorner;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, sideToSpine))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.sideToSpine;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spine))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spine;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineEnd))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineEnd;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineCorner))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineCorner;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineCornerUp))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineCornerUp;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineCross))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineCross;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineTCross))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineTCross;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineTip))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineTip;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, levelsCross))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.levelsCross;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, sideCut))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.sideCut;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineEndUp))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineEndUp;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                        else if ((val=allCheck(i, i2, i3, spineEndUp2))!=4) {
                            Mapper.tiles[i][i2][i3].object = D.spineEndUp;
                            Mapper.tiles[i][i2][i3].facing = val;
                        }
                    }
                }
            }
        }
        
    }
    
    private static int allCheck(int i, int i2, int i3, int[][] check) {
        if (checkMatch(i, i2, i3, check)) {
            return Data.right;
        }
        else if (checkMatch(i, i2, i3, rotate(check, 3))) {
            return Data.down;
        }
        else if (checkMatch(i, i2, i3, rotate(check, 2))) {
            return Data.left;
        }
        else if (checkMatch(i, i2, i3, rotate(check, 1))) {
            return Data.up;
        }
        return 4;
    }
    
    private static boolean checkMatch(int i, int i2, int i3, int[][] check) {
        int cX=0;
        int cY=0;
        int roof;
        for (int x=i-1; x<=i+1; x++) {
            for (int z=i3-1; z<=i3+1; z++) {
                if (Mapper.tiles[x][i2][z]==null) {
                    roof=0;
                }
                else {
                    roof = Mapper.tiles[x][i2][z].roofLevel;
                }
                switch (check[cX][cY]) {
                    case -2:
                        if (Mapper.tiles[i][i2][i3].roofLevel<=roof) {
                            return false;
                        }
                        break;
                    case -1:
                        if (Mapper.tiles[i][i2][i3].roofLevel<roof) {
                            return false;
                        }
                        break;
                    case 0:
                        if (Mapper.tiles[i][i2][i3].roofLevel!=roof) {
                            return false;
                        }
                        break;
                    case 1:
                        if (Mapper.tiles[i][i2][i3].roofLevel>roof) {
                            return false;
                        }
                        break;
                    case 2:
                        if (Mapper.tiles[i][i2][i3].roofLevel>=roof) {
                            return false;
                        }
                        break;
                }
                cY++;
            }
            cX++;
            cY=0;
        }
        return true;
    }
    
    private static int[][] rotate(int[][] in, int r) {
        int[][] out = new int[3][3];
        int i=0;
        int i2=0;
        
        switch (r) {
            case 0:
                return in;
            case 1:
                for (int x=0; x<=2; x++) {
                    for (int y=2; y>=0; y--) {
                        out[x][y] = in[i][i2];
                        i++;
                    }
                    i2++;
                    i=0;
                }
                return out;
            case 2:
                for (int x=2; x>=0; x--) {
                    for (int y=2; y>=0; y--) {
                        out[x][y] = in[i][i2];
                        i2++;
                    }
                    i++;
                    i2=0;
                }
                return out;
            case 3:
                for (int x=2; x>=0; x--) {
                    for (int y=0; y<=2; y++) {
                        out[x][y] = in[i][i2];
                        i++;
                    }
                    i2++;
                    i=0;
                }
                return out;
        }
        return out;
    }
    
    private static void printArray(int[][] arr) {
        for (int i=0; i<3; i++) {
            for (int i2=0; i2<3; i2++) {
                if (arr[i][i2]>=0) {
                    System.out.print(" "+arr[i][i2]);
                }
                else {
                    System.out.print(arr[i][i2]);
                }
            }
            System.out.println();
        }
    }
    
    private static final int[][] side = {{ 1, 1, 1},
                                         { 0, 0, 0},
                                         {-1,-2,-1}};
    
    private static final int[][] sideCorner = {{-1,-2,-1},
                                               {-2, 0, 0},
                                               {-1, 0, 1}};
    
    private static final int[][] sideCut = {{ 1, 1, 1},
                                            { 1, 0, 0},
                                            { 1, 0,-2}};
    
    private static final int[][] sideToSpine = {{-2, 0, 1},
                                                { 0, 0, 1},
                                                {-2, 0, 1}};
    
    private static final int[][] spine = {{-1,-2,-1},
                                          { 0, 0, 0},
                                          {-1,-2,-1}};
    
    private static final int[][] spineEnd = {{-1,-2,-1},
                                             {-2, 0, 0},
                                             {-1,-2,-1}};
    
    private static final int[][] spineEndUp = {{-1,-2,-1},
                                               { 3, 0, 0},
                                               {-1, 0, 1}};
    
    private static final int[][] spineEndUp2 = {{-1,-2,-1},
                                                { 0, 0, 3},
                                                { 1, 0,-1}};
    
    private static final int[][] spineCorner = {{-1,-2,-1},
                                                { 0, 0,-2},
                                                {-2, 0,-1}};
    
    private static final int[][] spineCornerUp = {{-2, 0,-2},
                                                  { 0, 0, 0},
                                                  {-2, 0, 1}};
    
    private static final int[][] spineCross = {{-2, 0,-2},
                                               { 0, 0, 0},
                                               {-2, 0,-2}};
    
    private static final int[][] spineTCross = {{-2, 0,-1},
                                                { 0, 0,-2},
                                                {-2, 0,-1}};
    
    private static final int[][] spineTip = {{-1,-2,-1},
                                            {-2, 0,-2},
                                            {-1,-2,-1}};
    
    private static final int[][] levelsCross = {{ 1, 0,-2},
                                                { 0, 0, 0},
                                                {-2, 0, 1}};
    
}
