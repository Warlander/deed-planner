package Entities;

public class Data {
    
    public String name;
    public String shortName;
    
    public Type type;
    
    public double rPaint=1;
    public double gPaint=1;
    public double bPaint=1;
    
    public Data copy() {
        return new Data(name, shortName, type);
    }
    
    public Data(String name, String shortName, Type type) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
    }
    
    public String toString() {
        return name;
    }
    
}
