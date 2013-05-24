package Lib.Utils;

import Lib.Files.FileManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Screenshot {
    
    public static void takeScreenshot() {
        System.out.println("Taking screenshots");
        GL11.glReadBuffer(GL11.GL_FRONT);
        int width = Display.getWidth();
        int height= Display.getHeight();
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        System.out.println("Saving data from screen");
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
        System.out.println("Data from screen saved!");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        Calendar calendar = Calendar.getInstance();
        File file = FileManager.getFile(sdf.format(calendar.getTime())+".png");
        String format = "png";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                        int i = (x+(width*y))*bpp;
                        int r = buffer.get(i) & 0xFF;
                        int g = buffer.get(i + 1) & 0xFF;
                        int b = buffer.get(i + 2) & 0xFF;
                        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
                }
        }

        try {
            System.out.println("Saving to file");
            ImageIO.write(image, format, file);
            System.out.println("Screenshot saved!");
        } catch (IOException e) { e.printStackTrace(); }
    }
    
}
