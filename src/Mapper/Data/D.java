package Mapper.Data;

import Lib.Graphics.Ground;
import Lib.Graphics.Tex;
import Lib.Object.Data;
import Lib.Object.ObjectData;
import java.util.ArrayList;

public class D {
    
    public static ArrayList<Data> models = new ArrayList<>();
    public static ArrayList<Ground> grounds = new ArrayList<>();
    
    public static Tex skybox;
    public static Tex water;
    
    public static ObjectData side;
    public static ObjectData sideCorner;
    public static ObjectData sideCut;
    
    public static ObjectData sideToSpine;
    
    public static ObjectData spine;
    public static ObjectData spineCorner;
    public static ObjectData spineCornerUp;
    public static ObjectData spineCross;
    public static ObjectData spineTCross;
    public static ObjectData spineTip;
    public static ObjectData spineEnd;
    public static ObjectData spineEndUp;
    
    public static ObjectData levelsCross;
    
    public static Data getDataByName(String name) {
        for (Data d : models) {
            if (d.name.equals(name)) {
                return d;
            }
        }
        return null;
    }
    
}
