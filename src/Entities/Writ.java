package Entities;

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
    
}
