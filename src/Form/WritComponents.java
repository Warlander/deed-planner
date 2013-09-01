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
            for (int y=0; y<15; y++) {
                if (Mapper.tiles[g.x+1][y][g.y]!=null) {
                    data.add(Mapper.tiles[g.x+1][y][g.y]);
                }
                if (Mapper.bordersx[g.x+1][y][g.y+1]!=null && !data.contains(Mapper.bordersx[g.x+1][y][g.y+1])) {
                    data.add(Mapper.bordersx[g.x+1][y][g.y+1]);
                }
                if (Mapper.bordersx[g.x+1][y][g.y]!=null && !data.contains(Mapper.bordersx[g.x+1][y][g.y])) {
                    data.add(Mapper.bordersx[g.x+1][y][g.y]);
                }
                if (Mapper.bordersy[g.x][y][g.y]!=null && !data.contains(Mapper.bordersy[g.x][y][g.y])) {
                    data.add(Mapper.bordersy[g.x][y][g.y]);
                }
                if (Mapper.bordersy[g.x+1][y][g.y]!=null && !data.contains(Mapper.bordersy[g.x+1][y][g.y])) {
                    data.add(Mapper.bordersy[g.x+1][y][g.y]);
                }
            }
        }
    }
    
}
