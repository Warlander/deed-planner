package Form;

import Lib.Graphics.Ground;
import Lib.Object.Data;
import Mapper.Data.D;
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
        upSpin.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        downSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        downSpin.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        leftSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        leftSpin.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        rightSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rightSpin.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        currWidth.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currWidth.setText("Current width: 25");

        currHeight.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currHeight.setText("Current height: 25");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currWidth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currHeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(leftSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 53, Short.MAX_VALUE)
                                .addComponent(rightSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
        
        if (width<25 || width>1000) {
            close();
            return;
        }
        else if (height<25 || height>1000) {
            close();
            return;
        }
        
        Data[][][] tiles = new Data[Mapper.width+right+left][Mapper.height+up+down][15];
        Data[][][] bordersx = new Data[Mapper.width+right+left][Mapper.height+up+down][15];
        Data[][][] bordersy = new Data[Mapper.width+right+left][Mapper.height+up+down][15];
        Ground[][] ground = new Ground[Mapper.width+right+left][Mapper.height+up+down];
        
        int xReg = 0;
        int yReg = 0;
        
        for (int i=-left; i<Mapper.width+right; i++) {
            for (int i2=-down; i2<Mapper.height+up; i2++) {
                if (i>=0 && i<Mapper.width && i2>=0 && i2<Mapper.height) {
                    for (int i3=0; i3<15; i3++) {
                        tiles[xReg][yReg][i3] = Mapper.tiles[i][i2][i3];
                        bordersx[xReg][yReg][i3] = Mapper.bordersx[i][i2][i3];
                        bordersy[xReg][yReg][i3] = Mapper.bordersy[i][i2][i3];
                    }
                    ground[xReg][yReg] = Mapper.ground[i][i2];
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
                }
                else {
                    ground[i][i2].x = i;
                    ground[i][i2].y = i2;
                }
            }
        }
        
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.ground = ground;
        Mapper.width = width;
        Mapper.height = height;
        close();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JSpinner leftSpin;
    private javax.swing.JSpinner rightSpin;
    private javax.swing.JSpinner upSpin;
    // End of variables declaration//GEN-END:variables
}
