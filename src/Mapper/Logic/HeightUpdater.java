package Mapper.Logic;

import Form.HouseCalc;
import Form.SlopeInput;
import Lib.Files.Properties;
import Mapper.Input.Keybindings;
import Mapper.Mapper;
import Mapper.Input.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

public final class HeightUpdater {
    
    private static Point p2=null;
    
    static void update() {
        if (Keybindings.hold(Keybindings.UP_SPEED_MOD1) && (MouseInput.scrollDown ^ MouseInput.scrollUp)) {
            if (MouseInput.scrollDown) {
                if (HouseCalc.elevationAdd>1) {
                    HouseCalc.elevationAdd--;
                }
            }
            else {
                if (HouseCalc.elevationAdd<1000) {
                    HouseCalc.elevationAdd++;
                }
            }
            HouseCalc.addSpinner.getModel().setValue(HouseCalc.elevationAdd);
        }
        if (Keybindings.hold(Keybindings.UP_SPEED_MOD2) && (MouseInput.scrollDown ^ MouseInput.scrollUp)) {
            if (MouseInput.scrollDown) {
                HouseCalc.elevationSetLeft--;
            }
            else {
                HouseCalc.elevationSetLeft++;
            }
            HouseCalc.setLeftSpinner.getModel().setValue(HouseCalc.elevationSetLeft);
        }
        
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)Properties.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)Properties.scale);

        int tileX = (int) (((double)MouseInput.x+(double)UpCamera.x*realSize)/((double)Display.getWidth()/(double)Properties.scale/tileScaler))+1;
        int tileY = (int) (((double)MouseInput.y+(double)UpCamera.y*realSize)/((double)Display.getHeight()/(double)Properties.scale));

        if (tileX<0 || tileY<0 || tileX>Mapper.width || tileY>Mapper.height) {
            return;
        }

        double x = MouseInput.x+UpCamera.x*realSize;
        double y = MouseInput.y+UpCamera.y*realSize;

        double xClip = x%tileSize;
        double yClip = y%tileSize;
        
        Point[] points = selectedPoint(tileX, tileY, xClip, yClip, tileSize);
        
        if (points!=null) {
            HouseCalc.statusBar.setPoint(points[0]);
        }
        
        if (MouseInput.hold.left ^ MouseInput.hold.right) {
            if (points==null) {
                points = selectedBorder(tileX, tileY, xClip, yClip, tileSize);
            }
            if (points==null) {
                points = selectedTile(tileX, tileY);
            }
            if (points==null) {
                return;
            }
            
            for (Point point : points) {
                if (!containsWrit(point)) {
                    switch ((String)HouseCalc.elevationList.getSelectedValue()) {
                        case "Increase/decrease height":
                            if (MouseInput.pressed.left ^ MouseInput.pressed.right) {
                                increaseHeight(point);
                            }
                            break;
                        case "Increase/decrease area height":
                            if (MouseInput.pressed.left ^ MouseInput.pressed.right) {
                                liftArea(point);
                            }
                            break;
                        case "Set height":
                            setHeight(point);
                            break;
                        case "Select height":
                            if (MouseInput.pressed.left ^ MouseInput.pressed.right) {
                                selectHeight(point);
                            }
                            break;
                        case "Reset height":
                            resetHeight(point);
                            break;
                        case "Smooth height":
                            if ((MouseInput.pressed.left ^ MouseInput.pressed.right) && points.length==1) {
                                smoothHeight(point);
                            }
                            break;
                        case "Level area":
                            if ((MouseInput.pressed.left ^ MouseInput.pressed.right) && points.length==1) {
                                levelArea(point);
                            }
                            break;
                        case "Edit slope":
                            if ((MouseInput.pressed.left) && points.length==2) {
                                editSlope(point);
                            }
                    }
                }
            }
            checkElevations();
        }
    }
    
    private static boolean containsWrit(Point point) {
        if (point.getX()<Mapper.width && point.getY()<Mapper.height && Mapper.ground[point.getX()][point.getY()].writ!=null) {return true;}
        else if (point.getX()-1>=0 && point.getY()<Mapper.height && Mapper.ground[point.getX()-1][point.getY()].writ!=null) {return true;}
        else if (point.getX()-1>=0 && point.getY()-1>=0 && Mapper.ground[point.getX()-1][point.getY()-1].writ!=null) {return true;}
        else if (point.getY()-1>=0 && point.getX()<Mapper.width && Mapper.ground[point.getX()][point.getY()-1].writ!=null) {return true;}
        return false;
    }
    
    static Point[] selectedPoint(int tileX, int tileY, double xClip, double yClip, double tileSize) {
        Point point = new Point();
        if (xClip<(tileSize/6) && yClip<(tileSize/6)) {
            point.setX(tileX);
            point.setY(tileY);
        }
        else if (xClip>(tileSize-tileSize/6) && yClip<(tileSize/6)) {
            point.setX(tileX+1);
            point.setY(tileY);
        }
        else if (xClip<(tileSize/6) && yClip>(tileSize-tileSize/6)) {
            point.setX(tileX);
            point.setY(tileY+1);
        }
        else if (xClip>(tileSize-tileSize/6) && yClip>(tileSize-tileSize/6)) {
            point.setX(tileX+1);
            point.setY(tileY+1);
        }
        else {
            return null;
        }
        if (point.getX()<0 || point.getY()<0 || point.getY()>Mapper.width || point.getY()>Mapper.height) {
            return null;
        }
        point.setX(point.getX()-1);
        return new Point[]{point};
    }
    
    private static Point[] selectedBorder(int tileX, int tileY, double xClip, double yClip, double tileSize) {
        Point p1 = new Point();
        Point p2 = new Point();
        if (yClip<(tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
            p1.setX(tileX-1); p1.setY(tileY);
            p2.setX(tileX);   p2.setY(tileY);
        }
        else if (yClip>(tileSize-tileSize/6) && xClip>(tileSize/6) && xClip<(tileSize-tileSize/6)) {
            p1.setX(tileX-1); p1.setY(tileY+1);
            p2.setX(tileX);   p2.setY(tileY+1);
        }
        else if (xClip<(tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
            p1.setX(tileX-1); p1.setY(tileY);
            p2.setX(tileX-1); p2.setY(tileY+1);
        }
        else if (xClip>(tileSize-tileSize/6) && yClip>(tileSize/6) && yClip<(tileSize-tileSize/6)) {
            p1.setX(tileX);   p1.setY(tileY);
            p2.setX(tileX);   p2.setY(tileY+1);
        }
        else {
            return null;
        }
        if (p1.getX()<0 || p1.getY()<0 || p1.getY()>Mapper.width || p1.getY()>Mapper.height || p2.getX()<0 || p2.getY()<0 || p2.getY()>Mapper.width || p2.getY()>Mapper.height) {
            return null;
        }
        return new Point[]{p1,p2};
    }
    
    private static Point[] selectedTile(int tileX, int tileY) {
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        Point p4 = new Point();
        p1.setX(tileX-1);   p1.setY(tileY);
        p2.setX(tileX-1);   p2.setY(tileY+1);
        p3.setX(tileX);     p3.setY(tileY);
        p4.setX(tileX);     p4.setY(tileY+1);
        
        Point[] points = new Point[]{p1,p2,p3,p4};
        for (Point point : points) {
            if (point.getX()<0 || point.getY()<0 || point.getX()>Mapper.width || point.getY()>Mapper.height) {
                return null;
            }
        }
        return points;
    }
    
    private static void increaseHeight(Point point) {
        if (MouseInput.pressed.left) {
            Mapper.heightmap[point.getX()][point.getY()]+=HouseCalc.elevationAdd;
        }
        else {
            Mapper.heightmap[point.getX()][point.getY()]-=HouseCalc.elevationAdd;
        }
    }
    
    private static void setHeight(Point point) {
        if (MouseInput.hold.left) {
            Mapper.heightmap[point.getX()][point.getY()]=HouseCalc.elevationSetLeft;
        }
        else {
            Mapper.heightmap[point.getX()][point.getY()]=HouseCalc.elevationSetRight;
        }
    }
    
    private static void selectHeight(Point point) {
        if (MouseInput.pressed.left) {
            HouseCalc.elevationSetLeft = (int)Mapper.heightmap[point.getX()][point.getY()];
            HouseCalc.setLeftSpinner.setValue((int)Mapper.heightmap[point.getX()][point.getY()]);
        }
        else {
            HouseCalc.elevationSetRight = (int)Mapper.heightmap[point.getX()][point.getY()];
            HouseCalc.setRightSpinner.setValue((int)Mapper.heightmap[point.getX()][point.getY()]);
        }
    }
    
    private static void resetHeight(Point point) {
        Mapper.heightmap[point.getX()][point.getY()]=0;
    }
    
    private static void smoothHeight(Point p1) {
        if (p2==null) {
            p2 = p1;
        }
        else {
            if (p1.getX()==p2.getX() ^ p1.getY()==p2.getY()) {
                float min = Math.min(getHeight(p1), getHeight(p2));
                float max = Math.max(getHeight(p1), getHeight(p2));
                float diff = max-min;
                Point higher = getPoint(max, p1, p2);
                Point lower = getPoint(min, p1, p2);
                
                int mult = 0;
                
                if (p1.getX()==p2.getX()) {
                    diff/=Math.abs(higher.getY()-lower.getY());
                    if (higher.getY()>lower.getY()) {
                        for (int i=lower.getY(); i<higher.getY(); i++) {
                            Mapper.heightmap[p1.getX()][i] = min + diff*mult;
                            mult++;
                        }
                    }
                    else if (lower.getY()>higher.getY()) {
                        for (int i=lower.getY(); i>higher.getY(); i--) {
                            Mapper.heightmap[p1.getX()][i] = min + diff*mult;
                            mult++;
                        }
                    }
                }
                else if (p1.getY()==p2.getY()) {
                    diff/=Math.abs(higher.getX()-lower.getX());
                    if (higher.getX()>lower.getX()) {
                        for (int i=lower.getX(); i<higher.getX(); i++) {
                            Mapper.heightmap[i][p1.getY()] = min + diff*mult;
                            mult++;
                        }
                    }
                    else if (lower.getX()>higher.getX()) {
                        for (int i=lower.getX(); i>higher.getX(); i--) {
                            Mapper.heightmap[i][p1.getY()] = min + diff*mult;
                            mult++;
                        }
                    }
                }
            }
            p2=null;
        }
    }
    
    private static void levelArea(Point p1) {
        if (p2==null) {
            p2 = p1;
        }
        else {
            int minX = Math.min(p1.getX(), p2.getX());
            int maxX = Math.max(p1.getX(), p2.getX());
            int minY = Math.min(p1.getY(), p2.getY());
            int maxY = Math.max(p1.getY(), p2.getY());
            
            int set;
            if (MouseInput.pressed.left) {
                set = HouseCalc.elevationSetLeft;
            }
            else {
                set = HouseCalc.elevationSetRight;
            }
            
            for (int i=minX; i<=maxX; i++) {
                for (int i2=minY; i2<=maxY; i2++) {
                    Mapper.heightmap[i][i2] = set;
                }
            }
            
            p2=null;
        }
    }
    
    private static void liftArea(Point p1) {
        if (p2==null) {
            p2 = p1;
        }
        else {
            int minX = Math.min(p1.getX(), p2.getX());
            int maxX = Math.max(p1.getX(), p2.getX());
            int minY = Math.min(p1.getY(), p2.getY());
            int maxY = Math.max(p1.getY(), p2.getY());
            
            int add;
            if (MouseInput.pressed.left) {
                add = HouseCalc.elevationAdd;
            }
            else {
                add = -HouseCalc.elevationAdd;
            }
            
            for (int i=minX; i<=maxX; i++) {
                for (int i2=minY; i2<=maxY; i2++) {
                    Mapper.heightmap[i][i2]+=add;
                }
            }
            
            p2=null;
        }
    }
    
    private static void editSlope(Point p1) {
        if (p2==null) {
            p2 = p1;
        }
        else {
            SlopeInput.p1 = p1;
            SlopeInput.p2 = p2;
            SlopeInput.main(null);
            
            p2=null;
        }
    }
    
    public static void setHeight(Point p, float height) {
        Mapper.heightmap[p.getX()][p.getY()] = height;
    }
    
    public static float getHeight(Point p) {
        return Mapper.heightmap[p.getX()][p.getY()];
    }
    
    private static Point getPoint(float height, Point p1, Point p2) {
        if (Mapper.heightmap[p1.getX()][p1.getY()]==height) {
            return p1;
        }
        return p2;
    }
    
    public static void checkElevations() {
        float elevationMin = Mapper.maxElevation;
        for (int i=0; i<=Mapper.width; i++) {
            for (int i2=0; i2<=Mapper.height; i2++) {
                elevationMin = Math.min(elevationMin, Mapper.heightmap[i][i2]);
            }
        }
        Mapper.minElevation=elevationMin;
        
        float elevationMax = Mapper.minElevation;
        for (int i=0; i<=Mapper.width; i++) {
            for (int i2=0; i2<=Mapper.height; i2++) {
                elevationMax = Math.max(elevationMax, Mapper.heightmap[i][i2]);
            }
        }
        Mapper.maxElevation=elevationMax;
        Mapper.diffElevation = Mapper.maxElevation - Mapper.minElevation;
    }
    
}
