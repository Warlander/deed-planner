package Form;

import Lib.Files.Properties;
import Lib.Graphics.Ground;
import Lib.Object.Data;
import Lib.Object.DataLoader;
import Lib.Object.Structure;
import Lib.Object.Writ;
import Lib.Utils.WebTools;
import Mapper.Data.D;
import Mapper.FPPCamera;
import Mapper.Mapper;
import Mapper.UpCamera;
import java.awt.Component;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class HouseCalc extends javax.swing.JFrame {

    public static String mapStr=null;
    
    private static boolean error = false;
    
    public static int brushSize = 0;
    public static DrawMode drawMode = DrawMode.pencil;
    public static WritMode writMode = WritMode.add;
    public static PaintMode paintMode = PaintMode.type;
    public static boolean renderHeight = false;
    public static int elevationAdd = 1;
    public static int elevationSetLeft = 0;
    public static int elevationSetRight = 0;
    
    public static boolean lockAxis = false;
    
    public static double r=1;
    public static double g=1;
    public static double b=1;
    
    private final boolean release = false;
    private final boolean debug = false;
    private int version;
    private String visualVersion;
    private boolean checkVersion = true;
    
    public HouseCalc() {
        System.setProperty("Dsun.java2d.opengl", "true");
        getProperties();
        BufferedReader reader=null;
        BufferedReader webReader=null;
        
        try {
            reader = new BufferedReader(new FileReader("version.txt"));
            version = Integer.parseInt(reader.readLine());
            visualVersion = reader.readLine();
            webReader = WebTools.siteToReader(reader.readLine());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        
        if (release) {
            try {
                System.setOut(new PrintStream("InfoLog.txt"));
                System.setErr(new PrintStream(new FileOutputStream("ErrorLog.txt") {
                    public void write(int b) throws IOException {
                        super.write(b);
                        error();
                    }
                    public void write(byte[] b) throws IOException {
                        super.write(b);
                        error();
                    }
                    public void write(byte[] b, int off, int len) throws IOException {
                        super.write(b, off, len);
                        error();
                    }
                }));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HouseCalc.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (checkVersion) {
                try {
                    int verNew;
                    if (webReader!=null) {
                        verNew = Integer.parseInt(webReader.readLine());
                    }
                    else {
                        verNew = version;
                    }
                    if (version<verNew) {
                        NewVersion.main(null);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(HouseCalc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!debug) {
                String system = System.getProperty("os.name");
                if (system.contains("Windows")) {
                    System.setProperty("org.lwjgl.librarypath", new File("Windows").getAbsolutePath());
                }
                else if (system.contains("Mac")) {
                    System.setProperty("org.lwjgl.librarypath", new File("Mac").getAbsolutePath());
                }
                else if (system.contains("Linux")) {
                    System.setProperty("org.lwjgl.librarypath", new File("Linux").getAbsolutePath());
                }
                else if (system.contains("Solaris")) {
                    System.setProperty("org.lwjgl.librarypath", new File("Solaris").getAbsolutePath());
                }
            }
        }
        initComponents();
        colorablePane.setVisible(false);
        writCreatorPane.setVisible(false);
        System.out.println("Mapper form initialized");
        System.out.println("Initializing mapper core");
        mapper = new Mapper();
        mapper.run();
        System.out.println("Mapper OpenGL window started");
        try {
            reader.close();
            if (webReader!=null) {
                webReader.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(HouseCalc.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTitle("DeedPlanner "+visualVersion);
        elevationList.setSelectedIndex(0);
    }

    private void getProperties() {
        Properties.loadProperties();
        if (Properties.getProperty("javaCompile")!=null && (boolean)Properties.getProperty("javaCompile")) {
            System.setProperty("XX:CompileThreshold", "1");
        }
        if (Properties.getProperty("checkVersion")!=null) {
            checkVersion = (boolean)Properties.getProperty("checkVersion");
        }
        
        cameraProperties();
    }
    
    private void cameraProperties() {
        if (Properties.getProperty("allowWheel")!=null) {
            UpCamera.allowWheel = (boolean)Properties.getProperty("allowWheel");
        }
        if (Properties.getProperty("keyboardFractionUp")!=null) {
            UpCamera.keyboardFraction = (float)Properties.getProperty("keyboardFractionUp");
        }
        if (Properties.getProperty("mouseFractionUp")!=null) {
            UpCamera.mouseFraction = (float)Properties.getProperty("mouseFractionUp");
        }
        if (Properties.getProperty("showGrid")!=null) {
            UpCamera.showGrid = (boolean)Properties.getProperty("showGrid");
        }
        if (Properties.getProperty("scale")!=null) {
            String propString = Properties.getProperty("scale").toString();
            UpCamera.scale = Integer.parseInt(propString.substring(0, propString.indexOf(".")));
        }
        
        if (Properties.getProperty("mouseFractionFpp")!=null) {
            FPPCamera.fraction = (float)Properties.getProperty("mouseFractionFpp");
        }
        if (Properties.getProperty("cameraRotationFpp")!=null) {
            FPPCamera.rotate = (float)Properties.getProperty("cameraRotationFpp");
        }
        if (Properties.getProperty("mod1Fpp")!=null) {
            FPPCamera.shiftMod = (float)Properties.getProperty("mod1Fpp");
        }
        if (Properties.getProperty("mod2Fpp")!=null) {
            FPPCamera.controlMod = (float)Properties.getProperty("mod2Fpp");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPane = new javax.swing.JPanel();
        interfacePane = new javax.swing.JPanel();
        floorUp = new javax.swing.JButton();
        floorDown = new javax.swing.JButton();
        floorLabel = new javax.swing.JLabel();
        contextPane = new javax.swing.JTabbedPane();
        groundsPane = new javax.swing.JScrollPane();
        groundsList = new javax.swing.JList();
        elevationPane = new javax.swing.JScrollPane();
        elevationList = new javax.swing.JList();
        writsPane = new javax.swing.JScrollPane();
        writsList = new javax.swing.JList();
        floorsPane = new javax.swing.JScrollPane();
        floorsList = new javax.swing.JList();
        wallsPane = new javax.swing.JScrollPane();
        wallsList = new javax.swing.JList();
        roofsPane = new javax.swing.JScrollPane();
        roofsList = new javax.swing.JList();
        structuresPane = new javax.swing.JScrollPane();
        structuresList = new javax.swing.JList();
        toolkitPane = new javax.swing.JLayeredPane();
        flatsPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        pencilToggle = new javax.swing.JToggleButton();
        fillToggle = new javax.swing.JToggleButton();
        selectToggle = new javax.swing.JToggleButton();
        colorablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        gSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        bSpinner = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        writCreatorPane = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton8 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        writMoveUp = new javax.swing.JLabel();
        writMoveLeft = new javax.swing.JLabel();
        writMoveRight = new javax.swing.JLabel();
        writMoveDown = new javax.swing.JLabel();
        elevationCreatorPane = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        addSpinner = new javax.swing.JSpinner();
        setLeftSpinner = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        setRightSpinner = new javax.swing.JSpinner();
        upHeight = new javax.swing.JLabel();
        leftHeight = new javax.swing.JLabel();
        centerHeight = new javax.swing.JLabel();
        rightHeight = new javax.swing.JLabel();
        downHeight = new javax.swing.JLabel();
        programFrame = new java.awt.Canvas();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        wallsButton = new javax.swing.JButton();
        helpLabel = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        statusBar = new Form.StatusBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HouseCalc");
        setMinimumSize(new java.awt.Dimension(800, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        interfacePane.setPreferredSize(new java.awt.Dimension(250, 74));

        floorUp.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorUp.setText("/\\");
            floorUp.setPreferredSize(new java.awt.Dimension(40, 40));
            floorUp.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    floorUpActionPerformed(evt);
                }
            });

            floorDown.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            floorDown.setLabel("\\/");
            floorDown.setPreferredSize(new java.awt.Dimension(40, 40));
            floorDown.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    floorDownActionPerformed(evt);
                }
            });

            floorLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
            floorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            floorLabel.setText("Floor 1");

            contextPane.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            contextPane.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    contextPaneStateChanged(evt);
                }
            });

            groundsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            groundsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            groundsList.setFixedCellHeight(20);
            groundsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    groundsListValueChanged(evt);
                }
            });
            groundsPane.setViewportView(groundsList);

            contextPane.addTab("Ground", groundsPane);

            elevationList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            elevationList.setModel(new javax.swing.AbstractListModel() {
                String[] strings = { "Increase height", "Decrease height", "Set height", "Select height", "Reset height", "Smooth height" };
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
            });
            elevationList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            elevationList.setFixedCellHeight(20);
            elevationList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    elevationListValueChanged(evt);
                }
            });
            elevationPane.setViewportView(elevationList);

            contextPane.addTab("Elevation", elevationPane);

            writsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            writsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            writsList.setFixedCellHeight(20);
            writsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    writsListValueChanged(evt);
                }
            });
            writsPane.setViewportView(writsList);

            contextPane.addTab("Writs", writsPane);

            floorsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            floorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            floorsList.setFixedCellHeight(20);
            floorsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    floorsListValueChanged(evt);
                }
            });
            floorsPane.setViewportView(floorsList);

            contextPane.addTab("Floors", floorsPane);

            wallsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            wallsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            wallsList.setFixedCellHeight(20);
            wallsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    wallsListValueChanged(evt);
                }
            });
            wallsPane.setViewportView(wallsList);

            contextPane.addTab("Walls", wallsPane);

            roofsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            roofsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            roofsList.setFixedCellHeight(20);
            roofsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    roofsListValueChanged(evt);
                }
            });
            roofsPane.setViewportView(roofsList);

            contextPane.addTab("Roofs", roofsPane);

            structuresList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            structuresList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            structuresList.setFixedCellHeight(20);
            structuresList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    structuresListValueChanged(evt);
                }
            });
            structuresPane.setViewportView(structuresList);

            contextPane.addTab("Objects", structuresPane);

            flatsPane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));

            jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jLabel1.setText("Brush size:");

            jSpinner1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, 9, 1));
            jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    jSpinner1StateChanged(evt);
                }
            });

            pencilToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            pencilToggle.setSelected(true);
            pencilToggle.setText("Pencil");
            pencilToggle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    pencilToggleActionPerformed(evt);
                }
            });

            fillToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            fillToggle.setText("Fill");
            fillToggle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    fillToggleActionPerformed(evt);
                }
            });

            selectToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            selectToggle.setText("Select");
            selectToggle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    selectToggleActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout flatsPaneLayout = new javax.swing.GroupLayout(flatsPane);
            flatsPane.setLayout(flatsPaneLayout);
            flatsPaneLayout.setHorizontalGroup(
                flatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(flatsPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(flatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(flatsPaneLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(flatsPaneLayout.createSequentialGroup()
                            .addComponent(pencilToggle)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(selectToggle))
                        .addComponent(fillToggle))
                    .addContainerGap(100, Short.MAX_VALUE))
            );
            flatsPaneLayout.setVerticalGroup(
                flatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(flatsPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(flatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(flatsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pencilToggle)
                        .addComponent(selectToggle))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(fillToggle)
                    .addContainerGap(27, Short.MAX_VALUE))
            );

            flatsPane.setBounds(0, 0, 250, 120);
            toolkitPane.add(flatsPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

            colorablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));

            jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jLabel2.setText("Dye:");

            rSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, 1.0d, 0.01d));
            rSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    rSpinnerStateChanged(evt);
                }
            });

            jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(255, 0, 0));
            jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel3.setText("R");

            gSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, 1.0d, 0.01d));
            gSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    gSpinnerStateChanged(evt);
                }
            });

            jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jLabel4.setForeground(new java.awt.Color(0, 255, 0));
            jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel4.setText("G");

            bSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.0d, 1.0d, 0.01d));
            bSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    bSpinnerStateChanged(evt);
                }
            });

            jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jLabel5.setForeground(new java.awt.Color(0, 0, 255));
            jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel5.setText("B");

            jCheckBox2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            jCheckBox2.setText("Lock axis");
            jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jCheckBox2ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout colorablePaneLayout = new javax.swing.GroupLayout(colorablePane);
            colorablePane.setLayout(colorablePaneLayout);
            colorablePaneLayout.setHorizontalGroup(
                colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(colorablePaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(colorablePaneLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(rSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(gSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jCheckBox2))
                    .addContainerGap(20, Short.MAX_VALUE))
            );
            colorablePaneLayout.setVerticalGroup(
                colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(colorablePaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(colorablePaneLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(colorablePaneLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(colorablePaneLayout.createSequentialGroup()
                                .addGroup(colorablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                    .addComponent(jCheckBox2)
                    .addContainerGap())
            );

            colorablePane.setBounds(0, 0, 251, 120);
            toolkitPane.add(colorablePane, javax.swing.JLayeredPane.DEFAULT_LAYER);

            writCreatorPane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));
            writCreatorPane.setMinimumSize(new java.awt.Dimension(250, 110));
            writCreatorPane.setPreferredSize(new java.awt.Dimension(250, 110));

            jButton7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            jButton7.setText("New writ");
            jButton7.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton7ActionPerformed(evt);
                }
            });

            jToggleButton1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            jToggleButton1.setSelected(true);
            jToggleButton1.setText("Add tiles");
            jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jToggleButton1ActionPerformed(evt);
                }
            });

            jToggleButton2.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            jToggleButton2.setText("Delete tiles");
            jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jToggleButton2ActionPerformed(evt);
                }
            });

            jButton8.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            jButton8.setText("Delete selected writ and whole building");
            jButton8.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton8ActionPerformed(evt);
                }
            });

            jTextField1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            jTextField1.setText("Writ 1");
            jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTextField1MouseClicked(evt);
                }
            });

            writMoveUp.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
            writMoveUp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            writMoveUp.setText("/\\");
                writMoveUp.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        writMoveUpMouseClicked(evt);
                    }
                });

                writMoveLeft.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
                writMoveLeft.setText("<");
                writMoveLeft.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        writMoveLeftMouseClicked(evt);
                    }
                });

                writMoveRight.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
                writMoveRight.setText(">");
                writMoveRight.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        writMoveRightMouseClicked(evt);
                    }
                });

                writMoveDown.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                writMoveDown.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                writMoveDown.setText("\\/");
                writMoveDown.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        writMoveDownMouseClicked(evt);
                    }
                });

                javax.swing.GroupLayout writCreatorPaneLayout = new javax.swing.GroupLayout(writCreatorPane);
                writCreatorPane.setLayout(writCreatorPaneLayout);
                writCreatorPaneLayout.setHorizontalGroup(
                    writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(writCreatorPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(writCreatorPaneLayout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                            .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton8)
                                .addGroup(writCreatorPaneLayout.createSequentialGroup()
                                    .addComponent(jToggleButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jToggleButton2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(writMoveUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(writCreatorPaneLayout.createSequentialGroup()
                                            .addComponent(writMoveLeft)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(writMoveRight))
                                        .addComponent(writMoveDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap())
                );
                writCreatorPaneLayout.setVerticalGroup(
                    writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(writCreatorPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(writCreatorPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jToggleButton1)
                                    .addComponent(jToggleButton2)
                                    .addComponent(writMoveDown)))
                            .addGroup(writCreatorPaneLayout.createSequentialGroup()
                                .addComponent(writMoveUp)
                                .addGap(0, 0, 0)
                                .addGroup(writCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(writMoveLeft)
                                    .addComponent(writMoveRight))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );

                writCreatorPane.setBounds(0, 0, 250, 120);
                toolkitPane.add(writCreatorPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

                elevationCreatorPane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(0, 0, 0)));

                jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jLabel6.setText("Increase/decrease amount:");

                addSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                addSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));
                addSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                    public void stateChanged(javax.swing.event.ChangeEvent evt) {
                        addSpinnerStateChanged(evt);
                    }
                });

                setLeftSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                setLeftSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -999, 1000, 1));
                setLeftSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                    public void stateChanged(javax.swing.event.ChangeEvent evt) {
                        setLeftSpinnerStateChanged(evt);
                    }
                });

                jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jLabel7.setText("Set (LMB):");

                jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jLabel8.setText("Set (RMB):");

                setRightSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                setRightSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -999, 1000, 1));
                setRightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
                    public void stateChanged(javax.swing.event.ChangeEvent evt) {
                        setRightSpinnerStateChanged(evt);
                    }
                });

                upHeight.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                upHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                upHeight.setText("100");

                leftHeight.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                leftHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                leftHeight.setText("100");

                centerHeight.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                centerHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                centerHeight.setText("100");

                rightHeight.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                rightHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                rightHeight.setText("100");

                downHeight.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                downHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                downHeight.setText("100");

                javax.swing.GroupLayout elevationCreatorPaneLayout = new javax.swing.GroupLayout(elevationCreatorPane);
                elevationCreatorPane.setLayout(elevationCreatorPaneLayout);
                elevationCreatorPaneLayout.setHorizontalGroup(
                    elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addSpinner))
                            .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                        .addGap(142, 142, 142)
                                        .addComponent(leftHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(setLeftSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(setRightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                                .addGap(15, 15, 15)
                                .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(downHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(upHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                        .addComponent(centerHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(rightHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );
                elevationCreatorPaneLayout.setVerticalGroup(
                    elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(addSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(setLeftSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(upHeight, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(setRightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(elevationCreatorPaneLayout.createSequentialGroup()
                                .addGroup(elevationCreatorPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(centerHeight)
                                    .addComponent(leftHeight)
                                    .addComponent(rightHeight))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(downHeight)))
                        .addContainerGap(19, Short.MAX_VALUE))
                );

                elevationCreatorPane.setBounds(0, 0, 250, 120);
                toolkitPane.add(elevationCreatorPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

                javax.swing.GroupLayout interfacePaneLayout = new javax.swing.GroupLayout(interfacePane);
                interfacePane.setLayout(interfacePaneLayout);
                interfacePaneLayout.setHorizontalGroup(
                    interfacePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(interfacePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(floorDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(floorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(floorUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(contextPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(interfacePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(toolkitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                );
                interfacePaneLayout.setVerticalGroup(
                    interfacePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(interfacePaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(interfacePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(floorUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(floorDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(floorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(140, 140, 140)
                        .addComponent(contextPane, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                        .addGap(0, 0, 0))
                    .addGroup(interfacePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(interfacePaneLayout.createSequentialGroup()
                            .addGap(58, 58, 58)
                            .addComponent(toolkitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(372, Short.MAX_VALUE)))
                );

                jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jPanel1MouseMoved(evt);
                    }
                });

                jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/load.png"))); // NOI18N
                jButton4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jButton4MouseMoved(evt);
                    }
                });
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton4ActionPerformed(evt);
                    }
                });

                jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/save.png"))); // NOI18N
                jButton5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jButton5MouseMoved(evt);
                    }
                });
                jButton5.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton5ActionPerformed(evt);
                    }
                });

                jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/materials.png"))); // NOI18N
                jButton3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jButton3MouseMoved(evt);
                    }
                });
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton3ActionPerformed(evt);
                    }
                });

                jButton6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
                jButton6.setForeground(new java.awt.Color(255, 0, 0));
                jButton6.setText("Clear");
                jButton6.setEnabled(false);
                jButton6.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton6ActionPerformed(evt);
                    }
                });

                jCheckBox1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jCheckBox1ActionPerformed(evt);
                    }
                });

                jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/resize.png"))); // NOI18N
                jButton2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jButton2MouseMoved(evt);
                    }
                });
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton2ActionPerformed(evt);
                    }
                });

                jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/settings.png"))); // NOI18N
                jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jButton1MouseMoved(evt);
                    }
                });
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton1ActionPerformed(evt);
                    }
                });

                jToggleButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
                jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/fpsview.png"))); // NOI18N
                jToggleButton5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jToggleButton5MouseMoved(evt);
                    }
                });
                jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jToggleButton5ActionPerformed(evt);
                    }
                });

                wallsButton.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                wallsButton.setText("Walls: type");
                wallsButton.setToolTipText("");
                wallsButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        wallsButtonActionPerformed(evt);
                    }
                });

                helpLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

                jButton9.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
                jButton9.setText("Elevation: off");
                jButton9.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jButton9ActionPerformed(evt);
                    }
                });

                jToggleButton6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
                jToggleButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/fixedCamera.png"))); // NOI18N
                jToggleButton6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    public void mouseMoved(java.awt.event.MouseEvent evt) {
                        jToggleButton6MouseMoved(evt);
                    }
                });
                jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jToggleButton6ActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(wallsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButton9)
                        .addGap(2, 2, 2)
                        .addComponent(helpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBox1)
                        .addGap(0, 0, 0)
                        .addComponent(jButton6)
                        .addGap(2, 2, 2))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wallsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(helpLabel)
                            .addComponent(jButton9)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9))
                );

                javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
                mainPane.setLayout(mainPaneLayout);
                mainPaneLayout.setHorizontalGroup(
                    mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPaneLayout.createSequentialGroup()
                        .addComponent(interfacePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(programFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                );
                mainPaneLayout.setVerticalGroup(
                    mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPaneLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(programFrame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(interfacePane, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    Mapper mapper;
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        programFrame.setSize(getRootPane().getWidth()-250, getRootPane().getHeight()-82);
        mapper.resizeRequest();
    }//GEN-LAST:event_formComponentResized

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        programFrame.setSize(getRootPane().getWidth()-250, getRootPane().getHeight()-82);
        mapper.resizeRequest();
        if (this.getExtendedState()!=JFrame.MAXIMIZED_BOTH) {
            setSize(getWidth(), getHeight()-1);
            setSize(getWidth(), getHeight()+1);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ResizeWindow.main(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SettingsWindow.main(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void contextPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_contextPaneStateChanged
        JScrollPane pane = (JScrollPane) contextPane.getSelectedComponent();
        if (pane==groundsPane) {
            Mapper.currType=Lib.Object.Type.ground;
            flatsPane.setVisible(true);
            colorablePane.setVisible(false);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(false);
            if (groundsList.getSelectedValue() instanceof Ground) {
                Mapper.deleting=false;
                Mapper.gData = (Ground)groundsList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
        else if (pane==elevationPane) {
            Mapper.currType=Lib.Object.Type.elevation;
            flatsPane.setVisible(false);
            colorablePane.setVisible(false);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(true);
            if (elevationList.getSelectedValue() instanceof String) {
                Mapper.deleting=false;
                Mapper.eAction = (String)elevationList.getSelectedValue();
            }
        }
        else if (pane==writsPane) {
            Mapper.currType=Lib.Object.Type.writ;
            flatsPane.setVisible(false);
            colorablePane.setVisible(false);
            writCreatorPane.setVisible(true);
            elevationCreatorPane.setVisible(false);
            if (writsList.getSelectedValue() instanceof Writ) {
                Mapper.deleting=false;
                Mapper.wData = (Writ)writsList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
        else if (pane==floorsPane) {
            Mapper.currType=Lib.Object.Type.floor;
            flatsPane.setVisible(true);
            colorablePane.setVisible(false);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(false);
            if (floorsList.getSelectedValue() instanceof Data) {
                Mapper.deleting=false;
                Mapper.data = (Data)floorsList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
        else if (pane==wallsPane) {
            Mapper.currType=Lib.Object.Type.wall;
            flatsPane.setVisible(false);
            colorablePane.setVisible(true);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(false);
            if (wallsList.getSelectedValue() instanceof Data) {
                Mapper.deleting=false;
                Mapper.data = (Data)wallsList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
        else if (pane==roofsPane) {
            Mapper.currType=Lib.Object.Type.roof;
            flatsPane.setVisible(true);
            colorablePane.setVisible(false);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(false);
            if (roofsList.getSelectedValue() instanceof Data) {
                Mapper.deleting=false;
                Mapper.data = (Data)roofsList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
        else if (pane==structuresPane) {
            Mapper.currType=Lib.Object.Type.structure;
            flatsPane.setVisible(false);
            colorablePane.setVisible(true);
            writCreatorPane.setVisible(false);
            elevationCreatorPane.setVisible(false);
            if (structuresList.getSelectedValue() instanceof Structure) {
                Mapper.deleting=false;
                Mapper.sData = (Structure)structuresList.getSelectedValue();
            }
            else {
                Mapper.deleting=true;
            }
        }
    }//GEN-LAST:event_contextPaneStateChanged

    private void roofsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_roofsListValueChanged
        Mapper.currType=Lib.Object.Type.roof;
        if (roofsList.getSelectedValue() instanceof Data) {
            Mapper.deleting=false;
            Mapper.data = (Data)roofsList.getSelectedValue();
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_roofsListValueChanged

    private void wallsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_wallsListValueChanged
        Mapper.currType=Lib.Object.Type.wall;
        if (wallsList.getSelectedValue() instanceof Data) {
            Mapper.deleting=false;
            Mapper.data = (Data)wallsList.getSelectedValue();
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_wallsListValueChanged

    private void floorsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_floorsListValueChanged
        Mapper.currType=Lib.Object.Type.floor;
        if (floorsList.getSelectedValue() instanceof Data) {
            Mapper.deleting=false;
            Mapper.data = (Data)floorsList.getSelectedValue();
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_floorsListValueChanged

    private void groundsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_groundsListValueChanged
        Mapper.currType=Lib.Object.Type.ground;
        if (groundsList.getSelectedValue() instanceof Ground) {
            Mapper.deleting=false;
            if (Mapper.z>=0) {
                Mapper.gData = (Ground)groundsList.getSelectedValue();
            }
            else {
                Mapper.cData = (Ground)groundsList.getSelectedValue();
            }
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_groundsListValueChanged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        jButton6.setEnabled(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        reset();
    }//GEN-LAST:event_jButton6ActionPerformed

    public static void reset() {
        for (int i=0; i<Mapper.width; i++) {
            for (int i2=0; i2<Mapper.height; i2++) {
                Mapper.ground[i][i2] = D.grounds.get(0).copy(i, i2);
                Mapper.caveGround[i][i2] = D.caveGrounds.get(0).copy(i, i2);
                for (int i3=0; i3<15; i3++) {
                    Mapper.tiles[i][i2][i3]=null;
                    Mapper.bordersx[i][i2][i3]=null;
                    Mapper.bordersy[i][i2][i3]=null;
                    for (int i4=0; i4<4; i4++) {
                        for (int i5=0; i5<4; i5++) {
                            Mapper.objects[i*4+i4][i2*4+i5][i3]=null;
                        }
                    }
                }
            }
        }
        Mapper.updater.writUpdater.model.clear();
        Mapper.wData=null;
        HouseCalc.jTextField1.setText("Writ 1");
        jCheckBox1.setSelected(false);
        jButton6.setEnabled(false);
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        SaveWindow.main(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LoadWindow.main(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        MaterialsWindow.main(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void floorDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floorDownActionPerformed
        if (Mapper.z>=0) {
            Mapper.z--;
        }
        if (Mapper.z==-1) {
            HouseCalc.groundsList.setModel(DataLoader.caveGrounds);
            HouseCalc.groundsList.setSelectedIndex(0);
        }
        floorLabel.setText("Floor "+(Mapper.z+1));
        programFrame.requestFocus();
    }//GEN-LAST:event_floorDownActionPerformed

    private void floorUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floorUpActionPerformed
        if (Mapper.z<15-1) {
            Mapper.z++;
        }
        if (Mapper.z==0) {
            HouseCalc.groundsList.setModel(DataLoader.grounds);
            HouseCalc.groundsList.setSelectedIndex(0);
        }
        floorLabel.setText("Floor "+(Mapper.z+1));
        programFrame.requestFocus();
    }//GEN-LAST:event_floorUpActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        Mapper.fpsView = jToggleButton5.isSelected();
        floorDown.setEnabled(!jToggleButton5.isSelected());
        floorUp.setEnabled(!jToggleButton5.isSelected());
        floorLabel.setEnabled(!jToggleButton5.isSelected());
        contextPane.setEnabled(!jToggleButton5.isSelected());
        groundsList.setEnabled(!jToggleButton5.isSelected());
        floorsList.setEnabled(!jToggleButton5.isSelected());
        wallsList.setEnabled(!jToggleButton5.isSelected());
        roofsList.setEnabled(!jToggleButton5.isSelected());
        jButton4.setEnabled(!jToggleButton5.isSelected());
        wallsButton.setEnabled(!jToggleButton5.isSelected());
        containerLoop(toolkitPane);
        programFrame.requestFocus();
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void containerLoop(Container cont) {
        cont.setEnabled(!jToggleButton5.isSelected());
        for (Component c : cont.getComponents()) {
            if (c instanceof Container) {
                containerLoop((Container) c);
            }
            c.setEnabled(!jToggleButton5.isSelected());
        }
    }
    
    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        brushSize = (int)jSpinner1.getModel().getValue()-1;
    }//GEN-LAST:event_jSpinner1StateChanged

    private void pencilToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pencilToggleActionPerformed
        pencilToggle.setSelected(true);
        fillToggle.setSelected(false);
        selectToggle.setSelected(false);
        drawMode = DrawMode.pencil;
    }//GEN-LAST:event_pencilToggleActionPerformed

    private void fillToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillToggleActionPerformed
        pencilToggle.setSelected(false);
        fillToggle.setSelected(true);
        selectToggle.setSelected(false);
        drawMode = DrawMode.fill;
    }//GEN-LAST:event_fillToggleActionPerformed

    private void selectToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectToggleActionPerformed
        pencilToggle.setSelected(false);
        fillToggle.setSelected(false);
        selectToggle.setSelected(true);
        drawMode = DrawMode.select;
    }//GEN-LAST:event_selectToggleActionPerformed

    private void rSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rSpinnerStateChanged
        r = (double)rSpinner.getModel().getValue();
    }//GEN-LAST:event_rSpinnerStateChanged

    private void gSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_gSpinnerStateChanged
        g = (double)gSpinner.getModel().getValue();
    }//GEN-LAST:event_gSpinnerStateChanged

    private void bSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bSpinnerStateChanged
        b = (double)bSpinner.getModel().getValue();
    }//GEN-LAST:event_bSpinnerStateChanged

    private void wallsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallsButtonActionPerformed
        switch (paintMode) {
            case type:
                wallsButton.setText("Walls: paint");
                paintMode = PaintMode.paint;
                break;
            case paint:
                wallsButton.setText("Walls: cycle");
                paintMode = PaintMode.cycle;
                break;
            case cycle:
                wallsButton.setText("Walls: type");
                paintMode = PaintMode.type;
                break;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_wallsButtonActionPerformed

    private void jButton4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseMoved
        helpLabel.setText("Load");
    }//GEN-LAST:event_jButton4MouseMoved

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        helpLabel.setText("");
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jButton5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseMoved
        helpLabel.setText("Save");
    }//GEN-LAST:event_jButton5MouseMoved

    private void jButton3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseMoved
        helpLabel.setText("Calculate");
    }//GEN-LAST:event_jButton3MouseMoved

    private void jToggleButton5MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton5MouseMoved
        helpLabel.setText("2d/3d view");
    }//GEN-LAST:event_jToggleButton5MouseMoved

    private void jButton2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseMoved
        helpLabel.setText("Resize");
    }//GEN-LAST:event_jButton2MouseMoved

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        helpLabel.setText("Settings");
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (!jTextField1.getText().equals("")) {
            Mapper.updater.writUpdater.model.addElement(new Writ(jTextField1.getText()));
            int cap = Mapper.updater.writUpdater.model.getSize()+1;
            jTextField1.setText("Writ "+cap);
            writsList.setSelectedIndex(Mapper.updater.writUpdater.model.getSize()-1);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (!writsList.isSelectionEmpty()) {
            Mapper.updater.writUpdater.deleteWrit();
            int cap = Mapper.updater.writUpdater.model.getSize()+1;
            jTextField1.setText("Writ "+cap);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        jToggleButton1.setSelected(true);
        jToggleButton2.setSelected(false);
        writMode = WritMode.add;
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        jToggleButton1.setSelected(false);
        jToggleButton2.setSelected(true);
        writMode = WritMode.delete;
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void writsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_writsListValueChanged
        Mapper.currType=Lib.Object.Type.writ;
        if (writsList.getSelectedValue() instanceof Writ) {
            Mapper.deleting=false;
            Mapper.wData = (Writ)writsList.getSelectedValue();
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_writsListValueChanged

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        lockAxis = jCheckBox2.isSelected();
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void elevationListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_elevationListValueChanged
        Mapper.currType=Lib.Object.Type.elevation;
        if (elevationList.getSelectedValue() instanceof String) {
            Mapper.deleting=false;
            Mapper.eAction = (String)elevationList.getSelectedValue();
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_elevationListValueChanged

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (renderHeight) {
            jButton9.setText("Elevation: off");
            renderHeight = false;
        }
        else {
            jButton9.setText("Elevation: on");
            renderHeight = true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void addSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addSpinnerStateChanged
        elevationAdd = (int)addSpinner.getModel().getValue();
    }//GEN-LAST:event_addSpinnerStateChanged

    private void setLeftSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_setLeftSpinnerStateChanged
        elevationSetLeft = (int)setLeftSpinner.getModel().getValue();
    }//GEN-LAST:event_setLeftSpinnerStateChanged

    private void setRightSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_setRightSpinnerStateChanged
        elevationSetRight = (int)setRightSpinner.getModel().getValue();
    }//GEN-LAST:event_setRightSpinnerStateChanged

    private void writMoveRightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_writMoveRightMouseClicked
        if (Mapper.wData!=null) {
            Mapper.updater.writUpdater.move(1, 0);
        }
    }//GEN-LAST:event_writMoveRightMouseClicked

    private void writMoveLeftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_writMoveLeftMouseClicked
        if (Mapper.wData!=null) {
            Mapper.updater.writUpdater.move(-1, 0);
        }
    }//GEN-LAST:event_writMoveLeftMouseClicked

    private void writMoveDownMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_writMoveDownMouseClicked
        if (Mapper.wData!=null) {
            Mapper.updater.writUpdater.move(0, -1);
        }
    }//GEN-LAST:event_writMoveDownMouseClicked

    private void writMoveUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_writMoveUpMouseClicked
        if (Mapper.wData!=null) {
            Mapper.updater.writUpdater.move(0, 1);
        }
    }//GEN-LAST:event_writMoveUpMouseClicked

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        FPPCamera.fixedHeight = jToggleButton6.isSelected();
    }//GEN-LAST:event_jToggleButton6ActionPerformed

    private void jToggleButton6MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton6MouseMoved
        helpLabel.setText("Fixed cam");
    }//GEN-LAST:event_jToggleButton6MouseMoved

    private void structuresListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_structuresListValueChanged
        Mapper.currType=Lib.Object.Type.structure;
        if (structuresList.getSelectedValue() instanceof Structure) {
            Mapper.deleting=false;
            Mapper.sData = (Structure)structuresList.getSelectedValue();
        }
        else {
            Mapper.deleting=true;
        }
        programFrame.requestFocus();
    }//GEN-LAST:event_structuresListValueChanged

    public static void error() {
        if (!error) {
            error=true;
            StatusBar.errorLabel.setVisible(true);
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
            java.util.logging.Logger.getLogger(HouseCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HouseCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HouseCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HouseCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        if (args.length>0) {
            mapStr = args[0];
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HouseCalc().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JSpinner addSpinner;
    private javax.swing.JSpinner bSpinner;
    public static javax.swing.JLabel centerHeight;
    private javax.swing.JPanel colorablePane;
    public static javax.swing.JTabbedPane contextPane;
    public static javax.swing.JLabel downHeight;
    private javax.swing.JPanel elevationCreatorPane;
    public static javax.swing.JList elevationList;
    public static javax.swing.JScrollPane elevationPane;
    public static javax.swing.JToggleButton fillToggle;
    private javax.swing.JPanel flatsPane;
    private javax.swing.JButton floorDown;
    public static javax.swing.JLabel floorLabel;
    private javax.swing.JButton floorUp;
    public static javax.swing.JList floorsList;
    public static javax.swing.JScrollPane floorsPane;
    private javax.swing.JSpinner gSpinner;
    public static javax.swing.JList groundsList;
    public static javax.swing.JScrollPane groundsPane;
    private javax.swing.JLabel helpLabel;
    private javax.swing.JPanel interfacePane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    public static javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    public static javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    public static javax.swing.JLabel leftHeight;
    public static javax.swing.JPanel mainPane;
    public static javax.swing.JToggleButton pencilToggle;
    public static java.awt.Canvas programFrame;
    private javax.swing.JSpinner rSpinner;
    public static javax.swing.JLabel rightHeight;
    public static javax.swing.JList roofsList;
    public static javax.swing.JScrollPane roofsPane;
    public static javax.swing.JToggleButton selectToggle;
    public static javax.swing.JSpinner setLeftSpinner;
    public static javax.swing.JSpinner setRightSpinner;
    public static Form.StatusBar statusBar;
    public static javax.swing.JList structuresList;
    public static javax.swing.JScrollPane structuresPane;
    private javax.swing.JLayeredPane toolkitPane;
    public static javax.swing.JLabel upHeight;
    private javax.swing.JButton wallsButton;
    public static javax.swing.JList wallsList;
    public static javax.swing.JScrollPane wallsPane;
    private javax.swing.JPanel writCreatorPane;
    private javax.swing.JLabel writMoveDown;
    private javax.swing.JLabel writMoveLeft;
    private javax.swing.JLabel writMoveRight;
    private javax.swing.JLabel writMoveUp;
    public static javax.swing.JList writsList;
    public static javax.swing.JScrollPane writsPane;
    // End of variables declaration//GEN-END:variables
}
