package Form;

import Mapper.Logic.HeightUpdater;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import org.lwjgl.util.Point;

public class SlopeInput extends javax.swing.JDialog {
    
    public static boolean north = true;
    public static boolean east = true;
    public static int slopeInput = 0;
    
    public static Point p1;
    public static Point p2;
    
    public SlopeInput(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        if (north) {
            snRadio.setSelected(true);
        }
        else {
            nsRadio.setSelected(true);
        }
        
        if (east) {
            weRadio.setSelected(true);
        }
        else {
            ewRadio.setSelected(true);
        }
        
        JTextField textField = ((JSpinner.DefaultEditor) inputField.getEditor()).getTextField();
        textField.setText("");
        
        textField.addKeyListener(new KeyAdapter() {
  
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_0:
                        slopeInput = 0;
                        break;
                    case KeyEvent.VK_1:
                        break;
                    case KeyEvent.VK_2:
                        break;
                    case KeyEvent.VK_3:
                        break;
                    case KeyEvent.VK_4:
                        break;
                    case KeyEvent.VK_5:
                        break;
                    case KeyEvent.VK_6:
                        break;
                    case KeyEvent.VK_7:
                        break;
                    case KeyEvent.VK_8:
                        break;
                    case KeyEvent.VK_9:
                        break;
                    case KeyEvent.VK_ESCAPE:
                        break;
                    case KeyEvent.VK_DELETE:
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        break;
                    case KeyEvent.VK_MINUS:
                        break;
                    case KeyEvent.VK_NUMPAD0:
                        slopeInput = 0;
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        break;
                    case KeyEvent.VK_NUMPAD3:
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        break;
                    case KeyEvent.VK_NUMPAD5:
                        break;
                    case KeyEvent.VK_NUMPAD6:
                        break;
                    case KeyEvent.VK_NUMPAD7:
                        break;
                    case KeyEvent.VK_NUMPAD8:
                        break;
                    case KeyEvent.VK_NUMPAD9:
                        break;
                    case 109:
                        break;
                    default:
                        dispose();
                        break;
                }
                
                
            }
            
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        snRadio = new javax.swing.JRadioButton();
        nsRadio = new javax.swing.JRadioButton();
        ewRadio = new javax.swing.JRadioButton();
        weRadio = new javax.swing.JRadioButton();
        inputField = new javax.swing.JSpinner();
        helpLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Slope edit");
        setAlwaysOnTop(true);
        setModal(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        buttonGroup1.add(snRadio);
        snRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        snRadio.setText("S>N");
        snRadio.setFocusable(false);
        snRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(nsRadio);
        nsRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nsRadio.setText("N>S");
        nsRadio.setFocusable(false);
        nsRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsRadioActionPerformed(evt);
            }
        });

        buttonGroup2.add(ewRadio);
        ewRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ewRadio.setText("E>W");
        ewRadio.setFocusable(false);
        ewRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ewRadioActionPerformed(evt);
            }
        });

        buttonGroup2.add(weRadio);
        weRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        weRadio.setText("W>E");
        weRadio.setFocusable(false);
        weRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weRadioActionPerformed(evt);
            }
        });

        inputField.setModel(new javax.swing.SpinnerNumberModel(0, -500, 500, 1));
        inputField.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                inputFieldStateChanged(evt);
            }
        });

        helpLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        helpLabel.setText("<html>Exit with any key except<br/>\n0-9, Esc and Delete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputField)
                    .addComponent(helpLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weRadio)
                            .addComponent(snRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nsRadio)
                            .addComponent(ewRadio))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(snRadio)
                    .addComponent(nsRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weRadio)
                    .addComponent(ewRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(helpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void snRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snRadioActionPerformed
        north = true;
    }//GEN-LAST:event_snRadioActionPerformed

    private void nsRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsRadioActionPerformed
        north = false;
    }//GEN-LAST:event_nsRadioActionPerformed

    private void weRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weRadioActionPerformed
        east = true;
    }//GEN-LAST:event_weRadioActionPerformed

    private void ewRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ewRadioActionPerformed
        east = false;
    }//GEN-LAST:event_ewRadioActionPerformed

    private void inputFieldStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_inputFieldStateChanged
        slopeInput = (int) inputField.getModel().getValue();
    }//GEN-LAST:event_inputFieldStateChanged

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        if (evt.getWheelRotation()<0) {
            inputField.getModel().setValue((int) inputField.getModel().getValue()+1);
        }
        else {
            inputField.getModel().setValue((int) inputField.getModel().getValue()-1);
        }
    }//GEN-LAST:event_formMouseWheelMoved

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (p1.getX()==p2.getX()) {
            if (north) {
                HeightUpdater.setHeight(p1, HeightUpdater.getHeight(p2)+slopeInput);
            }
            else {
                HeightUpdater.setHeight(p2, HeightUpdater.getHeight(p1)+slopeInput);
            }
        }
        else {
            if (east) {
                HeightUpdater.setHeight(p1, HeightUpdater.getHeight(p2)+slopeInput);
            }
            else {
                HeightUpdater.setHeight(p2, HeightUpdater.getHeight(p1)+slopeInput);
            }
        }
        
        HeightUpdater.checkElevations();
    }//GEN-LAST:event_formWindowClosed
   
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SlopeInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SlopeInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SlopeInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SlopeInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SlopeInput dialog = new SlopeInput(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton ewRadio;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JSpinner inputField;
    private javax.swing.JRadioButton nsRadio;
    private javax.swing.JRadioButton snRadio;
    private javax.swing.JRadioButton weRadio;
    // End of variables declaration//GEN-END:variables
}
