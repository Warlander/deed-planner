package Lib.Object;

import java.io.*;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class ReadObject {
    
    private ReadObject() {}
    
    /**
     * Method to load external models.
     * 
     * @param source BufferedReader from which you want to read data
     * @return ObjectData
     * @throws IOException when directory cannot be found
     */
    static int read(String file) throws IOException {
        BufferedReader source = new BufferedReader(new FileReader(file));
        
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
        
        int listID = GL11.glGenLists(1);
        
        GL11.glNewList(listID, GL11.GL_COMPILE);
            GL11.glBegin(GL11.GL_TRIANGLES);
                for (int i=0; i<vert.size(); i++) {
                    GL11.glTexCoord2f(u.get(coord.get(i)), 1-v.get(coord.get(i)));
                    GL11.glVertex3f(x.get(vert.get(i)), y.get(vert.get(i)), z.get(vert.get(i)));
                }
            GL11.glEnd();
        GL11.glEndList();
        
        System.out.println("Model at: "+file+" loaded.");
        return listID;
    }
    
}
