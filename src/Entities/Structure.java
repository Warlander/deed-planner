package Entities;


public class Structure {
    
    public String name;
    public String shortName;
    
    public double rPaint=1;
    public double gPaint=1;
    public double bPaint=1;
    
    public int rotation=0;
    
    public Structure copy() {
        return new Structure(name, shortName);
    }
    
    public Structure(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }
    
    public String toString() {
        return name;
    }
    
}
