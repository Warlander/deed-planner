package Lib.Entities;

import Lib.Graphics.Ground;
import Mapper.Mapper;
import java.util.ArrayList;

public class Writ {
    
    public ArrayList<Ground> tiles;
    public String name;
    
    public Writ(String name) {
        this();
        this.name = name;
    }
    
    public Writ() {
        tiles = new ArrayList<>();
    }
    
    public String toString() {
        return name;
    }
    
    public ArrayList<Data> getAllData() {
        ArrayList<Data> data = new ArrayList<>();
        
        for (Ground g : tiles) {
            for (int y=0; y<15; y++) {
                if (Mapper.tiles[g.x+1][y][g.y]!=null) {
                    data.add(Mapper.tiles[g.x+1][y][g.y]);
                }
                if (Mapper.bordersx[g.x+1][y][g.y+1]!=null && !data.contains(Mapper.bordersx[g.x+1][y][g.y+1])) {
                    data.add(Mapper.bordersx[g.x+1][y][g.y+1]);
                }
                if (Mapper.bordersx[g.x+1][y][g.y]!=null && !data.contains(Mapper.bordersx[g.x+1][y][g.y])) {
                    data.add(Mapper.bordersx[g.x+1][y][g.y]);
                }
                if (Mapper.bordersy[g.x][y][g.y]!=null && !data.contains(Mapper.bordersy[g.x][y][g.y])) {
                    data.add(Mapper.bordersy[g.x][y][g.y]);
                }
                if (Mapper.bordersy[g.x+1][y][g.y]!=null && !data.contains(Mapper.bordersy[g.x+1][y][g.y])) {
                    data.add(Mapper.bordersy[g.x+1][y][g.y]);
                }
            }
        }
        
        return data;
    }
    
}
