package Server;

import Entities.Ground;
import Entities.Writ;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveSystem {
    
    private static final String endl = System.getProperty("line.separator");
    
    protected static void saveMaker(String path) {
        try {
            PrintStream out = new PrintStream(path);
            saveMaker(out);
            out.close();
            System.out.println("Map saved!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected static void saveMaker(PrintStream out) {
        StringBuilder maps = new StringBuilder();
        maps.append("1.0|").append(endl);
        maps.append(Kernel.width).append(" ").append(Kernel.height).append(endl);
        
        for (int x=0; x<=Kernel.width; x++) {
            for (int y=0; y<=Kernel.height; y++) {
                if (Kernel.heightmap[x][y]!=0f) {
                    saveHeight(maps, x, y);
                }
            }
        }
        
        for (int x=0; x<Kernel.width; x++) {
            for (int y=0; y<Kernel.height; y++) {
                if (Kernel.ground[x][y]!=null && !Kernel.ground[x][y].shortName.equals(D.grounds.get(0).shortName)) {
                    saveGround(maps, x, y);
                }
            }
        }
        
        for (int x=0; x<Kernel.width; x++) {
            for (int y=0; y<Kernel.height; y++) {
                if (Kernel.caveGround[x][y]!=null && !Kernel.caveGround[x][y].shortName.equals(D.caveGrounds.get(0).shortName)) {
                    saveCave(maps, x, y);
                }
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Kernel.width*4; x++) {
                for (int y=0; y<Kernel.height*4; y++) {
                    if (Kernel.objects[x][y][z]!=null) {
                        saveObject(maps, x, y, z);
                    }
                }
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Kernel.width; x++) {
                for (int y=0; y<Kernel.height; y++) {
                    if (Kernel.tiles[x][y][z]!=null) {
                        saveTile(maps, x, y, z);
                    }
                }
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Kernel.width; x++) {
                for (int y=0; y<Kernel.height; y++) {
                    if (Kernel.bordersx[x][y][z]!=null) {
                        saveBorderX(maps, x, y, z);
                    }
                }
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Kernel.width; x++) {
                for (int y=0; y<Kernel.height; y++) {
                    if (Kernel.bordersy[x][y][z]!=null) {
                        saveBorderY(maps, x, y, z);
                    }
                }
            }
        }
        
        for (int x=0; x<Kernel.width; x++) {
            for (int y=0; y<Kernel.height; y++) {
                if (Kernel.labels[x][y]!=null) {
                    saveLabel(maps, x, y);
                }
                if (Kernel.caveLabels[x][y]!=null) {
                    saveCaveLabel(maps, x, y);
                }
            }
        }
        
        if (!Kernel.writs.isEmpty()) {
            for (int i=0; i<Kernel.writs.size(); i++) {
                Writ w = (Writ)Kernel.writs.get(i);
                saveWrit(maps, w);
            }
        }
        String save = maps.toString();
        
        out.print(save);
    }
    
    protected static void saveHeight(StringBuilder builder, int x, int y) {
        builder.append("H ").append(x).append(" ").append(y).append(" ").append((int)Kernel.heightmap[x][y]).append(endl);
    }
    
    protected static void saveGround(StringBuilder builder, int x, int y) {
        builder.append("G ").append(x).append(" ").append(y).append(" ").append(Kernel.ground[x][y].shortName).append(endl);
    }
    
    protected static void saveCave(StringBuilder builder, int x, int y) {
        builder.append("C ").append(x).append(" ").append(y).append(" ").append(Kernel.caveGround[x][y].shortName).append(endl);
    }
    
    /**
     * 1. Type
     * 2. x coord
     * 3. y coord
     * 4. z coord
     * 5. Shortname
     * 6. Red
     * 7. Green
     * 8. Blue
     * 9. Rotation
     */
    protected static void saveObject(StringBuilder builder, int x, int y, int z) {
        try {
            builder.append("O ").append(x).append(" ").append(y).append(" ").append(z).append(" ").append(Kernel.objects[x][y][z].shortName).append(" ").append(Kernel.objects[x][y][z].rPaint).append(" ").append(Kernel.objects[x][y][z].gPaint).append(" ").append(Kernel.objects[x][y][z].bPaint).append(" ").append(Kernel.objects[x][y][z].rotation).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DO ").append(x).append(" ").append(y).append(" ").append(z).append(endl);
        }
    }
    
    protected static void saveTile(StringBuilder builder, int x, int y, int z) {
        try {
            builder.append("T ").append(x).append(" ").append(y).append(" ").append(z).append(" ").append(Kernel.tiles[x][y][z].shortName).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DT ").append(x).append(" ").append(y).append(" ").append(z).append(endl);
        }
    }
    
    protected static void saveBorderX(StringBuilder builder, int x, int y, int z) {
        try {
            builder.append("BX ").append(x).append(" ").append(y).append(" ").append(z).append(" ").append(Kernel.bordersx[x][y][z].shortName).append(" ").append(Kernel.bordersx[x][y][z].rPaint).append(" ").append(Kernel.bordersx[x][y][z].gPaint).append(" ").append(Kernel.bordersx[x][y][z].bPaint).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DBX ").append(x).append(" ").append(y).append(" ").append(z).append(endl);
        }
    }
    
    protected static void saveBorderY(StringBuilder builder, int x, int y, int z) {
        try {
            builder.append("BY ").append(x).append(" ").append(y).append(" ").append(z).append(" ").append(Kernel.bordersy[x][y][z].shortName).append(" ").append(Kernel.bordersy[x][y][z].rPaint).append(" ").append(Kernel.bordersy[x][y][z].gPaint).append(" ").append(Kernel.bordersy[x][y][z].bPaint).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DBY ").append(x).append(" ").append(y).append(" ").append(z).append(endl);
        }
    }
    
    /**
     * 1. Type
     * 2. x coord
     * 3. y coord
     * 4. Text
     * 5. Font
     * 6. Size
     * 7. Red
     * 8. Green
     * 9. Blue
     * 10. Alpha
     * 11. Cave
     */
    protected static void saveLabel(StringBuilder builder, int x, int y) {
        try {
            builder.append("L ").append(x).append(" ").append(y).append(" ").append(Kernel.labels[x][y].text.replace(" ", "_").replace("\n", "\\n")).append(" ").append(Kernel.labels[x][y].font.getName().replace(" ", "_")).append(" ").append(Kernel.labels[x][y].font.getSize()).append(" ").append(Kernel.labels[x][y].color.getRed()).append(" ").append(Kernel.labels[x][y].color.getGreen()).append(" ").append(Kernel.labels[x][y].color.getBlue()).append(" ").append(Kernel.labels[x][y].color.getAlpha()).append(" ").append(false).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DL ").append(x).append(" ").append(y).append(false).append(endl);
        }
    }
    
    protected static void saveCaveLabel(StringBuilder builder, int x, int y) {
        try {
            builder.append("L ").append(x).append(" ").append(y).append(" ").append(Kernel.caveLabels[x][y].text.replace(" ", "_").replace("\n", "\\n")).append(" ").append(Kernel.caveLabels[x][y].font.getName().replace(" ", "_")).append(" ").append(Kernel.caveLabels[x][y].font.getSize()).append(" ").append(Kernel.caveLabels[x][y].color.getRed()).append(" ").append(Kernel.caveLabels[x][y].color.getGreen()).append(" ").append(Kernel.caveLabels[x][y].color.getBlue()).append(" ").append(Kernel.caveLabels[x][y].color.getAlpha()).append(" ").append(true).append(endl);
        } catch (NullPointerException ex) {
            builder.append("DL ").append(x).append(" ").append(y).append(true).append(endl);
        }
    }
    
    protected static void saveWrit(StringBuilder builder, Writ writ) {
        builder.append("W ").append(writ.name.replace(" ", "_")).append(" ").append(writ.tiles.size());
        for (Ground g : writ.tiles) {
            builder.append(" ").append(g.x).append(" ").append(g.y);
        }
        builder.append(endl);
    }
    
    protected static void saveWritDeletion(StringBuilder builder, String writ) {
        builder.append("DW ").append(writ.replace(" ", "_")).append(" ").append(endl);
    }
    
}
