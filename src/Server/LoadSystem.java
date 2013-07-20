package Server;

import Entities.Data;
import Entities.Ground;
import Entities.Label;
import Entities.Structure;
import Entities.Writ;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSystem {
    
    protected static void load1_0(BufferedReader br) throws IOException {
        Kernel.writs.clear();
        
        Scanner scan;
        
        br.readLine();
        String line = br.readLine();
        scan = new Scanner(line);
        int width = scan.nextInt();
        int height = scan.nextInt();
        
        float[][] heightmap = new float[width+1][height+1];
        Ground[][] ground = new Ground[width][height];
        Ground[][] caveGround = new Ground[width][height];
        Label[][] labels = new Label[width][height];
        Label[][] caveLabels = new Label[width][height];
        Structure[][][] objects = new Structure[width*4][height*4][15];
        Data[][][] tiles = new Data[width][height][15];
        Data[][][] bordersx = new Data[width][height][15];
        Data[][][] bordersy = new Data[width][height][15];
        
        for (int i=0; i<width; i++) {
            for (int i2=0; i2<height; i2++) {
                ground[i][i2] = D.grounds.get(0).copy(i, i2);
                caveGround[i][i2] = D.caveGrounds.get(0).copy(i, i2);
            }
        }
        
        loop:
        while ((line=br.readLine())!=null && !line.equals("")) {
            scan = new Scanner(line);
            
            switch (scan.next()) {
                case "H":
                    readHeight(scan, heightmap, null);
                    break;
                case "G":
                    readGround(scan, ground, null);
                    break;
                case "C":
                    readCave(scan, caveGround, null);
                    break;
                case "O":
                    readObject(scan, objects, null);
                    break;
                case "DO":
                    
                case "T":
                    readTile(scan, tiles, null);
                    break;
                case "DT":
                    
                case "BX":
                    readBorderX(scan, bordersx, null);
                    break;
                case "DBX":
                    
                case "BY":
                    readBorderY(scan, bordersy, null);
                    break;
                case "DBY":
                    
                case "L":
                    readLabel(scan, labels, caveLabels, null);
                    break;
                case "DL":
                    
                case "W":
                    readWrit(scan, ground, Kernel.writs, null);
                    break;
                case "DW":
                    String name = scan.next().replace("_", " ");
                    for (int i=0; i<Kernel.writs.size(); i++) {
                        Writ w = (Writ) Kernel.writs.get(i);
                        if (w.name.equals(name)) {
                            Kernel.writs.remove(i);
                            break;
                        }
                    }
                    break;
                default:
                    break loop;
            }
        }
        
        Kernel.width = width;
        Kernel.height = height;
        Kernel.heightmap = heightmap;
        Kernel.ground = ground;
        Kernel.labels = labels;
        Kernel.caveLabels = caveLabels;
        Kernel.caveGround = caveGround;
        Kernel.tiles = tiles;
        Kernel.objects = objects;
        Kernel.bordersx = bordersx;
        Kernel.bordersy = bordersy;
        System.out.println("Map loaded!");
    }
    
    protected static String readFragments(Scanner br) throws IOException {
        String line;
        Scanner scan;
        
        StringBuilder build = new StringBuilder();
        
        while ((line=br.nextLine())!=null && !line.equals("")) {
            scan = new Scanner(line);
            
            switch (scan.next()) {
                case "H":
                    readHeight(scan, Kernel.heightmap, build);
                    break;
                case "G":
                    readGround(scan, Kernel.ground, build);
                    break;
                case "C":
                    readCave(scan, Kernel.caveGround, build);
                    break;
                case "O":
                    readObject(scan, Kernel.objects, build);
                    break;
                case "T":
                    readTile(scan, Kernel.tiles, build);
                    break;
                case "BX":
                    readBorderX(scan, Kernel.bordersx, build);
                    break;
                case "BY":
                    readBorderY(scan, Kernel.bordersy, build);
                    break;
                case "L":
                    readLabel(scan, Kernel.labels, Kernel.caveLabels, build);
                    break;
                case "W":
                    readWrit(scan, Kernel.ground, Kernel.writs, build);
                    break;
            }
        }
        
        return build.toString();
    }
    
    public static void readHeight(Scanner source, float[][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        int val = source.nextInt();
        
        out[x][y] = val;
        
        if (build!=null) {
            SaveSystem.saveHeight(build, x, y);
        }
    }
    
    public static void readGround(Scanner source, Ground[][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            out[x][y] = getGround(shortName, x, y);
        }
        else {
            out[x][y] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveGround(build, x, y);
        }
    }
    
    public static void readCave(Scanner source, Ground[][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            out[x][y] = getCaveGround(shortName, x, y);
        }
        else {
            out[x][y] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveCave(build, x, y);
        }
    }
    
    public static void readObject(Scanner source, Structure[][][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            double rPaint = Double.parseDouble(source.next());
            double gPaint = Double.parseDouble(source.next());
            double bPaint = Double.parseDouble(source.next());
            int rotation = Integer.parseInt(source.next());

            Structure object = getLightweightStructure(shortName);
            object.rPaint = rPaint;
            object.gPaint = gPaint;
            object.bPaint = bPaint;
            object.rotation = rotation;
            out[x][y][z] = object;
        }
        else {
            out[x][y][z] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveObject(build, x, y, z);
        }
    }
    
    public static void readTile(Scanner source, Data[][][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            out[x][y][z] = getLightweightData(shortName);
        }
        else {
            out[x][y][z] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveTile(build, x, y, z);
        }
    }
    
    public static void readBorderX(Scanner source, Data[][][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            double rPaint = Double.parseDouble(source.next());
            double gPaint = Double.parseDouble(source.next());
            double bPaint = Double.parseDouble(source.next());

            Data object = getLightweightData(shortName);
            object.rPaint = rPaint;
            object.gPaint = gPaint;
            object.bPaint = bPaint;
            out[x][y][z] = object;
        }
        else {
            out[x][y][z] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveBorderX(build, x, y, z);
        }
    }
    
    public static void readBorderY(Scanner source, Data[][][] out, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        
        if (source.hasNext()) {
            String shortName = source.next();
            double rPaint = Double.parseDouble(source.next());
            double gPaint = Double.parseDouble(source.next());
            double bPaint = Double.parseDouble(source.next());

            Data object = getLightweightData(shortName);
            object.rPaint = rPaint;
            object.gPaint = gPaint;
            object.bPaint = bPaint;
            out[x][y][z] = object;
        }
        else {
            out[x][y][z] = null;
        }
        
        if (build!=null) {
            SaveSystem.saveBorderY(build, x, y, z);
        }
    }
    
    public static void readLabel(Scanner source, Label[][] out, Label[][] outAlt, StringBuilder build) {
        int x = source.nextInt();
        int y = source.nextInt();
        String text = source.next().replace("_", " ").replace("\\n", "\n");
        String font = source.next().replace("_", " ");
        int size = Integer.parseInt(source.next());
        int rPaint = Integer.parseInt(source.next());
        int gPaint = Integer.parseInt(source.next());
        int bPaint = Integer.parseInt(source.next());
        int aPaint = Integer.parseInt(source.next());
        boolean cave = false;
        if (source.hasNextBoolean()) {
            cave = source.nextBoolean();
        }
        
        Font f = new Font(font, Font.PLAIN, size);
        Color c = new Color(rPaint, gPaint, bPaint, aPaint);
        Label object = new Label(f, c, text, cave);
        if (!cave) {
            out[x][y] = object;
        }
        else {
            outAlt[x][y] = object;
        }
        
        if (build!=null && !cave) {
            SaveSystem.saveLabel(build, x, y);
        }
        else if (build!=null) {
            SaveSystem.saveCaveLabel(build, x, y);
        }
    }
    
    public static void readWrit(Scanner source, Ground[][] in, ArrayList<Writ> out, StringBuilder build) {
        String shortName = source.next().replace("_", " ");
        int tiles = source.nextInt();
        
        Writ w = new Writ(shortName);
        
        if (tiles==0) {
            for (int i=0; i<out.size(); i++) {
                if (w.name.equals(out.get(i).name)) {
                    out.remove(i);
                    return;
                }
            }
            return;
        }
        
        for (int i=0; i<tiles; i++) {
            int x = source.nextInt();
            int y = source.nextInt();
            w.tiles.add(in[x][y]);
        }
        
        for (Writ wCheck : out) {
            if (w.name.equals(wCheck.name)) {
                wCheck.tiles = w.tiles;
                return;
            }
        }
        out.add(w);
        
        if (build!=null) {
            SaveSystem.saveWrit(build, w);
        }
    }
    
    public static void readWritDeletion(Scanner source, Ground[][] in, ArrayList<Writ> out, StringBuilder build) {
        String shortName = source.next().replace("_", " ");
        
        for (int i=0; i<out.size(); i++) {
            Writ w = out.get(i);
            if (w.name.equals(shortName)) {
                out.remove(w);
                if (build!=null) {
                    SaveSystem.saveWritDeletion(build, shortName);
                }
                break;
            }
        }
    }
    
    private static Structure getLightweightStructure(String shortName) {
        for (Structure s : D.objects) {
            if (s.shortName.equals(shortName)) {
                Structure data = s.copy();
                return data;
            }
        }
        return null;
    }
    
    private static Data getLightweightData(String shortName) {
        for (Data d : D.models) {
            if (d.shortName.equals(shortName)) {
                return d.copy();
            }
        }
        return null;
    }
    
     private static Ground getGround(String shortName, int x, int y) {
        for (Ground g : D.grounds) {
            if (g.shortName.equals(shortName)) {
                return g.copy(x, y);
            }
        }
        return null;
    }
    
    private static Ground getCaveGround(String shortName, int x, int y) {
        for (Ground g : D.caveGrounds) {
            if (g.shortName.equals(shortName)) {
                return g.copy(x, y);
            }
        }
        return null;
    }
    
}
