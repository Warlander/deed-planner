package Lib.Graphics;

import Lib.Files.Properties;
import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class TextureLoader {
    
    public static Tex loadPNGTexture(String filename) throws FileNotFoundException {
        boolean modern = GLContext.getCapabilities().OpenGL30;
        
        InputStream in = null;
        PNGDecoder dec = null;
        ByteBuffer buf = null;
        Tex tex = new Tex();
        in = new FileInputStream(filename);
        try {
        dec = new PNGDecoder(in);
        buf = ByteBuffer.allocateDirect(4*dec.getWidth()*dec.getHeight());
        dec.decode(buf, dec.getWidth()*4, PNGDecoder.Format.RGBA);
        buf.flip();
        } catch (IOException ex) {
            Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        int texId = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, dec.getWidth(), dec.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        if (modern) {
            if (Properties.getProperty("useMipmaps")!=null) {
                if ((boolean)Properties.getProperty("useMipmaps")) {
                    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                }
            }
            else {
                GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            }
        }
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        if (modern) {
            if (Properties.getProperty("useMipmaps")!=null) {
                if ((boolean)Properties.getProperty("useMipmaps")) {
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
                }
            }
            else {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            }
        }
        else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        }

        tex.ID = texId;
        tex.width = dec.getWidth();
        tex.height = dec.getHeight();
        return tex;
    }
    
}
