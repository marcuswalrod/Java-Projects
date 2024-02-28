import java.io.File;
import java.io.IOException;

public class CreateFile {
    public static void main(String[] args) {
      try {
        File userData = new File("userData.txt");
        if (userData.createNewFile()) {
          System.out.println("File created: " + userData.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }    
  }