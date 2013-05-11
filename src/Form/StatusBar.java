package Form;

import Lib.Graphics.Ground;
import Lib.Object.Data;
import org.lwjgl.input.Mouse;

public class StatusBar extends javax.swing.JPanel {

    public Ground ground;
    public Data data;
    
    public StatusBar() {
        initComponents();
        errorLabel.setVisible(false);
        xLabel.setVisible(false);
        yLabel.setVisible(false);
        writName.setVisible(false);
        objectName.setVisible(false);
    }
    
    public void setGround(Ground ground) {
        this.ground = ground;
    }
    
    public void setData(Data data) {
        this.data = data;
    }
    
    public void display() {
        if (!Mouse.isGrabbed()) {
            if (ground!=null && !Mapper.Mapper.fpsView) {
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
            if (data!=null && !Mapper.Mapper.fpsView) {
                objectName.setText(data.name);
                objectName.setVisible(true);
            }
            else {
                objectName.setVisible(false);
            }
        }
        
        data = null;
        ground = null;
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
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel errorLabel;
    private javax.swing.JLabel objectName;
    public static Form.TipLabel tipLabel1;
    private javax.swing.JLabel writName;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    // End of variables declaration//GEN-END:variables
}
