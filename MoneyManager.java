import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Scanner;


public class MoneyManager {

    HashMap<String, Double> Envelopes = new HashMap<String, Double>();
    JSONObject UserEnvelopes = new JSONObject();

    String userDataFile = "envelopes.json";
    String importData = ReadFile(userDataFile);
    

    //Username check from JSON
    String userName;
    Boolean hasUserName = false;

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        MoneyManager user = new MoneyManager();
        //import user data

        //Test Envelope
        user.Envelopes.put("test", 3000.00);

        user.Greet(sc);
        Boolean run = true;

        while (run) {     
            user.ListEnvelopes();
            System.out.println("\nMake a selection to continue.");
            System.out.println("\nExit: (0)\nCreate Envelope: (1)\nSelect Envelope: (2)\nSave: (3)\nUnknown: (4)\nUnkown: (5)\n");
            int select = sc.nextInt();
            switch (select) {
                case 0:
                    System.out.println("Goodbye.");
                    run = false;
                    break;
                case 1:
                    user.CreateEnvelope(sc);
                    break;
                case 2:
                    user.SelectEnvelope(sc);
                    break;
                case 3:
                    user.SaveToJSON();
            }
            
        }
        sc.close();
    }   

    public void Greet(Scanner sc) {
        if (hasUserName) {
            System.out.println("\nWelcome back " + userName + ".");
            //Would like to add motivation statement here...
        }
        else {
            System.out.println("\nWelcome to Desktop Budgeting App. Enter your name to get started.");
            System.out.print("My name is: ");
            userName = sc.next();
        }
    }
    
    public void CreateEnvelope(Scanner sc) {
        System.out.println("Enter envelope name: ");
        String input = sc.next();
        System.out.println("Enter envelope amount: ");
        double amt = sc.nextDouble();
        Envelopes.put(input, amt);
        sc.nextLine();
    }

    public void ListEnvelopes() {
        System.out.printf("%n%-10s %-10s%n", "NAME", "AMOUNT");
        System.out.println("-----------------------------------");
        for (String i : Envelopes.keySet()) {
            System.out.printf("%-10s %-10s%n", i, Envelopes.get(i));
        }
    }

    public void SelectEnvelope(Scanner sc) {
        ListEnvelopes();
        System.out.print("\nEnter envelope name: ");
        String key = sc.next();
        System.out.println("\nAdd income: (0)\nAdd expense: (1)\nDelete envelope: (2)\n");
        int select = sc.nextInt();
        double amt = 0;
        switch (select) {
            case 0:
                System.out.print("\nEnter amount: ");
                amt = sc.nextDouble();
                double total = Envelopes.get(key) + amt;
                Envelopes.put(key,total);
                break;
            case 1: 
                System.out.print("\nEnter amount: ");
                amt = sc.nextDouble();
                amt = amt * -1;
                total = Envelopes.get(key) + amt;
                Envelopes.put(key,total);
                break;
            case 2:
                System.out.println("Are you sure you want to delete " + key + "?");
                System.out.print("y/n: ");
                String selection = sc.next();
                if (selection.equals("y")) {
                    Envelopes.remove(key);
                    System.out.println("\nSuccessfully deleted " + key + ".");
                }
                break;

        }
        
    }

    public String ReadFile(String fileName) {
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileName;
    }

    public void SaveToJSON() {
        //JSON Object UserEnvelopes
        for (String key : Envelopes.keySet()) {
            UserEnvelopes.put(key, Envelopes.get(key));
        }
        try {
            FileWriter myWriter = new FileWriter("envelopes.json");
            myWriter.write(UserEnvelopes.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
}
    

    
