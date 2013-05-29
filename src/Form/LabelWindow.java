package Form;

import Lib.Object.Label;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import org.lwjgl.util.Color;

public class LabelWindow extends javax.swing.JFrame {

    public static int x;
    public static int y;
    public static int size = 20;
    public static java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 10);
    public static java.awt.Color color = java.awt.Color.black;
    
    public LabelWindow() {
        initComponents();
        int selectedFont = 0;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (java.awt.Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            model.addElement(new FontWrapper(f));
            if (f.getName().equals(font.getName())) {
                selectedFont = model.getSize()-1;
            }
        }
        fontsBox.setModel(model);
        fontsBox.setSelectedIndex(selectedFont);
        colorPreview.setBackground(color);
        sizeSpinner.getModel().setValue(size);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        labelText = new javax.swing.JTextField();
        fontsBox = new javax.swing.JComboBox();
        colorButton = new javax.swing.JButton();
        colorPreview = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        sizeSpinner = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New label");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New label");

        labelText.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelText.setText("Label text");

        fontsBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        fontsBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontsBoxActionPerformed(evt);
            }
        });

        colorButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        colorButton.setText("Pick color");
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        colorPreview.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout colorPreviewLayout = new javax.swing.GroupLayout(colorPreview);
        colorPreview.setLayout(colorPreviewLayout);
        colorPreviewLayout.setHorizontalGroup(
            colorPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        colorPreviewLayout.setVerticalGroup(
            colorPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        saveButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        sizeSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        sizeSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, 100, 1));
        sizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sizeSpinnerStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelText)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(fontsBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(colorButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colorPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sizeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fontsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorButton)
                    .addComponent(colorPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed
        JColorChooser colorPick = new JColorChooser();
        java.awt.Color c = JColorChooser.showDialog(colorPick, "Select font color", color);;
        if (c!=null) {
            color = c;
            colorPreview.setBackground(c);
        }
    }//GEN-LAST:event_colorButtonActionPerformed

    private void fontsBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontsBoxActionPerformed
        font = ((FontWrapper)fontsBox.getSelectedItem()).font;
    }//GEN-LAST:event_fontsBoxActionPerformed

    private void sizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sizeSpinnerStateChanged
        size = (int) sizeSpinner.getModel().getValue();
    }//GEN-LAST:event_sizeSpinnerStateChanged

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        Font fnt = new Font(font.getName(), Font.PLAIN, size);
        String text = labelText.getText().replace("\\n", "\n");
        Mapper.Mapper.labels[x][y] = new Label(fnt, c, text);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

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
            java.util.logging.Logger.getLogger(LabelWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LabelWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LabelWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LabelWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LabelWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton colorButton;
    private javax.swing.JPanel colorPreview;
    private javax.swing.JComboBox fontsBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField labelText;
    private javax.swing.JButton saveButton;
    private javax.swing.JSpinner sizeSpinner;
    // End of variables declaration//GEN-END:variables
}
