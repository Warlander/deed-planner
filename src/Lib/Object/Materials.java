package Lib.Object;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Materials {
    
    private int id;
    private HashMap<String, Integer> materials;
    
    public Materials(int id) {
        this.id = id;
        materials = new HashMap<>();
    }
    
    public int getID() {
        return id;
    }
    
    public void put(String key, Integer value) {
        materials.put(key, value);
    }
    
    public Set<Entry<String, Integer>> entrySet() {
        return materials.entrySet();
    }
    
    public Integer get(String key) {
        return materials.get(key);
    }
    
}
