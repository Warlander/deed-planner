package Server;

import Entities.Data;
import Entities.Ground;
import Entities.Structure;
import Entities.Type;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    
    private static String spaceMax(String temp) {
        if (temp.indexOf(" ")>=0) {
            return temp.substring(0, temp.indexOf(" "));
        }
        return temp.substring(0, temp.length());
    }
    
    protected static void load() throws FileNotFoundException, IOException {
        String slash = System.getProperty("file.separator");
        
        File list = new File("Data"+slash+"Objects.txt");
        if (list.exists()) {
            preloader(list);
        }
        else {
            System.exit(1);
        }
    }
    
    private static void preloader(File list) throws FileNotFoundException, IOException {
        String temp;
        String name;
        String shortName;
        int tid;
        int mid;
        int matid;
        Data data;
        Structure structure;
        Ground ground;
        
        BufferedReader reader = new BufferedReader(new FileReader(list));
        String line;
        while ((line=reader.readLine())!=null) {
            if (!line.equals("")) {
                String request = line.substring(0, line.indexOf(" "));
                switch (request) {
                    case "Wall":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("mid=")+4);
                        mid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("matid=")+6);
                        matid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("r=")+2);
                        double r = Double.parseDouble(spaceMax(temp));
                        temp = line.substring(line.indexOf("g=")+2);
                        double g = Double.parseDouble(spaceMax(temp));
                        temp = line.substring(line.indexOf("b=")+2);
                        double b = Double.parseDouble(spaceMax(temp));
                        temp = line.substring(line.indexOf("mult=")+5);
                        float rendMult = Float.parseFloat(spaceMax(temp));
                        data = new Data(name, shortName, Type.wall);
                        D.models.add(data);
                        break;
                    case "Floor":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("mid=")+4);
                        mid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("matid=")+6);
                        matid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        data = new Data(name, shortName, Type.floor);
                        D.models.add(data);
                        break;
                    case "Roof":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("matid=")+6);
                        matid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        data = new Data(name, shortName, Type.roof);
                        D.models.add(data);
                        break;
                    case "Ground":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        ground = new Ground(name, shortName);
                        D.grounds.add(ground);
                        break;
                    case "Cave":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        ground = new Ground(name, shortName);
                        D.caveGrounds.add(ground);
                        break;
                    case "Object":
                        temp = line.substring(line.indexOf("tid=")+4);
                        tid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("mid=")+4);
                        mid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("matid=")+6);
                        matid = Integer.parseInt(spaceMax(temp));
                        temp = line.substring(line.indexOf("name=\"")+6);
                        name = temp.substring(0, temp.indexOf("\""));
                        temp = line.substring(line.indexOf("shortname=\"")+11);
                        shortName = temp.substring(0, temp.indexOf("\""));
                        structure = new Structure(name, shortName);
                        D.objects.add(structure);
                        break;
                }
            }
            
        }
    }
    
}
