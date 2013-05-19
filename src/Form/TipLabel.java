package Form;

import java.util.ArrayList;
import javax.swing.JLabel;

public class TipLabel extends JLabel {
    
    private ArrayList<String> tipsList;
    private int currentTip=0;
    
    private final int ticks = 450;
    private int timer = 0;
    
    public TipLabel() {
        tipsList = new ArrayList<>();
        tipsList.add("You can use shift and control keys to modify movement speed.");
        tipsList.add("You cannot place floors and roofs outside writ.");
        tipsList.add("Use fill option for floors to quickly fill the whole writ.");
        tipsList.add("If program is running slow turn off antialiasing and restart.");
        tipsList.add("Program will notify you when new version comes out.");
        tipsList.add("You cannot plan roofs on the first floor.");
        tipsList.add("Maximal allowed deed size is 1000X1000.");
        tipsList.add("Minimal allowed deed size is 25X25.");
        tipsList.add("You can modify pallete through \"Objects.txt\" file.");
        tipsList.add("Try \"Wurm Assistant\" program by Aldur!");
        tipsList.add("You can calculate writ materials while writ is selected.");
        tipsList.add("You can post suggestions and bugs in program official thread.");
        tipsList.add("This program is made in Java and LWJGL!");
        tipsList.add("You can edit tile, border or point height.");
        setTip();
    }
    
    private void setTip() {
        int tip = (int)(Math.random()*tipsList.size());
        while (tip==currentTip) {
            tip = (int)(Math.random()*tipsList.size());
        }
        currentTip = tip;
        setText(tipsList.get(currentTip));
    }
    
    public void tick() {
        timer++;
        if (timer==ticks) {
            timer=0;
            setTip();
        }
    }
    
}
