package Form;

import java.awt.Font;

public class FontWrapper {
    
    public Font font;
    
    public FontWrapper(Font font) {
        this.font = font;
    }
    
    public String toString() {
        return font.getName();
    }
    
}
