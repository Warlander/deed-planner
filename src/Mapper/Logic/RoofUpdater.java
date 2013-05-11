package Mapper.Logic;

import Form.HouseCalc;
import Lib.Object.Data;
import Lib.Object.Type;
import Mapper.Data.D;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;

public class RoofUpdater {
    
    protected void update(MouseInput mouse) {
        float tileScaler = (float)Display.getWidth()/(float)Display.getHeight();
        float tileSize = (float)Display.getHeight()/UpCamera.scale/4;
        int tileX = (int) ((mouse.x+UpCamera.y*tileSize)/((float)Display.getWidth()/UpCamera.scale/tileScaler))+1;
        int tileY = (int) ((mouse.y+UpCamera.x*tileSize)/((float)Display.getHeight()/UpCamera.scale));
        
        if (mouse.hold.left ^ mouse.hold.right) {
            if (Mapper.z==0) {
                return;
            }
            if (mouse.hold.left) {
                for (int i=0; i<15; i++) {
                    if (i==Mapper.z) {}
                    else if (Mapper.tiles[tileX][tileY][i]!=null && Mapper.tiles[tileX][tileY][i].type==Type.roof) {
                        if (i!=Mapper.z || Mapper.tiles[tileX][tileY][i].texture==Mapper.data.texture) {
                            return;
                        }
                    }
                }
            }
            
            int realX;
            int realY;
            
            for (int i=-HouseCalc.brushSize; i<=HouseCalc.brushSize; i++) {
                for (int i2=-HouseCalc.brushSize; i2<=HouseCalc.brushSize; i2++) {
                    realX = tileX+i;
                    realY = tileY+i2;
                    if (realX<2 || realY<1 || realX>Mapper.width-1 || tileY>Mapper.height-1) {}
                    else if (Mapper.ground[realX-1][realY].writ==null) {}
                    else if (Mapper.deleting) Mapper.tiles[realX][realY][Mapper.z]=null;
                    else if (mouse.hold.left) Mapper.tiles[realX][realY][Mapper.z]=Mapper.data.copy();
                    else if (mouse.hold.right) Mapper.tiles[realX][realY][Mapper.z]=null;
                }
            }
        }

        roofsRefit();
        
        if (tileX<2 || tileY<1 || tileX>Mapper.width-1 || tileY>Mapper.height-1) {return;}
        HouseCalc.statusBar.setData(Mapper.tiles[tileX][tileY][Mapper.z]);
    }
    
