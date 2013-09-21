package Form;

import Lib.Graphics.Ground;
import Lib.Entities.Data;
import Mapper.Mapper;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Point;

public class StatusBar extends javax.swing.JPanel {

    private Ground ground;
    private Data data;
    private Point point;
    
    public StatusBar() {
        initComponents();
        errorLabel.setVisible(false);
        xLabel.setVisible(false);
        yLabel.setVisible(false);
        writName.setVisible(false);
        objectName.setVisible(false);
        elevationShow.setVisible(false);
        helpLabel.setText("");
    }
    
    public void setGround(Ground ground) {
        this.ground = ground;
    }
    
    public void setData(Data data) {
        this.data = data;
    }
    
    public void setPoint(Point point) {
        this.point = point;
    }
    
    public void display() {
        if (!Mouse.isGrabbed()) {
            if (ground!=null && !Mapper.fpsView) {
                xLabel.setText("X "+ground.x);
                yLabel.setText("Y "+ground.y);
                xLabel.setVisible(true);
                yLabel.setVisible(true);
                if (ground.writ!=null) {
                    writName.setText(ground.writ.name);
                    writName.setVisible(true);
                }
                else {
                    writName.setVisible(false);
                }
            }
            else {
                xLabel.setVisible(false);
                yLabel.setVisible(false);
                writName.setVisible(false);
            }
            if (data!=null && !Mapper.fpsView) {
                objectName.setText(data.name);
                objectName.setVisible(true);
            }
            else {
                objectName.setVisible(false);
            }
            if (point!=null && !Mapper.fpsView && point.getX()>0 && point.getX()<Mapper.width  && point.getY()>0 && point.getY()<Mapper.height) {
                elevationShow.setText("Elevation: "+(int)Mapper.heightmap[point.getX()][point.getY()]);
                elevationShow.setVisible(true);
                HouseCalc.centerHeight.setText(Integer.toString((int)Mapper.heightmap[point.getX()][point.getY()]));
                HouseCalc.centerHeight.setVisible(true);
                if (checkCorrectness(point.getX(), point.getY()+1)) {
                    HouseCalc.upHeight.setText(Integer.toString((int)Mapper.heightmap[point.getX()][point.getY()+1]-(int)Mapper.heightmap[point.getX()][point.getY()]));
                    HouseCalc.upHeight.setVisible(true);
                }
                else {
                    HouseCalc.upHeight.setVisible(false);
                }
                if (checkCorrectness(point.getX(), point.getY()-1)) {
                    HouseCalc.downHeight.setText(Integer.toString((int)Mapper.heightmap[point.getX()][point.getY()-1]-(int)Mapper.heightmap[point.getX()][point.getY()]));
                    HouseCalc.downHeight.setVisible(true);
                }
                else {
                    HouseCalc.downHeight.setVisible(false);
                }
                if (checkCorrectness(point.getX()-1, point.getY())) {
                    HouseCalc.leftHeight.setText(Integer.toString((int)Mapper.heightmap[point.getX()-1][point.getY()]-(int)Mapper.heightmap[point.getX()][point.getY()]));
                    HouseCalc.leftHeight.setVisible(true);
                }
                else {
                    HouseCalc.leftHeight.setVisible(false);
                }
                if (checkCorrectness(point.getX()+1, point.getY())) {
                    HouseCalc.rightHeight.setText(Integer.toString((int)Mapper.heightmap[point.getX()+1][point.getY()]-(int)Mapper.heightmap[point.getX()][point.getY()]));
                    HouseCalc.rightHeight.setVisible(true);
                }
                else {
                    HouseCalc.rightHeight.setVisible(false);
                }
            }
            else {
                elevationShow.setVisible(false);
                HouseCalc.centerHeight.setVisible(false);
                HouseCalc.upHeight.setVisible(false);
                HouseCalc.downHeight.setVisible(false);
                HouseCalc.leftHeight.setVisible(false);
                HouseCalc.rightHeight.setVisible(false);
            }
        }
        
        data = null;
        ground = null;
        point = null;
    }
    
    private boolean checkCorrectness(int x, int y) {
        if (x<0 || y<0 || x>Mapper.width || y>Mapper.height) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipLabel1 = new Form.TipLabel();
        errorLabel = new javax.swing.JLabel();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        writName = new javax.swing.JLabel();
        objectName = new javax.swing.JLabel();
        elevationShow = new javax.swing.JLabel();
        helpLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(32767, 38));
        setMinimumSize(new java.awt.Dimension(800, 38));
        setPreferredSize(new java.awt.Dimension(800, 38));
        setLayout(null);

        tipLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        add(tipLabel1);
        tipLabel1.setBounds(0, 2, 350, 15);

        errorLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText("Error has occurred - please see error log for more details");
        add(errorLabel);
        errorLabel.setBounds(0, 23, 350, 15);

        xLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        xLabel.setText("X 1000");
        add(xLabel);
        xLabel.setBounds(351, 2, 50, 15);

        yLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        yLabel.setText("Y 1000");
        add(yLabel);
        yLabel.setBounds(351, 23, 50, 15);

        writName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        writName.setText("Y 1000");
        add(writName);
        writName.setBounds(401, 23, 150, 15);

        objectName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        objectName.setText("X 1000");
        add(objectName);
        objectName.setBounds(401, 2, 150, 15);

        elevationShow.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        elevationShow.setText("X 1000");
        add(elevationShow);
        elevationShow.setBounds(561, 2, 110, 15);

        helpLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        helpLabel.setText("Help label");
        add(helpLabel);
        helpLabel.setBounds(561, 23, 120, 15);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel elevationShow;
    public static javax.swing.JLabel errorLabel;
    public javax.swing.JLabel helpLabel;
    private javax.swing.JLabel objectName;
    public static Form.TipLabel tipLabel1;
    private javax.swing.JLabel writName;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    // End of variables declaration//GEN-END:variables
}
