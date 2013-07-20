package Mapper;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.net.Socket;

public class Server {

    public static boolean running = false;
    
    public static Socket socket;
    public static BufferedReader in;
    public static PrintStream out;
    
    public static StringBuilder builder;
    
}
