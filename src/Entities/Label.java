package Entities;

import java.awt.Color;
import java.awt.Font;

public class Label {
    
    public Font font;
    public Color color;
    public String text;
    
    public boolean cave;
    
    public Label(Font font, Color color, String text, boolean cave) {
        this.font = font;
        this.color = color;
        this.text = text;
        this.cave = cave;
    }
    
}
