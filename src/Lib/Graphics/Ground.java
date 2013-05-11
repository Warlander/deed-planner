package Lib.Graphics;

import Lib.Object.Writ;

public class Ground {
    
    public Tex tex;
    
    public int x;
    public int y;
    
    public String name;
    public String shortName;
    
    public Writ writ;
    
    public Ground copy(int x, int y) {
        return new Ground(tex, name, shortName, x, y);
    }
    
    public Ground(Tex tex, String name, String shortName) {
        this.tex = tex;
        this.name = name;
        this.shortName = shortName;
    }
    
    public Ground(Tex tex, String name, String shortName, int x , int y) {
        this.tex = tex;
        this.name = name;
        this.shortName = shortName;
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return name;
    }
    
}
