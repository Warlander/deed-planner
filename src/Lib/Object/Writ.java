package Lib.Object;

import Lib.Graphics.Ground;
import java.util.ArrayList;

public class Writ {
    
    public ArrayList<Ground> tiles;
    public String name;
    
    public Writ(String name) {
        tiles = new ArrayList<>();
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
}
