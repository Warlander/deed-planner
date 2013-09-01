package Form;

import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.Materials;
import Mapper.Data.D;
import java.util.HashMap;
import Mapper.Mapper;
import java.util.Map;

public class MaterialsWindow extends javax.swing.JFrame {

    private HashMap<String, Integer> map;
    private HashMap<String, Integer>[] floors;
    private HashMap<String, Integer> objects;
    
    public MaterialsWindow() {
        initComponents();
        setResult();
    }
    
    private void setResult() {
        if (Mapper.wData!=null && HouseCalc.contextPane.getSelectedComponent()==HouseCalc.writsPane) {
            writResult();
        }
        else {
            mapResult();
        }
    }
    
    private void writResult() {
        StringBuilder res=new StringBuilder();
        String br=System.getProperty("line.separator");
        WritComponents writ = new WritComponents(Mapper.wData);
        calculateMaterialsWrit(writ);
        int carp = checkCarpentry(writ);
        
        res.append("WARNING - skill cost is based on both walls and floors, so calculations for structures without floors are wrong!").append(br).append(br);
        res.append("Results for the \"").append(Mapper.wData.name).append("\" writ:").append(br).append(br);
        res.append("You need ").append(carp).append(" carpentry to build this structure.").append(br).append(br);
        res.append("Materials needed: ").append(br);
        if (!map.isEmpty()) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                res.append(e.getKey()).append("=").append(e.getValue()).append(br);
            }
        }
        else {
            res.append("None").append(br);
        }
        res.append(br);
        res.append("Materials for each structure type: ").append(br);
        if (!map.isEmpty()) {
            for (Map.Entry<String, Integer> e : objects.entrySet()) {
                Materials m = D.getDataByName(e.getKey()).materials;
                res.append(e.getValue()).append(" ").append(e.getKey()).append(": ");
                for (Map.Entry<String, Integer> e2 : m.entrySet()) {
                    res.append(e2.getKey()).append("=").append(e2.getValue()*e.getValue()).append(", ");
                }
                res.delete(res.length()-2, res.length());
                res.append(br);
            }
        }
        else {
            res.append("None").append(br);
        }
        result.setText(res.toString());
    }
    
    private void mapResult() {
        StringBuilder res=new StringBuilder();
        String br=System.getProperty("line.separator");
        calculateMaterialsMap();
        
        res.append("Results for the whole map:").append(br).append(br);
        res.append("Materials needed: ").append(br);
        if (!map.isEmpty()) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                res.append(e.getKey()).append("=").append(e.getValue()).append(br);
            }
        }
        else {
            res.append("None").append(br);
        }
        res.append(br);
        res.append("Materials for each structure type: ").append(br);
        if (!map.isEmpty()) {
            for (Map.Entry<String, Integer> e : objects.entrySet()) {
                Materials m = D.getDataByName(e.getKey()).materials;
                res.append(e.getValue()).append(" ").append(e.getKey()).append(": ");
                for (Map.Entry<String, Integer> e2 : m.entrySet()) {
                    res.append(e2.getKey()).append("=").append(e2.getValue()*e.getValue()).append(", ");
                }
                res.delete(res.length()-2, res.length());
                res.append(br);
            }
        }
        else {
            res.append("None").append(br);
        }
        res.append(br);
        res.append("Materials for each floor: ").append(br);
        for (int i=0; i<15; i++) {
            res.append("Floor ").append(i+1).append(": ");
            if (!floors[i].isEmpty()) {
                for (Map.Entry<String, Integer> e : floors[i].entrySet()) {
                    res.append(e.getKey()).append("=").append(e.getValue()).append(", ");
                }
                res.delete(res.length()-2, res.length());
            }
            else {
                res.append("None");
            }
            res.append(br);
        }
        result.setText(res.toString());
    }
    
    private void calculateMaterialsWrit(WritComponents writ) {
        map = new HashMap<>();
        objects = new HashMap<>();
        
        for (Data d : writ.data) {
            addToMap(map, d.materials);
            addToMap(objects, d);
        }
    }
    
    private void calculateMaterialsMap() {
        map = new HashMap<>();
        floors = new HashMap[15];
        objects = new HashMap<>();
        for (int i=0; i<15; i++) {
            floors[i] = new HashMap<>();
        }
        
        for (int i=0; i<Mapper.width; i++) {
            for (int i2=0; i2<15; i2++) {
                for (int i3=0; i3<Mapper.height; i3++) {
                    if (Mapper.tiles[i][i2][i3]!=null) {
                        addToMap(map, Mapper.tiles[i][i2][i3].materials);
                        addToMap(floors[i2], Mapper.tiles[i][i2][i3].materials);
                        addToMap(objects, Mapper.tiles[i][i2][i3]);
                    }
                    if (Mapper.bordersx[i][i2][i3]!=null) {
                        addToMap(map, Mapper.bordersx[i][i2][i3].materials);
                        addToMap(floors[i2], Mapper.bordersx[i][i2][i3].materials);
                        addToMap(objects, Mapper.bordersx[i][i2][i3]);
                    }
                    if (Mapper.bordersy[i][i2][i3]!=null) {
                        addToMap(map, Mapper.bordersy[i][i2][i3].materials);
                        addToMap(floors[i2], Mapper.bordersy[i][i2][i3].materials);
                        addToMap(objects, Mapper.bordersy[i][i2][i3]);
                    }
                }
            }
        }
    }
    
    private void addToMap(HashMap<String, Integer> map, Materials add) {
        for (Map.Entry<String, Integer> entry : add.entrySet()) {
            String name = entry.getKey();
            int amount = entry.getValue();
            
            if (map.containsKey(name)) {
                map.put(name, map.get(name)+amount);
            }
            else {
                map.put(name, amount);
            }
        }
    }
    
    private void addToMap(HashMap<String, Integer> map, Data data) {
        if (map.containsKey(data.name)) {
            map.put(data.name, map.get(data.name)+1);
        }
        else {
            map.put(data.name, 1);
        }
    }
    
    private int checkCarpentry(WritComponents writ) {
        int carp=0;
        for (Data d : writ.data) {
            switch (d.type) {
                case floor:
                    for (int i=0; i<Mapper.width; i++) {
                        for (int i2=0; i2<Mapper.height; i2++) {
                            if (d==Mapper.tiles[i][i2][0]) {
                                carp++;
                                break;
                            }
                        }
                    }
                    break;
                case wall:
                    for (int i=0; i<Mapper.width; i++) {
                        for (int i2=0; i2<Mapper.height; i2++) {
                            if (d==Mapper.bordersx[i][i2][0] || d==Mapper.bordersy[i][i2][0]) {
                                carp++;
                                break;
                            }
                        }
                    }
                    break;
            }
        }
        for (int z=1; z<15; z++) {
            for (Ground g : Mapper.wData.tiles) {
                if (Mapper.tiles[g.x+1][g.y][z]!=null && Mapper.tiles[g.x+1][g.y][z].type!=Lib.Object.Type.roof) {
                    switch (z) {
                        case 1:
                            carp = Math.max(carp, 21);
                            break;
                        case 2:
                            carp = Math.max(carp, 24);
                            break;
                        case 3:
                            carp = Math.max(carp, 24);
                            break;
                        case 4:
                            carp = Math.max(carp, 30);
                            break;
                        case 5:
                            carp = Math.max(carp, 40);
                            break;
                        case 6:
                            carp = Math.max(carp, 49);
                            break;
                        case 7:
                            carp = Math.max(carp, 60);
                            break;
                        case 8:
                            carp = Math.max(carp, 70);
                            break;
                        case 9:
                            carp = Math.max(carp, 90);
                            break;
                        case 10:
                            carp = Math.max(carp, 94);
                            break;
                        case 11:
                            carp = Math.max(carp, 96);
                            break;
                        case 12:
                            carp = Math.max(carp, 97);
                            break;
                        case 13:
                            carp = Math.max(carp, 98);
                            break;
                        case 14:
                            carp = Math.max(carp, 99);
                            break;
                    }
                }
            }
        }
        
        return carp;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        result = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculation result");

        result.setEditable(false);
        result.setColumns(20);
        result.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        result.setRows(5);
        jScrollPane1.setViewportView(result);

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MaterialsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MaterialsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MaterialsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MaterialsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MaterialsWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea result;
    // End of variables declaration//GEN-END:variables
}
