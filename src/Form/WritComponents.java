package Form;

import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.Writ;
import java.util.ArrayList;
import Mapper.Mapper;

public class WritComponents {
    
    public ArrayList<Data> data;
    
    public WritComponents(Writ writ) {
        data = new ArrayList<>();
        
        for (Ground g : writ.tiles) {
            for (int z=0; z<15; z++) {
                if (Mapper.tiles[g.x+1][g.y][z]!=null) {
                    data.add(Mapper.tiles[g.x+1][g.y][z]);
                }
                if (Mapper.bordersx[g.x+1][g.y+1][z]!=null && !data.contains(Mapper.bordersx[g.x+1][g.y+1][z])) {
                    data.add(Mapper.bordersx[g.x+1][g.y+1][z]);
                }
                if (Mapper.bordersx[g.x+1][g.y][z]!=null && !data.contains(Mapper.bordersx[g.x+1][g.y][z])) {
                    data.add(Mapper.bordersx[g.x+1][g.y][z]);
                }
                if (Mapper.bordersy[g.x][g.y][z]!=null && !data.contains(Mapper.bordersy[g.x][g.y][z])) {
                    data.add(Mapper.bordersy[g.x][g.y][z]);
                }
                if (Mapper.bordersy[g.x+1][g.y][z]!=null && !data.contains(Mapper.bordersy[g.x+1][g.y][z])) {
                    data.add(Mapper.bordersy[g.x+1][g.y][z]);
                }
            }
        }
    }
    
}
