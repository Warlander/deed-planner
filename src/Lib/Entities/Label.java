package Lib.Entities;

import Lib.Graphics.TrueTypeFont;
import Lib.Utils.MatrixTools;
import java.awt.Font;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
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
        if (!Mapper.Mapper.fpsView) {
            GL11.glPushMatrix();
                GL11.glTranslatef(sx*4-2, 0.2f, sy*4);
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glColor4ub(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
                ttf.drawString(0, 0, text, 0.125f, -0.125f, TrueTypeFont.ALIGN_CENTER);
            GL11.glPopMatrix();
        }
    }
    
}
