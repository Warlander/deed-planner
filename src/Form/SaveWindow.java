package Form;

import Lib.Files.FileManager;
import Lib.Graphics.Ground;
import Lib.Object.Writ;
import Mapper.Mapper;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class SaveWindow extends javax.swing.JFrame {

    private final String slash = System.getProperty("file.separator");
    private static String previousDir;
    
    DefaultListModel list;
    
    private String save;
    
    public SaveWindow() {
        initComponents();
        if (FileManager.fileExists("Saves")) {
            list = new DefaultListModel();
            File[] directories = FileManager.getFile("Saves/").listFiles();
            FileWrapper[] wrap = new FileWrapper[directories.length];
            for (int i=0; i<directories.length; i++) {
                wrap[i] = new FileWrapper(directories[i]);
                list.addElement(wrap[i]);
            }
            jList1.setModel(list);
        }
        else {
            FileManager.createDirectory("Saves/");
        }
        saveMaker();
    }
    
    private void saveMaker() {
        StringBuilder maps = new StringBuilder();
        maps.append("0.9|");
        maps.append(Mapper.width).append(",").append(Mapper.height).append(",");
        
        for (int x=0; x<=Mapper.width; x++) {
            for (int y=0; y<=Mapper.height; y++) {
                maps.append((int)Mapper.heightmap[x][y]).append(",");
            }
        }
        
        for (int x=0; x<Mapper.width; x++) {
            for (int y=0; y<Mapper.height; y++) {
                maps.append(Mapper.ground[x][y].shortName).append(",");
                maps.append(Mapper.caveGround[x][y].shortName).append(",");
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Mapper.width*4; x++) {
                for (int y=0; y<Mapper.height*4; y++) {
                    if (Mapper.objects[x][y][z]!=null) {
                        maps.append(Mapper.objects[x][y][z].shortName).append(";").append(Mapper.objects[x][y][z].rPaint).append(";").append(Mapper.objects[x][y][z].gPaint).append(";").append(Mapper.objects[x][y][z].bPaint);
                    }
                    else {
                        maps.append("0");
                    }
                    maps.append(",");
                }
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<Mapper.width; x++) {
                for (int y=0; y<Mapper.height; y++) {
                    if (Mapper.tiles[x][y][z]!=null) {
                        maps.append(Mapper.tiles[x][y][z].shortName);
                    }
                    else {
                        maps.append("0");
                    }
                    maps.append(",");
                    if (Mapper.bordersx[x][y][z]!=null) {
                        maps.append(Mapper.bordersx[x][y][z].shortName).append(";").append(Mapper.bordersx[x][y][z].rPaint).append(";").append(Mapper.bordersx[x][y][z].gPaint).append(";").append(Mapper.bordersx[x][y][z].bPaint);
                    }
                    else {
                        maps.append("0");
                    }
                    maps.append(",");
                    if (Mapper.bordersy[x][y][z]!=null) {
                        maps.append(Mapper.bordersy[x][y][z].shortName).append(";").append(Mapper.bordersy[x][y][z].rPaint).append(";").append(Mapper.bordersy[x][y][z].gPaint).append(";").append(Mapper.bordersy[x][y][z].bPaint);
                    }
                    else {
                        maps.append("0");
                    }
                    maps.append(",");
                }
            }
        }
        
        maps.append(Mapper.updater.writUpdater.model.size()).append(",");
        if (!Mapper.updater.writUpdater.model.isEmpty()) {
            for (int i=0; i<Mapper.updater.writUpdater.model.size(); i++) {
                Writ w = (Writ)Mapper.updater.writUpdater.model.get(i);
                maps.append(w.name).append(";");
                for (int i2=0; i2<w.tiles.size(); i2++) {
                    Ground g = w.tiles.get(i2);
                    maps.append(g.x).append(".").append(g.y);
                    maps.append(";");
                }
                maps.append("end;");
            }
        }
        save = maps.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Save map");

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setText("Save to file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText("Save to pastebin.com");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Saved map");

        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setText("Copy code");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quick save");

        jList1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jList1.setFixedCellHeight(20);
        jScrollPane1.setViewportView(jList1);

        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setText("Save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextField1.setText("Save name");

        jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton5.setText("Overwrite selected");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton6.setText("Delete selected");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            String data;
            data = URLEncoder.encode("api_dev_key", "UTF-8") + "=" + URLEncoder.encode("24844c99ae9971a2da79a2f7d0da7642", "UTF-8");
            data += "&" + URLEncoder.encode("api_paste_code", "UTF-8") + "=" + URLEncoder.encode(save, "UTF-8");
            data += "&" + URLEncoder.encode("api_option", "UTF-8") + "=" + URLEncoder.encode("paste", "UTF-8");
            data += "&" + URLEncoder.encode("api_paste_expire_date", "UTF-8") + "=" + URLEncoder.encode("1M", "UTF-8");
            URL url = new URL("http://pastebin.com/api/api_post.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Desktop.getDesktop().browse(new URI(line));
            }
            wr.close();
            rd.close();
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        JFileChooser fc = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(".MAP file", new String[] { "MAP" });
        fc.setFileFilter(filter);
        if (previousDir!=null) {
            fc.setCurrentDirectory(new File(previousDir));
        }
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            saveToFile(file);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void saveToFile(File file) {
        if (!file.getPath().contains(".MAP")) {
            file = new File(file.getPath()+".MAP");
        }
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(new FileOutputStream(file));
            out.print(save);
            out.close();
            String ph = file.getPath();
            previousDir = ph.substring(0, ph.lastIndexOf(slash)+1);
            setVisible(false);
            dispose();
        } catch (IOException ex) {
            Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        StringSelection select = new StringSelection(save);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, select);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        saveToFile(FileManager.getFile("Saves"+slash+jTextField1.getText()+".MAP"));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (!jList1.isSelectionEmpty()) {
            FileWrapper wrap = (FileWrapper)jList1.getSelectedValue();
            list.removeElement(wrap);
            wrap.file.delete();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (!jList1.isSelectionEmpty()) {
            saveToFile(((FileWrapper)jList1.getSelectedValue()).file);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private class ExtensionFileFilter extends FileFilter {
        String description;

        String extensions[];

        public ExtensionFileFilter(String description, String extension) {
          this(description, new String[] { extension });
        }

        public ExtensionFileFilter(String description, String extensions[]) {
          if (description == null) {
            this.description = extensions[0];
          } else {
            this.description = description;
          }
          this.extensions = (String[]) extensions.clone();
          toLower(this.extensions);
        }

        private void toLower(String array[]) {
          for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
          }
        }

        public String getDescription() {
          return description;
        }

        public boolean accept(File file) {
          if (file.isDirectory()) {
            return true;
          } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
              String extension = extensions[i];
              if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                return true;
              }
            }
          }
          return false;
        }
    }
    
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
            java.util.logging.Logger.getLogger(SaveWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaveWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaveWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaveWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SaveWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
