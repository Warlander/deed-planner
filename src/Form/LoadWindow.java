package Form;

import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.Writ;
import Mapper.Data.D;
import Mapper.FPPCamera;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import Mapper.Mapper;
import Mapper.UpCamera;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class LoadWindow extends javax.swing.JFrame {

    private static String ver=null;
    
    public LoadWindow() {
        initComponents();
    }

    public static void loadManager(String load) {
        String version = readVersion(load);
        load = load.substring(load.indexOf("|")+1);
        ver = version;
        switch(version) {
            case "0.5":
                load0_5(load);
                break;
            case "0.4":
                load0_4(load);
                break;
            case "0.3":
                load0_3(load);
                break;
            case "0.2":
                load0_2(load);
                break;
        }
        UpCamera.reset();
        FPPCamera.reset();
    }
    
    private static void load0_5(String load) {
        InputStream is = new ByteArrayInputStream(load.getBytes());
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        Mapper.updater.writUpdater.model.clear();
        
        int width = Integer.parseInt(readToChar(br, ','));
        int height = Integer.parseInt(readToChar(br, ','));
        
        Ground[][] ground = new Ground[width][height];
        Data[][][] tiles = new Data[width][height][15];
        Data[][][] bordersx = new Data[width][height][15];
        Data[][][] bordersy = new Data[width][height][15];
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                String name = readToChar(br, ',');
                ground[x][y] = getGround(name, x, y);
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    String name = readToChar(br, ',');
                    tiles[x][y][z] = getData(name);
                    name = readToChar(br, ',');
                    bordersx[x][y][z] = getData(name);
                    name = readToChar(br, ',');
                    bordersy[x][y][z] = getData(name);
                }
            }
        }
        
        int writs = Integer.parseInt(readToChar(br, ','));
        String writRead;
        
        for (int i=0; i<writs; i++) {
            Writ w = new Writ(readToChar(br, ';'));
            while (!(writRead=readToChar(br, ';')).equals("end")) {
                int gx=Integer.parseInt(writRead.substring(0, writRead.indexOf(".")));
                int gy=Integer.parseInt(writRead.substring(writRead.indexOf(".")+1));
                w.tiles.add(ground[gx][gy]);
                ground[gx][gy].writ = w;
            }
            Mapper.updater.writUpdater.model.addElement(w);
        }
        
        Mapper.ground = ground;
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.width = width;
        Mapper.height = height;
        
        int cap = Mapper.updater.writUpdater.model.getSize()+1;
        HouseCalc.jTextField1.setText("Writ "+cap);
        
        Mapper.updater.roofUpdater.roofsRefit();
    }
    
    private static void load0_4(String load) {
        InputStream is = new ByteArrayInputStream(load.getBytes());
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        int width = Integer.parseInt(readToChar(br, ','));
        int height = Integer.parseInt(readToChar(br, ','));
        
        Ground[][] ground = new Ground[width][height];
        Data[][][] tiles = new Data[width][height][15];
        Data[][][] bordersx = new Data[width][height][15];
        Data[][][] bordersy = new Data[width][height][15];
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                String name = readToChar(br, ',');
                ground[x][y] = getGround(name, x, y);
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    String name = readToChar(br, ',');
                    tiles[x][y][z] = getData(name);
                    name = readToChar(br, ',');
                    bordersx[x][y][z] = getData(name);
                    name = readToChar(br, ',');
                    bordersy[x][y][z] = getData(name);
                }
            }
        }
        
        Mapper.ground = ground;
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.width = width;
        Mapper.height = height;
        Mapper.wData=null;
        
        Mapper.updater.roofUpdater.roofsRefit();
    }
    
    private static void load0_3(String load) {
        int width = Integer.parseInt(load.substring(0, load.indexOf(",")));
        load = load.substring(load.indexOf(",")+1);
        int height = Integer.parseInt(load.substring(0, load.indexOf(",")));
        load = load.substring(load.indexOf(",")+1);
        
        Ground[][] ground = new Ground[width][height];
        Data[][][] tiles = new Data[width][height][15];
        Data[][][] bordersx = new Data[width][height][15];
        Data[][][] bordersy = new Data[width][height][15];
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                String name = load.substring(0, load.indexOf(","));
                load = load.substring(load.indexOf(",")+1);
                ground[x][y] = getGround(name, x, y);
            }
        }
        
        for (int z=0; z<15; z++) {
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    String name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    tiles[x][y][z] = getData(name);
                    name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    bordersx[x][y][z] = getData(name);
                    name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    bordersy[x][y][z] = getData(name);
                }
            }
        }
        
        Mapper.ground = ground;
        Mapper.tiles = tiles;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.width = width;
        Mapper.height = height;
        
        Mapper.updater.roofUpdater.roofsRefit();
    }
    
    private static void load0_2(String load) {
        for (int z=0; z<15; z++) {
            for (int x=0; x<25; x++) {
                for (int y=0; y<25; y++) {
                    String name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    Mapper.tiles[x][y][z] = getData(name);
                    name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    Mapper.bordersx[x][y][z] = getData(name);
                    name = load.substring(0, load.indexOf(","));
                    load = load.substring(load.indexOf(",")+1);
                    Mapper.bordersy[x][y][z] = getData(name);
                }
            }
        }
        Mapper.updater.roofUpdater.roofsRefit();
    }
    
    private static String readToChar(BufferedReader read, char toChar) {
        char c;
        String out="";
        try {
            while ((c=(char)read.read())!=toChar) {
                out+=c;
            }
        } catch (IOException ex) {
            Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
    
    private static Data getData(String shortName) {
        if (shortName.equals("0")) {
            return null;
        }
        for (Data d : D.models) {
            if (ver.equals("0.2") || ver.equals("0.3")) {
                if (d.shortName.equals(shortName)) {
                    return d.copy();
                }
            }
            else {
                if (shortName.contains(";")) {
                    if (d.shortName.equals(shortName.substring(0, shortName.indexOf(";")))) {
                        shortName = shortName.substring(shortName.indexOf(";")+1);
                        double r = Double.parseDouble(shortName.substring(0, shortName.indexOf(";")));
                        shortName = shortName.substring(shortName.indexOf(";")+1);
                        double g = Double.parseDouble(shortName.substring(0, shortName.indexOf(";")));
                        shortName = shortName.substring(shortName.indexOf(";")+1);
                        double b = Double.parseDouble(shortName);
                        Data data = d.copy();
                        data.rPaint = r;
                        data.gPaint = g;
                        data.bPaint = b;
                        return data;
                    }
                }
                else {
                    if (d.shortName.equals(shortName)) {
                        return d.copy();
                    }
                }
            }
        }
        return null;
    }
    
    private static Ground getGround(String shortName, int x, int y) {
        for (Ground g : D.grounds) {
            if (g.shortName.equals(shortName)) {
                return g.copy(x, y);
            }
        }
        return null;
    }
    
    private static String readVersion(String text) {
        if (text.indexOf("|")>1) {
            return text.substring(0, text.indexOf("|"));
        }
        return "error";
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codeField = new javax.swing.JTextField();
        codeButton = new javax.swing.JButton();
        urlButton = new javax.swing.JButton();
        urlField = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Load map");
        setResizable(false);

        codeField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeField.setText("Enter code");

        codeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeButton.setText("Load by code");
        codeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeButtonActionPerformed(evt);
            }
        });

        urlButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        urlButton.setText("Load from pastebin.com");
        urlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlButtonActionPerformed(evt);
            }
        });

        urlField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        urlField.setText("Enter URL");

        fileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        fileButton.setText("Open from file");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urlButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeButtonActionPerformed
        if (!codeField.getText().contains("|")) {
            return;
        }
        loadManager(codeField.getText());
        setVisible(false);
        dispose();
    }//GEN-LAST:event_codeButtonActionPerformed

    private void urlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlButtonActionPerformed
        if (!urlField.getText().contains("/")) {
            return;
        }
        String url;
        if (urlField.getText().contains("raw")) {
            url = urlField.getText();
        }
        else {
            String id = urlField.getText().substring(urlField.getText().lastIndexOf("/")+1);
            url = "http://pastebin.com/raw.php?i="+id;
        }
        try {
            URL uri = new URL(url);
            URLConnection conn = uri.openConnection();
            InputStream in = conn.getInputStream();
            String output="";
            while (in.available()>0) {
                output = output.concat(String.valueOf((char)in.read()));
            }
            in.close();
            loadManager(output);
        } catch (IOException ex) {
            Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        setVisible(false);
        dispose();
    }//GEN-LAST:event_urlButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(".MAP file", "MAP");
        fc.setFileFilter(filter);
        
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                BufferedReader read = new BufferedReader(new FileReader(file));
                String out = read.readLine();
                read.close();
                loadManager(out);
            } catch (IOException ex) {
                Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setVisible(false);
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

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
            java.util.logging.Logger.getLogger(LoadWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoadWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoadWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoadWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoadWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton codeButton;
    private javax.swing.JTextField codeField;
    private javax.swing.JButton fileButton;
    private javax.swing.JButton urlButton;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
}
