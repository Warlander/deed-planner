package Server;

import Entities.Client;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputProcessor {
    
    protected InputProcessor() {}
    
    protected void process(Scanner input) {
        String command = input.next();
        System.out.println("Processing command: "+command);
        switch(command) {
            case "help":
                help();
                break;
            case "clear":
                clear();
                break;
            case "exit":
                System.exit(0);
                break;
            case "mapinfo":
                System.out.println("Width: "+Kernel.width+", height: "+Kernel.height);
                break;
            case "resize":
                try {
                    int left = input.nextInt();
                    int right = input.nextInt();
                    int top = input.nextInt();
                    int bottom = input.nextInt();
                    if (MapUtils.resizeMap(left, right, top, bottom)) {
                        System.out.println("Map resized, current width and height: "+Kernel.width+", "+Kernel.height);
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input values!");
                }
                break;
            case "clearmap":
                MapUtils.reset();
                break;
            case "save":
                try {
                    SaveSystem.saveMaker(input.nextLine());
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "load":
                try {
                    LoadSystem.load1_0(new BufferedReader(new FileReader(input.nextLine())));
                } catch (IOException | InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "info":
                System.out.println();
                System.out.println("Spectator password: "+Kernel.spectatorPass);
                System.out.println("Editor password: "+Kernel.editorPass);
                System.out.println("Server refresh rate: "+Kernel.lag);
                System.out.println("Server port: "+Kernel.port);
                System.out.println("Server IP: "+Kernel.ip.getHostAddress());
                break;
            case "lag":
                try {
                    Kernel.lag = input.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "spectatorpass":
                try {
                    Kernel.spectatorPass = input.next();
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "editorpass":
                try {
                    Kernel.editorPass = input.next();
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "port":
                if (Kernel.serverStarted) {
                    System.out.println("Cannot change port while server is running!");
                    break;
                }
                try {
                    Kernel.port = input.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "ip":
                if (Kernel.serverStarted) {
                    System.out.println("Cannot change IP while server is running!");
                    break;
                }
                try {
                    Kernel.ip = InetAddress.getByName(input.next());
                } catch (InputMismatchException | UnknownHostException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "startserver":
                if (!Kernel.connectionsListener.isAlive()) {
                    Kernel.connectionsListener.start();
                    Kernel.clientsManager.start();
                }
                else {
                    System.out.println("Server is already started!");
                }
                break;
            case "closeserver":
                Kernel.connectionsListener.interrupt();
                Kernel.clientsManager.interrupt();
                SaveSystem.saveMaker("Backup.MAP");
                System.exit(0);
                break;
            case "userlist":
                System.out.println();
                if (Kernel.clients.isEmpty()) {
                    System.out.println("No active users");
                    break;
                }
                for (Client c : Kernel.clients) {
                    System.out.println(c.name+", editor: "+c.op);
                }
                System.out.println();
                break;
            case "op":
                try {
                    String name = input.next();
                    for (Client c : Kernel.clients) {
                        if (c.name.equals(name)) {
                            if (c.op) {
                                System.out.println(name+" already have editor status!");
                                break;
                            }
                            c.op = true;
                            System.out.println(name+" current status: editor");
                            break;
                        }
                    }
                    System.out.println("Cannot find user with "+name+" nick");
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "deop":
                try {
                    String name = input.next();
                    for (Client c : Kernel.clients) {
                        if (c.name.equals(name)) {
                            if (!c.op) {
                                System.out.println(name+" already have spectator status!");
                                break;
                            }
                            c.op = false;
                            System.out.println(name+" current status: spectator");
                            break;
                        }
                    }
                    System.out.println("Cannot find user with "+name+" nick");
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input value!");
                }
                break;
            case "kick":
                break;
            default:
                System.out.println("Unrecognized command");
                break;
        }
    }
    
    private void help() {
        System.out.println();
        System.out.println("Arguments explaination:");
        System.out.println();
        System.out.println("|Argument| = argument must be a string");
        System.out.println("<Argument> = argument must be an integer");
        System.out.println();
        System.out.println("Utility commands:");
        System.out.println();
        System.out.println("help - display this message");
        System.out.println("clear - visually clears the console screen");
        System.out.println("exit - forces server to close - in most cases this is better to use \"closeserver\" command");
        System.out.println();
        System.out.println("Map commands:");
        System.out.println();
        System.out.println("mapinfo - returns current map width and height");
        System.out.println("resize <left> <right> <top> <bottom> - resize current map");
        System.out.println("clearmap - clears the current map - WARNING, cannot be reversed!");
        System.out.println("save |path| - save map to the \"path\" file");
        System.out.println("load |path| - load map from the \"path\" file");
        System.out.println();
        System.out.println("Server commands:");
        System.out.println();
        System.out.println("info - returns info about the server");
        System.out.println("lag <miliseconds> - sets server refresh rate - optimal is 100 or 50, default: 100");
        System.out.println("spectatorpass |password| - sets password for spectators, default: spectator");
        System.out.println("editorpass |password| - sets password for editors, default: editor");
        System.out.println("ip |ip| - sets current IP adress, default: local host");
        System.out.println("port <port> - sets current port, default: 6978");
        System.out.println("startserver - starts the server");
        System.out.println("closeserver - close the server");
        System.out.println();
        System.out.println("Users commands:");
        System.out.println();
        System.out.println("userlist - shows active users");
        System.out.println("op |username| - grants editor status to the \"username\" user");
        System.out.println("deop |username| - grants spectator status to the \"username\" user");
        System.out.println("kick |username| - kicks \"username\" from server");
        System.out.println();
    }
    
    private void clear() {
        for (int i=0; i<100; i++) {
            System.out.println();
        }
    }
    
}
