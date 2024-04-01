import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Scanner;


public class MoneyManager {
    String importData = ReadJSONFile("envelopes.json");
    JSONObject UserEnvelopes = new JSONObject(importData); 
    HashMap<String, Double> Envelopes = new HashMap<String, Double>();
    String userName;
    Boolean hasUserName = false;
    

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        MoneyManager user = new MoneyManager();
        //import user data
        user.JSONToHash();
        //Test Envelope
        user.Envelopes.put("test", 3000.00);
        
        user.userName = user.ReadFile("userdata.txt");
        if (user.userName != null && !user.userName.isEmpty()) {
            user.hasUserName = true;
        }

        user.Greet(sc);
        Boolean run = true;

        while (run) {     
            user.ListEnvelopes();
            System.out.println("\nWhat would you like to do?");
            System.out.println("\nExit: (0)\nCreate Envelope: (1)\nSelect Envelope: (2)\nSave: (3)\nUnknown: (4)\nUnkown: (5)\n");
            int select = sc.nextInt();
            switch (select) {
                case 0:
                    System.out.println("Have a great day.");
                    user.SaveFile();
                    run = false;
                    break;
                case 1:
                    user.CreateEnvelope(sc);
                    break;
                case 2:
                    user.SelectEnvelope(sc);
                    break;
                case 3:
                    user.SaveFile();
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
            try {
                FileWriter myWriter = new FileWriter("userData.txt");
                myWriter.write(userName);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }
        }
    }
    
    public void CreateEnvelope(Scanner sc) {
        System.out.println("Enter envelope name: ");
        String input = sc.next();
        System.out.println("Enter envelope amount: ");
        double amt = sc.nextDouble();
        Envelopes.put(input, amt);
        sc.nextLine();
        SaveFile();
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
                    UserEnvelopes.remove(key);
                    System.out.println("\nSuccessfully deleted " + key + ".");
                }
                break;

        }
        SaveFile();
    }

    //Read JSON
    private static String ReadJSONFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("An error occurred while reading the JSON file.");
            e.printStackTrace();
            return null;
        }
    }

    public String ReadFile(String fileName) {
        String data = "";
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    public void JSONToHash() {
        for (String key : UserEnvelopes.keySet()) {
            double value = UserEnvelopes.getDouble(key);
            Envelopes.put(key, value);
        }
    }

    public void SaveFile() {
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
    

    
