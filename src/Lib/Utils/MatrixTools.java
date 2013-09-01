package Lib.Utils;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class MatrixTools {

    public static void deform(float scale) {
        FloatBuffer matrix = (FloatBuffer)BufferUtils.createFloatBuffer(16);
        matrix.put(new float[] {1, 0, scale, 0,
                                0, 1, 0, 0,
                                0, 0, 1, 0,
                                0, 0, 0, 1}).flip();
        GL11.glMultMatrix(matrix);
    }
    
    public static void multMatrix(FloatBuffer matrix) {
        GL11.glMultMatrix(matrix);
    }
    
}