    public void roofsRefit() {
        int tVert=0;
        int tHorizon=0;
        int tPos=0;
        int tNeg=0;
        
        for (int i=0; i<Mapper.width; i++) {
            for (int i2=0; i2<Mapper.height; i2++) {
                for (int i3=0; i3<15; i3++) {
                    if (Mapper.tiles[i][i2][i3]!=null) {
                        if (Mapper.tiles[i][i2][i3].type==Type.roof) {
                            tVert=1;
                            while ((Mapper.tiles[i][i2+tVert][i3]!=null && Mapper.tiles[i][i2+tVert][i3].type==Type.roof) && (Mapper.tiles[i][i2-tVert][i3]!=null && Mapper.tiles[i][i2-tVert][i3].type==Type.roof)) {
                                tVert++;
                            }
                            tHorizon=1;
                            while ((Mapper.tiles[i+tHorizon][i2][i3]!=null && Mapper.tiles[i+tHorizon][i2][i3].type==Type.roof) && (Mapper.tiles[i-tHorizon][i2][i3]!=null && Mapper.tiles[i-tHorizon][i2][i3].type==Type.roof)) {
                                tHorizon++;
                            }
                            tPos=1;
                            while ((Mapper.tiles[i+tPos][i2+tPos][i3]!=null && Mapper.tiles[i+tPos][i2+tPos][i3].type==Type.roof) && (Mapper.tiles[i-tPos][i2-tPos][i3]!=null && Mapper.tiles[i-tPos][i2-tPos][i3].type==Type.roof)) {
                                tPos++;
                            }
                            tNeg=1;
                            while ((Mapper.tiles[i+tNeg][i2-tNeg][i3]!=null && Mapper.tiles[i+tNeg][i2-tNeg][i3].type==Type.roof) && (Mapper.tiles[i-tNeg][i2+tNeg][i3]!=null && Mapper.tiles[i-tNeg][i2+tNeg][i3].type==Type.roof)) {
                                tNeg++;
                            }
                            Mapper.tiles[i][i2][i3].roofLevel=Math.min(Math.min(tVert, tHorizon), Math.min(tPos, tNeg));
                        }
                        else {
                            Mapper.tiles[i][i2][i3].roofLevel=0;
                        }
                    }
                }
            }
        }
        
        for (int i=0; i<Mapper.width; i++) {
            for (int i2=0; i2<Mapper.height; i2++) {
                for (int i3=0; i3<15; i3++) {
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
                            switch (val) {
                                case 0:
                                    Mapper.tiles[i][i2][i3].modX = -1;
                                    break;
                                case 1:
                                    Mapper.tiles[i][i2][i3].modY = -1;
                                    break;
                                case 2:
                                    Mapper.tiles[i][i2][i3].modX = -1;
                                    break;
                                case 3:
                                    Mapper.tiles[i][i2][i3].modY = -1;
                                    break;
                            }
                            
                        }
                    }
                }
            }
        }
        
    }
    
    private int allCheck(int i, int i2, int i3, int[][] check) {
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
    
    private boolean checkMatch(int i, int i2, int i3, int[][] check) {
        int cX=0;
        int cY=0;
        int roof;
        for (int x=i-1; x<=i+1; x++) {
            for (int y=i2-1; y<=i2+1; y++) {
                if (Mapper.tiles[x][y][i3]==null) {
                    roof=0;
                }
                else {
                    roof = Mapper.tiles[x][y][i3].roofLevel;
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
    
    private int[][] rotate(int[][] in, int r) {
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
    
    private void printArray(int[][] arr) {
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
    
    private final int[][] side = {{ 1, 1, 1},
                                  { 0, 0, 0},
                                  {-1,-2,-1}};
    
    private final int[][] sideCorner = {{-1,-2,-1},
                                        {-2, 0, 0},
                                        {-1, 0, 1}};
    
    private final int[][] sideCut = {{ 1, 1, 1},
                                     { 1, 0, 0},
                                     { 1, 0,-2}};
    
    private final int[][] sideToSpine = {{-2, 0, 1},
                                         { 0, 0, 1},
                                         {-2, 0, 1}};
    
    private final int[][] spine = {{-1,-2,-1},
                                   { 0, 0, 0},
                                   {-1,-2,-1}};
    
    private final int[][] spineEnd = {{-1,-2,-1},
                                      {-2, 0, 0},
                                      {-1,-2,-1}};
    
    private final int[][] spineEndUp = {{-1,-2,-1},
                                        { 3, 0, 0},
                                        {-1, 0, 1}};
    
    private final int[][] spineEndUp2 = {{-1,-2,-1},
                                         { 0, 0, 3},
                                         { 1, 0,-1}};
    
    private final int[][] spineCorner = {{-1,-2,-1},
                                         { 0, 0,-2},
                                         {-2, 0,-1}};
    
    private final int[][] spineCornerUp = {{-2, 0,-2},
                                           { 0, 0, 0},
                                           {-2, 0, 1}};
    
    private final int[][] spineCross = {{-2, 0,-2},
                                        { 0, 0, 0},
                                        {-2, 0,-2}};
    
    private final int[][] spineTCross = {{-2, 0,-1},
                                         { 0, 0,-2},
                                         {-2, 0,-1}};
    
    private final int[][] spineTip = {{-1,-2,-1},
                                      {-2, 0,-2},
                                      {-1,-2,-1}};
    
    private final int[][] levelsCross = {{ 1, 0,-2},
                                         { 0, 0, 0},
                                         {-2, 0, 1}};
    
}
