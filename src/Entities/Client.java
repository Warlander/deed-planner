package Entities;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
   
    public long decay = System.currentTimeMillis()+15000;
    
    public String name;
    public boolean op;
    public Socket socket;
    
    public PrintStream out;
    
    public Scanner in;
    
    public Client (Socket socket) {
        try {
            this.socket = socket;
            out = new PrintStream(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void kill() {
        try {
            out.println(-1);
            out.flush();
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
