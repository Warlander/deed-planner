package Entities;

public class Ground {
    
    public int x;
    public int y;
    
    public String name;
    public String shortName;
    
    public Ground copy(int x, int y) {
        return new Ground(name, shortName, x, y);
    }
    
    public Ground(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }
    
    public Ground(String name, String shortName, int x , int y) {
        this(name, shortName);
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return name;
    }
    
}
