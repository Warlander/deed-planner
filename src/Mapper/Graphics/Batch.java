package Mapper.Graphics;

import Lib.Entities.Data;
import Lib.Graphics.Tex;
import Lib.Object.Rotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Currently unused texture batch class which may be used in the future - performance update was smaller than I expected.
 * 
 * @author Warlander
 */
public final class Batch {

    private static HashMap<Tex, ArrayList<Point3>> batch = new HashMap<>();
    
    public static void addToBatch(Data data, int x, int y, int z, Rotation rotation) {
        Point3 point = new Point3(data, x, y, z, rotation);
        if (!batch.containsKey(data.texture)) {
            batch.put(data.texture, new ArrayList<Point3>());
        }
        batch.get(data.texture).add(point);
    }
    
    public static void renderBatch() {
        for (Map.Entry<Tex, ArrayList<Point3>> entry : batch.entrySet()) {
            for (Point3 p : entry.getValue()) {
                p.data.render(p.x, p.y, p.z, p.rotation);
            }
        }
        batch.clear();
    }
            
    private static class Point3 {
        
        Data data;
        public int x;
        public int y;
        public int z;
        public Rotation rotation;
        
        public Point3(Data data, int x, int y, int z, Rotation rotation) {
            this.data = data;
            this.x = x;
            this.y = y;
            this.z = z;
            this.rotation = rotation;
        }
        
    }
    
}
