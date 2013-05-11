package Lib.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadObject {
    
    public static ObjectData read(BufferedReader source) throws IOException {
        ObjectData data = new ObjectData();
        String line;
        ArrayList<Float> x = new ArrayList();
        ArrayList<Float> y = new ArrayList();
        ArrayList<Float> z = new ArrayList();
        ArrayList<Float> u = new ArrayList();
        ArrayList<Float> v = new ArrayList();
        ArrayList<Integer> vert = new ArrayList();
        ArrayList<Integer> coord = new ArrayList();
        while ((line=source.readLine())!=null) {
            if (line.startsWith("vt")) {
                line = line.substring(line.indexOf(" ")+1);
                u.add(Float.parseFloat(line.substring(0, line.indexOf(" "))));
                line = line.substring(line.indexOf(" ")+1);
                v.add(Float.parseFloat(line));
            }
            else if (line.startsWith("v")) {
                line = line.substring(line.indexOf(" ")+1);
                x.add(Float.parseFloat(line.substring(0, line.indexOf(" "))));
                line = line.substring(line.indexOf(" ")+1);
                y.add(Float.parseFloat(line.substring(0, line.indexOf(" "))));
                line = line.substring(line.indexOf(" ")+1);
                z.add(Float.parseFloat(line));
            }
            else if (line.startsWith("f")) {
                line = line.substring(line.indexOf(" ")+1);
                vert.add(Integer.parseInt(line.substring(0, line.indexOf("/")))-1);
                line = line.substring(line.indexOf("/")+1);
                coord.add(Integer.parseInt(line.substring(0, line.indexOf(" ")))-1);
                line = line.substring(line.indexOf(" ")+1);
                vert.add(Integer.parseInt(line.substring(0, line.indexOf("/")))-1);
                line = line.substring(line.indexOf("/")+1);
                coord.add(Integer.parseInt(line.substring(0, line.indexOf(" ")))-1);
                line = line.substring(line.indexOf(" ")+1);
                vert.add(Integer.parseInt(line.substring(0, line.indexOf("/")))-1);
                line = line.substring(line.indexOf("/")+1);
                coord.add(Integer.parseInt(line)-1);
            }
        }
        
        data.x = copyFloatList(x);
        data.y = copyFloatList(y);
        data.z = copyFloatList(z);
        data.texU = copyFloatList(u);
        data.texV = copyFloatList(v);
        data.vert = copyIntList(vert);
        data.coord = copyIntList(coord);
        data.size = vert.size();
        return data;
    }
    
    private static float[] copyFloatList(ArrayList<Float> list) {
        float[] array = new float[list.size()];
        for (int i=0; i<list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    private static int[] copyIntList(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i=0; i<list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    
}
