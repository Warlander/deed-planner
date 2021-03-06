package Lib.Utils;

public abstract class Repeater {

    private long time = System.currentTimeMillis();
    private final int repeatMillis;
    
    public Repeater(int repeatMillis) {
        this.repeatMillis = repeatMillis;
    }
    
    public void update() {
        if ((System.currentTimeMillis() - time)>repeatMillis) {
            time = System.currentTimeMillis();
            action();
        }
    }
    
    protected abstract void action();
    
}
