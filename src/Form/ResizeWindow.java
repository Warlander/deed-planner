package Form;

import Lib.Graphics.Ground;
import Lib.Entities.Data;
import Lib.Entities.Label;
import Lib.Entities.Structure;
import Mapper.Data.D;
import Mapper.Logic.HeightUpdater;
import Mapper.Mapper;

public class ResizeWindow extends javax.swing.JFrame {
    
    public ResizeWindow() {
        initComponents();
        currWidth.setText("Current width: "+Mapper.width);
        currHeight.setText("Current height: "+Mapper.height);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        upSpin = new javax.swing.JSpinner();
        downSpin = new javax.swing.JSpinner();
        leftSpin = new javax.swing.JSpinner();
        rightSpin = new javax.swing.JSpinner();
        currWidth = new javax.swing.JLabel();
        currHeight = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Resize area");
        setResizable(false);

        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setText("Resize!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        upSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        upSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        downSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        downSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        leftSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        leftSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        rightSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rightSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        currWidth.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currWidth.setText("Current width: 25");

        currHeight.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currHeight.setText("Current height: 25");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Global add height");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("(You can input negative vaues, too)");

        jSpinner1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText("Add/Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(upSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(downSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currWidth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currHeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(leftSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 53, Short.MAX_VALUE)
                                .addComponent(rightSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSpinner1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(upSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currWidth)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rightSpin)
                    .addComponent(leftSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(currHeight)
                .addGap(18, 18, 18)
                .addComponent(downSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int up = (int)upSpin.getModel().getValue();
        int down = (int)downSpin.getModel().getValue();
        int left = (int)leftSpin.getModel().getValue();
        int right = (int)rightSpin.getModel().getValue();
        
        int width = Mapper.width+right+left;
        int height = Mapper.height+up+down;
        
        if (width<25 || width>9999) {
            close();
            return;
        }
        else if (height<25 || height>9999) {
            close();
            return;
        }
        
        Structure[][][] objects = new Structure[width*4][15][height*4];
        Data[][][] tiles = new Data[width][15][height];
        Data[][][] bordersx = new Data[width][15][height];
        Data[][][] bordersy = new Data[width][15][height];
        Ground[][] ground = new Ground[width][height];
        Ground[][] caveGround = new Ground[width][height];
        Label[][] labels = new Label[width][height];
        Label[][] caveLabels = new Label[width][height];
        float[][] heightmap = new float[width+1][height+1];
        
        int xReg = 0;
        int yReg = 0;
        
        for (int i=-left; i<Mapper.width+right; i++) {
            for (int i2=-down; i2<Mapper.height+up; i2++) {
                if (i>=0 && i<Mapper.width && i2>=0 && i2<Mapper.height) {
                    for (int i3=0; i3<15; i3++) {
                        for (int i4=0; i4<4; i4++) {
                            for (int i5=0; i5<4; i5++) {
                                objects[xReg*4+i4][i3][yReg*4+i5] = Mapper.objects[i*4+i4][i3][i2*4+i5];
                            }
                        }
                        tiles[xReg][i3][yReg] = Mapper.tiles[i][i3][i2];
                        bordersx[xReg][i3][yReg] = Mapper.bordersx[i][i3][i2];
                        bordersy[xReg][i3][yReg] = Mapper.bordersy[i][i3][i2];
                    }
                    labels[xReg][yReg] = Mapper.labels[i][i2];
                    caveLabels[xReg][yReg] = Mapper.caveLabels[i][i2];
                    ground[xReg][yReg] = Mapper.ground[i][i2];
                    caveGround[xReg][yReg] = Mapper.caveGround[i][i2];
                }
                yReg++;
            }
            xReg++;
            yReg=0;
        }
        
        xReg = 0;
        yReg = 0;
        
        for (int i=-left; i<Mapper.width+right+1; i++) {
            for (int i2=-down; i2<Mapper.height+up+1; i2++) {
                if (i>=0 && i<Mapper.width+1 && i2>=0 && i2<Mapper.height+1) {
                    heightmap[xReg][yReg] = Mapper.heightmap[i][i2];
                }
                yReg++;
            }
            xReg++;
            yReg=0;
        }
        
        for (int i=0; i<Mapper.width+right+left; i++) {
            for (int i2=0; i2<Mapper.height+up+down; i2++) {
                if (ground[i][i2]==null) {
                    ground[i][i2]=D.grounds.get(0).copy(i, i2);
                    caveGround[i][i2]=D.caveGrounds.get(0).copy(i, i2);
                }
                else {
                    ground[i][i2].x = i;
                    ground[i][i2].y = i2;
                    caveGround[i][i2].x = i;
                    caveGround[i][i2].y = i2;
                }
            }
        }
        
        if (Mapper.width>width) {
            Mapper.width = width;
        }
        if (Mapper.height>height) {
            Mapper.height = height;
        }
        Mapper.heightmap = heightmap;
        Mapper.labels = labels;
        Mapper.caveLabels = caveLabels;
        Mapper.objects = objects;
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.ground = ground;
        Mapper.caveGround = caveGround;
        Mapper.width = width;
        Mapper.height = height;
        close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int add = (int) jSpinner1.getModel().getValue();
        for (int i=0; i<=Mapper.width; i++) {
            for (int i2=0; i2<=Mapper.height; i2++) {
                Mapper.heightmap[i][i2]+=add;
            }
        }
        HeightUpdater.checkElevations();
        close();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void close() {
        setVisible(false);
        dispose();
    }
    
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
            java.util.logging.Logger.getLogger(ResizeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResizeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResizeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResizeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResizeWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currHeight;
    private javax.swing.JLabel currWidth;
    private javax.swing.JSpinner downSpin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner leftSpin;
    private javax.swing.JSpinner rightSpin;
    private javax.swing.JSpinner upSpin;
    // End of variables declaration//GEN-END:variables
}
