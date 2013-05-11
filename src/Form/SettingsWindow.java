package Form;

import Lib.Files.Properties;
import Mapper.FPPCamera;
import Mapper.UpCamera;

public class SettingsWindow extends javax.swing.JFrame {

    public SettingsWindow() {
        initComponents();
        getProperties();
    }
    
    private void getProperties() {
        Properties.loadProperties();
        if (Properties.getProperty("javaCompile")!=null) {
            forceJavaCompileBox.setSelected((boolean)Properties.getProperty("javaCompile"));
        }
        if (Properties.getProperty("checkVersion")!=null) {
            checkUpdatesBox.setSelected((boolean)Properties.getProperty("checkVersion"));
        }
        
        if (Properties.getProperty("allowWheel")!=null) {
            wheelScaleBox.setSelected((boolean)Properties.getProperty("allowWheel"));
        }
        if (Properties.getProperty("keyboardFractionUp")!=null) {
            keyboardFractionUpSelect.getModel().setValue((float)Properties.getProperty("keyboardFractionUp"));
        }
        if (Properties.getProperty("mouseFractionUp")!=null) {
            mouseFractionUpSelect.getModel().setValue((float)Properties.getProperty("mouseFractionUp"));
        }
        if (Properties.getProperty("showGrid")!=null) {
            showGridBox.setSelected((boolean)Properties.getProperty("showGrid"));
        }
        if (Properties.getProperty("scale")!=null) {
            String propString = Properties.getProperty("scale").toString();
            scaleUpSelect.getModel().setValue(Integer.parseInt(propString.substring(0, propString.indexOf("."))));
        }
        
        if (Properties.getProperty("mouseFractionFpp")!=null) {
            mouseFractionFppSelect.getModel().setValue((float)Properties.getProperty("mouseFractionFpp"));
        }
        if (Properties.getProperty("cameraRotationFpp")!=null) {
            cameraRotationFppSelect.getModel().setValue((float)Properties.getProperty("cameraRotationFpp"));
        }
        if (Properties.getProperty("mod1Fpp")!=null) {
            mod1FppSelect.getModel().setValue((float)Properties.getProperty("mod1Fpp"));
        }
        if (Properties.getProperty("mod2Fpp")!=null) {
            mod2FppSelect.getModel().setValue((float)Properties.getProperty("mod2Fpp"));
        }
        
        if (Properties.getProperty("antialiasing")!=null) {
            for (int i=0; i<antialiasingCombo.getItemCount(); i++) {
                int integer = Integer.parseInt((String)antialiasingCombo.getItemAt(i));
                if (integer==(int)((float)Properties.getProperty("antialiasing"))) {
                    antialiasingCombo.setSelectedIndex(i);
                }
            }
        }
        
        if (Properties.getProperty("useMipmaps")!=null) {
            mipmapToggle.setSelected((boolean)Properties.getProperty("useMipmaps"));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        showGridBox = new javax.swing.JCheckBox();
        keyboardFractionUpSelect = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        mouseFractionUpSelect = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        mouseFractionFppSelect = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        cameraRotationFppSelect = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        scaleUpSelect = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        wheelScaleBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        mod1FppSelect = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        mod2FppSelect = new javax.swing.JSpinner();
        forceJavaCompileBox = new javax.swing.JCheckBox();
        checkUpdatesBox = new javax.swing.JCheckBox();
        antialiasingCombo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        mipmapToggle = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HouseCalc Settings");
        setResizable(false);

        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setText("Save all settings");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel1.setText("Settings are stored in \"Home_Directory/HouseCalc\"");

        showGridBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        showGridBox.setSelected(true);
        showGridBox.setText("Show grid (top view only)");

        keyboardFractionUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        keyboardFractionUpSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), Float.valueOf(0.01f)));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Scrolling speed in top view (keyboard)");

        mouseFractionUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mouseFractionUpSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.2f), Float.valueOf(0.05f), Float.valueOf(1.0f), Float.valueOf(0.01f)));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Scrolling speed in top view (mouse)");

        mouseFractionFppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mouseFractionFppSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.2f), Float.valueOf(0.1f), Float.valueOf(2.0f), Float.valueOf(0.01f)));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Move speed in FPP view");

        cameraRotationFppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cameraRotationFppSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.015f), Float.valueOf(0.001f), Float.valueOf(0.1f), Float.valueOf(0.001f)));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Camera sensitivity in FPP view");

        scaleUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        scaleUpSelect.setModel(new javax.swing.SpinnerNumberModel(10, 5, 25, 1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Top view scale (visible vertical tiles)");

        wheelScaleBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        wheelScaleBox.setText("Allow changing top view scale with mouse wheel");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Shift key speed multiplier");

        mod1FppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mod1FppSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(10.0f), Float.valueOf(0.1f)));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Control key speed multiplier");

        mod2FppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mod2FppSelect.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.2f), Float.valueOf(0.1f), Float.valueOf(10.0f), Float.valueOf(0.1f)));

        forceJavaCompileBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        forceJavaCompileBox.setText("<html>Force Java to compile program on run<br>(slows down startup, but gives performance boost<br>\nmay cause instability, so is disabled by default)");
        forceJavaCompileBox.setToolTipText("");

        checkUpdatesBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        checkUpdatesBox.setSelected(true);
        checkUpdatesBox.setText("Check for updates");

        antialiasingCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        antialiasingCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16", "8", "4", "2", "0" }));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Antialiasing");

        mipmapToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mipmapToggle.setSelected(true);
        mipmapToggle.setText("Mipmapping (better textures)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkUpdatesBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(keyboardFractionUpSelect)
                                    .addComponent(scaleUpSelect, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mouseFractionUpSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(forceJavaCompileBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wheelScaleBox)
                            .addComponent(jLabel1)
                            .addComponent(showGridBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cameraRotationFppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(mod2FppSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(mod1FppSelect)
                                    .addComponent(mouseFractionFppSelect))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(antialiasingCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mipmapToggle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showGridBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(keyboardFractionUpSelect)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mouseFractionUpSelect)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scaleUpSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wheelScaleBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mouseFractionFppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cameraRotationFppSelect)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mod1FppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mod2FppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(forceJavaCompileBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkUpdatesBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antialiasingCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mipmapToggle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Properties.setProperty("allowWheel", wheelScaleBox.isSelected());
        UpCamera.allowWheel=wheelScaleBox.isSelected();
        Properties.setProperty("showGrid", showGridBox.isSelected());
        UpCamera.showGrid=showGridBox.isSelected();
        Properties.setProperty("keyboardFractionUp", keyboardFractionUpSelect.getModel().getValue());
        UpCamera.keyboardFraction=(float)keyboardFractionUpSelect.getModel().getValue();
        Properties.setProperty("mouseFractionUp", mouseFractionUpSelect.getModel().getValue());
        UpCamera.mouseFraction=(float)mouseFractionUpSelect.getModel().getValue();
        Properties.setProperty("scale", scaleUpSelect.getModel().getValue());
        UpCamera.scale=(int)scaleUpSelect.getModel().getValue();
        
        Properties.setProperty("mouseFractionFpp", mouseFractionFppSelect.getModel().getValue());
        FPPCamera.fraction=(float)mouseFractionFppSelect.getModel().getValue();
        Properties.setProperty("cameraRotationFpp", cameraRotationFppSelect.getModel().getValue());
        FPPCamera.rotate=(float)cameraRotationFppSelect.getModel().getValue();
        Properties.setProperty("mod1Fpp", mod1FppSelect.getModel().getValue());
        FPPCamera.shiftMod=(float)mod1FppSelect.getModel().getValue();
        Properties.setProperty("mod2Fpp", mod2FppSelect.getModel().getValue());
        FPPCamera.controlMod=(float)mod2FppSelect.getModel().getValue();
        
        Properties.setProperty("javaCompile", forceJavaCompileBox.isSelected());
        Properties.setProperty("checkVersion", checkUpdatesBox.isSelected());
        
        Properties.setProperty("antialiasing", (Integer.parseInt((String)antialiasingCombo.getSelectedItem())));
        Properties.setProperty("useMipmaps", mipmapToggle.isSelected());
        
        Properties.saveProperties();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(SettingsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox antialiasingCombo;
    private javax.swing.JSpinner cameraRotationFppSelect;
    private javax.swing.JCheckBox checkUpdatesBox;
    private javax.swing.JCheckBox forceJavaCompileBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner keyboardFractionUpSelect;
    private javax.swing.JToggleButton mipmapToggle;
    private javax.swing.JSpinner mod1FppSelect;
    private javax.swing.JSpinner mod2FppSelect;
    private javax.swing.JSpinner mouseFractionFppSelect;
    private javax.swing.JSpinner mouseFractionUpSelect;
    private javax.swing.JSpinner scaleUpSelect;
    private javax.swing.JCheckBox showGridBox;
    private javax.swing.JCheckBox wheelScaleBox;
    // End of variables declaration//GEN-END:variables
}
