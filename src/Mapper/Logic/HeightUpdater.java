package Mapper.Logic;

import Form.HouseCalc;
import Mapper.KeyboardInput;
import Mapper.Mapper;
import Mapper.MouseInput;
import Mapper.UpCamera;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

public class HeightUpdater {
    
    protected void update(KeyboardInput keyboard, MouseInput mouse) {
        if (keyboard.hold[Keyboard.KEY_LSHIFT] && (mouse.scrollDown ^ mouse.scrollUp)) {
            if (mouse.scrollDown) {
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
        if (keyboard.hold[Keyboard.KEY_LCONTROL] && (mouse.scrollDown ^ mouse.scrollUp)) {
            if (mouse.scrollDown) {
                HouseCalc.elevationSetLeft--;
            }
            else {
                HouseCalc.elevationSetLeft++;
            }
            HouseCalc.setLeftSpinner.getModel().setValue(HouseCalc.elevationSetLeft);
        }
        
        double tileScaler = (double)Display.getWidth()/(double)Display.getHeight();
        double realSize = (double)Display.getHeight()/(double)UpCamera.scale/4d;
        double tileSize = ((double)Display.getHeight()/(double)UpCamera.scale);

        int tileX = (int) (((double)mouse.x+(double)UpCamera.y*realSize)/((double)Display.getWidth()/(double)UpCamera.scale/tileScaler))+1;
        int tileY = (int) (((double)mouse.y+(double)UpCamera.x*realSize)/((double)Display.getHeight()/(double)UpCamera.scale));

        if (tileX<0 || tileY<0 || tileX>Mapper.width || tileY>Mapper.height) {
            return;
        }

        double x = mouse.x+UpCamera.y*realSize;
        double y = mouse.y+UpCamera.x*realSize;

        double xClip = x%tileSize;
        double yClip = y%tileSize;
        
        Point[] points = selectedPoint(tileX, tileY, xClip, yClip, tileSize);
        
        if (points!=null) {
            HouseCalc.statusBar.setPoint(points[0]);
        }
        
        if (mouse.pressed.left ^ mouse.pressed.right) {
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
                        case "Increase height":
                            increaseHeight(mouse, point);
                            break;
                        case "Decrease height":
                            decreaseHeight(mouse, point);
                            break;
                        case "Set height":
                            setHeight(mouse, point);
                            break;
                        case "Select height":
                            selectHeight(mouse, point);
                            break;
                        case "Reset height":
                            resetHeight(point);
                            break;
                    }
                }
            }
            checkElevations();
        }
    }
    
    private boolean containsWrit(Point point) {
        if (Mapper.ground[point.getX()][point.getY()].writ!=null) {return true;}
        else if (Mapper.ground[point.getX()-1][point.getY()].writ!=null) {return true;}
        else if (Mapper.ground[point.getX()-1][point.getY()-1].writ!=null) {return true;}
        else if (Mapper.ground[point.getX()][point.getY()-1].writ!=null) {return true;}
        return false;
    }
    
    protected Point[] selectedPoint(int tileX, int tileY, double xClip, double yClip, double tileSize) {
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
    
    private Point[] selectedBorder(int tileX, int tileY, double xClip, double yClip, double tileSize) {
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
    
    private Point[] selectedTile(int tileX, int tileY) {
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
            if (point.getX()<0 || point.getY()<0 || point.getY()>Mapper.width || point.getY()>Mapper.height) {
                return null;
            }
        }
        return points;
    }
    
    private void increaseHeight(MouseInput mouse, Point point) {
        if (mouse.pressed.left) {
            Mapper.heightmap[point.getX()][point.getY()]+=HouseCalc.elevationAdd;
        }
        else {
            Mapper.heightmap[point.getX()][point.getY()]-=HouseCalc.elevationAdd;
        }
    }
    
    private void decreaseHeight(MouseInput mouse, Point point) {
        if (mouse.pressed.left) {
            Mapper.heightmap[point.getX()][point.getY()]-=HouseCalc.elevationAdd;
        }
        else {
            Mapper.heightmap[point.getX()][point.getY()]+=HouseCalc.elevationAdd;
        }
    }
    
    private void setHeight(MouseInput mouse, Point point) {
        if (mouse.pressed.left) {
            Mapper.heightmap[point.getX()][point.getY()]=HouseCalc.elevationSetLeft;
        }
        else {
            Mapper.heightmap[point.getX()][point.getY()]=HouseCalc.elevationSetRight;
        }
    }
    
    private void selectHeight(MouseInput mouse, Point point) {
        if (mouse.pressed.left) {
            HouseCalc.elevationSetLeft = (int)Mapper.heightmap[point.getX()][point.getY()];
            HouseCalc.setLeftSpinner.setValue((int)Mapper.heightmap[point.getX()][point.getY()]);
        }
        else {
            HouseCalc.elevationSetRight = (int)Mapper.heightmap[point.getX()][point.getY()];
            HouseCalc.setRightSpinner.setValue((int)Mapper.heightmap[point.getX()][point.getY()]);
        }
    }
    
    private void resetHeight(Point point) {
        Mapper.heightmap[point.getX()][point.getY()]=0;
        
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