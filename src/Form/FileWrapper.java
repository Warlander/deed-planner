package Form;

import java.io.File;

public class FileWrapper {
    
    public File file;
    
    public FileWrapper(String path) {
        file = new File(path);
    }
    public FileWrapper(File file) {
        this.file = file;
    }
    
    public String toString() {
        return file.getName().replace(".MAP", "");
    }
    
}
