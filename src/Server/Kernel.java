package Server;

import Entities.Client;
import Entities.Data;
import Entities.Ground;
import Entities.Label;
import Entities.Structure;
import Entities.Writ;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Kernel {
    
    protected static Ground[][] caveGround=new Ground[25][25];
    protected static Ground[][] ground=new Ground[25][25];
    protected static float[][] heightmap = new float[25+1][25+1];
    protected static Data[][][] tiles=new Data[25][25][15];
    protected static Structure[][][] objects = new Structure[100][100][15];
    protected static Label[][] labels = new Label[25][25];
    protected static Label[][] caveLabels = new Label[25][25];
    protected static Data[][][] bordersx=new Data[25][25][15];
    protected static Data[][][] bordersy=new Data[25][25][15];
    
    protected static ArrayList<Writ> writs = new ArrayList<>();
    
    protected static ArrayList<Client> undecided;
    protected static ArrayList<Client> clients;
    
    protected static int width = 25;
    protected static int height = 25;
    
    protected static boolean running = true;
    protected static boolean serverStarted = false;
    
    protected static int lag = 100;
    protected static int port = 6978;
    protected static InetAddress ip;
    protected static ServerSocket socket;
    protected static String spectatorPass = "spectator";
    protected static String editorPass = "editor";
    
    public static void main(String[] args) {
        new Kernel();
    }
    
    private Kernel() {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        
        try {
            FileOutputStream fstr = new FileOutputStream("ServerLog.txt");
            MultiOutputStream out = new MultiOutputStream(System.out, fstr);
            MultiOutputStream err = new MultiOutputStream(System.err, fstr);
            System.setOut(new PrintStream(out));
            System.setErr(new PrintStream(err));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        undecided = new ArrayList<>();
        clients = new ArrayList<>();
        
        System.out.println();
        System.out.println("DEED PLANNER");
        System.out.println("   SERVER");
        System.out.println();
        System.out.println("Loading server data");
        try {
            DataLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        System.out.println("Server data loaded!");
        System.out.println();
        System.out.println("Initializing server console");
        inputListener.start();
        System.out.println("Server console initialized!");
        System.out.println("Type \"help\" for list of all commands.");
    }
    
    protected static void close() {
        System.out.println("Closing");
        inputListener.interrupt();
        connectionsListener.interrupt();
        System.out.println("Server closed");
        System.exit(0);
    }
    
    private static Thread inputListener = new Thread() {
        public void run() {
            Scanner scan = new Scanner(System.in);
            InputProcessor input = new InputProcessor();
            while (running) {
                if (scan.hasNext()) {
                    input.process(scan);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    
    protected static Thread connectionsListener = new Thread() {
        public void run() {
            try {
                serverStarted = true;
                socket = new ServerSocket(port, 0, ip);
                System.out.println("Server started!");
                while (running) {
                    undecided.add(new Client(socket.accept()));
                    System.out.println("Incoming connection!");
                }
            } catch (IOException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    protected static Thread clientsManager = new Thread() {
        public void run() {
            while (running) {
                long time = System.currentTimeMillis();
                handleUndecided(time);
                handleClients(time);
                
                try {
                    Thread.sleep(lag);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    
    private static void handleUndecided(long time) {
        for (int i=0; i<undecided.size(); i++) {
            Client c = undecided.get(i);
            if (c.in.hasNextInt()) {
                if (c.in.nextInt()==2) {
                    c.decay = time+15000;
                    c.name = c.in.next();
                    String password = c.in.next();
                    if (password.equals(editorPass)) {
                        c.op = true;
                        undecided.remove(c);
                        sendMap(c.out);
                        clients.add(c);
                        i--;
                        System.out.println("Client logged in as "+c.name+", editor.");
                    }
                    else if (password.equals(spectatorPass)) {
                        c.op = false;
                        undecided.remove(c);
                        sendMap(c.out);
                        clients.add(c);
                        i--;
                        System.out.println("Client logged in as "+c.name+", spectator.");
                    }
                    else {
                        undecided.remove(c);
                        c.kill();
                        i--;
                        System.out.println("Wrong client password, connection terminated.");
                    }
                }
            }
            if (c.decay<time) {
                undecided.remove(c);
                c.kill();
                i--;
                System.out.println("Connection with client timed out");
            }
        }
    }
    
    private static void sendMap(PrintStream out) {
        out.println(2);
        SaveSystem.saveMaker(out);
        out.flush();
    }
    
    private static void handleClients(long time) {
        for (int i=0; i<clients.size(); i++) {
            Client c = clients.get(i);
            if (c.in.hasNextInt()) {
                String out="";
                try {
                    out = LoadSystem.readFragments(c.in);
                } catch (IOException ex) {
                    Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
                }
                c.decay = time+15000;
                
                if (c.op) {
                    for (Client send : clients) {
                        send.out.println(1);
                        send.out.print(out);
                        send.out.flush();
                    }
                }
            }
            if (c.decay<time) {
                clients.remove(c);
                System.out.println("Connection with "+c.name+" timed out");
                c.kill();
                i--;
            }
        }
    }
    
}
