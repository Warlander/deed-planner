package Form;

import Lib.Files.FileManager;
import Lib.Graphics.Ground;
import Lib.Entities.Data;
import Lib.Entities.Label;
import Lib.Entities.Structure;
import Lib.Entities.Writ;
import Mapper.Data.D;
import Mapper.FPPCamera;
import Mapper.Logic.HeightUpdater;
import Mapper.Logic.RoofUpdater;
import Mapper.Logic.WritUpdater;
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
import java.awt.Font;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import org.lwjgl.util.Color;

public class LoadWindow extends javax.swing.JFrame {

    private final String endl = System.getProperty("line.separator");
    
    DefaultListModel list;
    
    public LoadWindow() {
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
    }

    public static void loadManager(String load) {
        String version = readVersion(load);
        load = load.substring(load.indexOf("|")+1);
        HouseCalc.reset();
        switch(version) {
            case "1.1" :
                try {
                    load1_1(load);
                } catch (IOException ex) {
                    Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "1.0":
                try {
                    load1_0(load);
                } catch (IOException ex) {
                    Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Unsupported/outdated save format: "+version);
                break;
        }
        UpCamera.reset();
        FPPCamera.reset();
    }
    
    private static void load1_1(String load) throws IOException {
        WritUpdater.model.clear();
        
        InputStream is = new ByteArrayInputStream(load.getBytes());
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Scanner scan;
        
        br.readLine();
        String line = br.readLine();
        scan = new Scanner(line);
        int width = scan.nextInt();
        int height = scan.nextInt();
        
        float[][] heightmap = new float[width+1][height+1];
        Ground[][] ground = new Ground[width][height];
        Ground[][] caveGround = new Ground[width][height];
        Label[][] labels = new Label[width][height];
        Label[][] caveLabels = new Label[width][height];
        Structure[][][] objects = new Structure[width*4][15][height*4];
        Data[][][] tiles = new Data[width][15][height];
        Data[][][] bordersx = new Data[width][15][height];
        Data[][][] bordersy = new Data[width][15][height];
        
        for (int i=0; i<width; i++) {
            for (int i2=0; i2<height; i2++) {
                ground[i][i2] = D.grounds.get(0).copy(i, i2);
                caveGround[i][i2] = D.caveGrounds.get(0).copy(i, i2);
            }
        }
        
        while ((line=br.readLine())!=null) {
            scan = new Scanner(line);
            
            switch (scan.next()) {
                case "H":
                    readHeight(scan, heightmap);
                    break;
                case "G":
                    readGround(scan, ground);
                    break;
                case "C":
                    readCave(scan, caveGround);
                    break;
                case "O":
                    readObject(scan, objects);
                    break;
                case "T":
                    readTile(scan, tiles);
                    break;
                case "BX":
                    readBorder(scan, bordersx);
                    break;
                case "BY":
                    readBorder(scan, bordersy);
                    break;
                case "L":
                    readLabel(scan, labels, caveLabels);
                    break;
                case "W":
                    readWrit(scan, ground, WritUpdater.model);
                    break;
            }
        }
        
        Mapper.width = width;
        Mapper.height = height;
        Mapper.heightmap = heightmap;
        HeightUpdater.checkElevations();
        Mapper.ground = ground;
        Mapper.labels = labels;
        Mapper.caveLabels = caveLabels;
        Mapper.caveGround = caveGround;
        Mapper.tiles = tiles;
        RoofUpdater.roofsRefit();
        Mapper.objects = objects;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        
        int cap = WritUpdater.model.getSize()+1;
        HouseCalc.jTextField1.setText("Writ "+cap);
    }
    
    private static void load1_0(String load) throws IOException {
        WritUpdater.model.clear();
        
        InputStream is = new ByteArrayInputStream(load.getBytes());
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Scanner scan;
        
        br.readLine();
        String line = br.readLine();
        scan = new Scanner(line);
        int width = scan.nextInt();
        int height = scan.nextInt();
        
        float[][] heightmap = new float[width+1][height+1];
        Ground[][] ground = new Ground[width][height];
        Ground[][] caveGround = new Ground[width][height];
        Label[][] labels = new Label[width][height];
        Label[][] caveLabels = new Label[width][height];
        Structure[][][] objects = new Structure[width*4][15][height*4];
        Data[][][] tiles = new Data[width][15][height];
        Data[][][] bordersx = new Data[width][15][height];
        Data[][][] bordersy = new Data[width][15][height];
        
        for (int i=0; i<width; i++) {
            for (int i2=0; i2<height; i2++) {
                ground[i][i2] = D.grounds.get(0).copy(i, i2);
                caveGround[i][i2] = D.caveGrounds.get(0).copy(i, i2);
            }
        }
        
        while ((line=br.readLine())!=null) {
            scan = new Scanner(line);
            
            switch (scan.next()) {
                case "H":
                    readHeight(scan, heightmap);
                    break;
                case "G":
                    readGround(scan, ground);
                    break;
                case "C":
                    readCave(scan, caveGround);
                    break;
                case "O":
                    readOldObject(scan, objects);
                    break;
                case "T":
                    readOldTile(scan, tiles);
                    break;
                case "BX":
                    readOldBorder(scan, bordersx);
                    break;
                case "BY":
                    readOldBorder(scan, bordersy);
                    break;
                case "L":
                    readLabel(scan, labels, caveLabels);
                    break;
                case "W":
                    readWrit(scan, ground, WritUpdater.model);
                    break;
            }
        }
        
        Mapper.width = width;
        Mapper.height = height;
        Mapper.objects = objects;
        Mapper.bordersx = bordersx;
        Mapper.bordersy = bordersy;
        Mapper.tiles = tiles;
        Mapper.ground = ground;
        Mapper.caveGround = caveGround;
        Mapper.labels = labels;
        Mapper.caveLabels = caveLabels;
        Mapper.heightmap = heightmap;
        HeightUpdater.checkElevations();
        RoofUpdater.roofsRefit();
        
        
        int cap = WritUpdater.model.getSize()+1;
        HouseCalc.jTextField1.setText("Writ "+cap);
    }
    
    public static void readHeight(Scanner source, float[][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        int val = source.nextInt();
        
        out[x][y] = val;
    }
    
    public static void readGround(Scanner source, Ground[][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        String shortName = source.next();
        
        out[x][y] = getGround(shortName, x, y);
    }
    
    public static void readCave(Scanner source, Ground[][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        String shortName = source.next();
        
        out[x][y] = getCaveGround(shortName, x, y);
    }
    
    public static void readObject(Scanner source, Structure[][][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        String shortName = source.next();
        double rPaint = Double.parseDouble(source.next());
        double gPaint = Double.parseDouble(source.next());
        double bPaint = Double.parseDouble(source.next());
        int rotation = Integer.parseInt(source.next());
        
        Structure object = getLightweightStructure(shortName);
        object.rPaint = rPaint;
        object.gPaint = gPaint;
        object.bPaint = bPaint;
        object.rotation = rotation;
        out[x][y][z] = object;
    }
    
    public static void readOldObject(Scanner source, Structure[][][] out) {
        int x = source.nextInt();
        int z = source.nextInt();
        int y = source.nextInt();
        String shortName = source.next();
        double rPaint = Double.parseDouble(source.next());
        double gPaint = Double.parseDouble(source.next());
        double bPaint = Double.parseDouble(source.next());
        int rotation = Integer.parseInt(source.next());
        
        Structure object = getLightweightStructure(shortName);
        object.rPaint = rPaint;
        object.gPaint = gPaint;
        object.bPaint = bPaint;
        object.rotation = rotation;
        out[x][y][z] = object;
    }
    
    public static void readTile(Scanner source, Data[][][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        String shortName = source.next();
        
        out[x][y][z] = getLightweightData(shortName);
    }
    
    public static void readOldTile(Scanner source, Data[][][] out) {
        int x = source.nextInt();
        int z = source.nextInt();
        int y = source.nextInt();
        String shortName = source.next();
        
        out[x][y][z] = getLightweightData(shortName);
    }
    
    public static void readBorder(Scanner source, Data[][][] out) {
        int x = source.nextInt();
        int y = source.nextInt();
        int z = source.nextInt();
        String shortName = source.next();
        double rPaint = Double.parseDouble(source.next());
        double gPaint = Double.parseDouble(source.next());
        double bPaint = Double.parseDouble(source.next());
        
        Data object = getLightweightData(shortName);
        object.rPaint = rPaint;
        object.gPaint = gPaint;
        object.bPaint = bPaint;
        out[x][y][z] = object;
    }
    
    public static void readOldBorder(Scanner source, Data[][][] out) {
        int x = source.nextInt();
        int z = source.nextInt();
        int y = source.nextInt();
        String shortName = source.next();
        double rPaint = Double.parseDouble(source.next());
        double gPaint = Double.parseDouble(source.next());
        double bPaint = Double.parseDouble(source.next());
        
        Data object = getLightweightData(shortName);
        object.rPaint = rPaint;
        object.gPaint = gPaint;
        object.bPaint = bPaint;
        out[x][y][z] = object;
    }
    
    public static void readLabel(Scanner source, Label[][] out, Label[][] outAlt) {
        int x = source.nextInt();
        int y = source.nextInt();
        String text = source.next().replace("_", " ").replace("\\n", "\n");
        String font = source.next().replace("_", " ");
        int size = Integer.parseInt(source.next());
        int rPaint = Integer.parseInt(source.next());
        int gPaint = Integer.parseInt(source.next());
        int bPaint = Integer.parseInt(source.next());
        int aPaint = Integer.parseInt(source.next());
        boolean cave = false;
        if (source.hasNextBoolean()) {
            cave = source.nextBoolean();
        }
        
        Font f = new Font(font, Font.PLAIN, size);
        Color c = new Color(rPaint, gPaint, bPaint, aPaint);
        Label object = new Label(f, c, text, cave);
        if (!cave) {
            out[x][y] = object;
        }
        else {
            outAlt[x][y] = object;
        }
    }
    
    public static void readWrit(Scanner source, Ground[][] in, DefaultListModel out) {
        String shortName = source.next().replace("_", " ");
        int tiles = source.nextInt();
        
        Writ w = new Writ(shortName);
        
        for (int i=0; i<tiles; i++) {
            int x = source.nextInt();
            int y = source.nextInt();
            w.tiles.add(in[x][y]);
            in[x][y].writ = w;
        }
        out.addElement(w);
    }
    
    private static Structure getLightweightStructure(String shortName) {
        for (Structure s : D.objects) {
            if (s.shortName.equals(shortName)) {
                Structure data = s.copy();
                return data;
            }
        }
        return null;
    }
    
    private static Data getLightweightData(String shortName) {
        for (Data d : D.models) {
            if (d.shortName.equals(shortName)) {
                return d.copy();
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
    
    private static Ground getCaveGround(String shortName, int x, int y) {
        for (Ground g : D.caveGrounds) {
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
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Load map");
        setResizable(false);

        codeField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeField.setText("Enter code");
        codeField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                codeFieldMouseClicked(evt);
            }
        });

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
        urlField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urlFieldMouseClicked(evt);
            }
        });

        fileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        fileButton.setText("Open from file");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quick load");

        jButton1.setText("Load selected");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(fileButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
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
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            StringBuilder output=new StringBuilder();
            String line;
            while ((line=read.readLine())!=null) {
                output.append(line).append(endl);
            }
            in.close();
            loadManager(output.toString());
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
                StringBuilder output = new StringBuilder();
                String line;
                while ((line=read.readLine())!=null) {
                    output.append(line).append(endl);
                }
                read.close();
                loadManager(output.toString());
            } catch (IOException ex) {
                Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setVisible(false);
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!jList1.isSelectionEmpty()) {
           File file = ((FileWrapper)jList1.getSelectedValue()).file;
            try {
                BufferedReader read = new BufferedReader(new FileReader(file));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line=read.readLine())!=null) {
                    output.append(line).append(endl);
                }
                read.close();
                loadManager(output.toString());
                setVisible(false);
                dispose();
            } catch (IOException ex) {
                Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void urlFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_urlFieldMouseClicked
        urlField.setText("");
    }//GEN-LAST:event_urlFieldMouseClicked

    private void codeFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeFieldMouseClicked
        codeField.setText("");
    }//GEN-LAST:event_codeFieldMouseClicked

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton urlButton;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
}
