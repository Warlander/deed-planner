package Lib.Object;

import Lib.Graphics.TrueTypeFont;
import Mapper.Mapper;
import java.awt.Font;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

public class Label {
    
    public TrueTypeFont ttf;
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
    
    public void render(int sx, int sy) {
        if (ttf==null) {
            ttf = new TrueTypeFont(font, false);
        }
        if (!Mapper.fpsView) {
            GL11.glPushMatrix();
                GL11.glTranslatef(sy*4, 99.999f, sx*4-2);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glRotatef(90, 0, 0, 1);
                GL11.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
                ttf.drawString(0, 0, text, 0.125f, -0.125f, TrueTypeFont.ALIGN_CENTER);
            GL11.glPopMatrix();
        }
    }
    
}
