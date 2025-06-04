package WordGuessingGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLogic game = new GameLogic();
        
        while (true) { 
            System.out.println("\n=== Welcome to Word Guesser ===");
            System.out.println("1. New Game");
            System.out.println("2. How to Play");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    game.startGameLoop();
                    break;
                case "2":
                    displayInstructions();
                    break;
                case "3":
                    System.out.println("Thanks for playing!");
                    scanner.close();
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void displayInstructions() {
        System.out.println("\n=== How to Play ===");
        System.out.println("1. Choose a difficulty level (Easy, Medium, Hard)");
        System.out.println("2. Try to guess the hidden word by entering letters");
        System.out.println("3. You have 6 attempts to guess the word");
        System.out.println("4. Commands:");
        System.out.println("   - 'hint' - get a helpful hint");
        System.out.println("   - 'undo' - undo your last guess");
        System.out.println("   - 'restart' - start a new game");
        System.out.println("   - 'quit' - exit the game");
        System.out.println("\nGood luck!\n");
        System.out.println("Press Enter to return to the main menu...");
        new Scanner(System.in).nextLine();  // Wait for user to press Enter
    }
}