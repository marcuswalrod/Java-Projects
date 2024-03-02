// imports
import java.util.Scanner;

public class Envelope {
    String name; //Name of envelope
    double amt; //Amount in envelope
    String draw; //Envelope to draw from in event of overage

    public Envelope() {
        name = "Envelope";
        amt = 0.00;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void getName() {
        System.out.println(this.name);
    }

    static void CreateEnvelope() {
        Envelope newEnvelope = new Envelope();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter Envelope Name: ");
        String newName = userInput.nextLine();
        userInput.close();
        newEnvelope.setName(newName);

    }
}