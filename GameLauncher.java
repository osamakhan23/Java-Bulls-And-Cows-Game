import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class GameLauncher {
    // Customize the double line with ANSI escape codes for colors and styling
    public static final String cyanBold = "\033[1;36m";
    public static final String reset = "\033[0m";

    // This method reads pre-supplied guesses from a file and returns them as an ArrayList
    private static ArrayList<String> readPreSuppliedGuesses(String filename) {
        ArrayList<String> preSuppliedGuesses = new ArrayList<>();

        // Convert the filename to lowercase
        filename = filename.toLowerCase();

        // Add the .txt extension if it's not already present
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        // Read the file using a Scanner and add each line to the preSuppliedGuesses ArrayList
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                preSuppliedGuesses.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        return preSuppliedGuesses;
    }

    // This method prints a double line with cyan bold color and specific length for visual separation
    public static void printDoubleLine() {
        int borderLength = 42; // 40 equal signs + 2 plus signs
        String doubleLine = "+" + "=".repeat(borderLength) + "+";

        System.out.println(cyanBold + doubleLine + reset);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String gameName = "Bulls and Cows";
        String welcomeMessage = String.format("Welcome to the %s Game!", gameName);

        // Create the ASCII art border with spacing
        int borderLength = welcomeMessage.length() + 4; // 2 spaces on either side of message
        String border = "+" + "-".repeat(borderLength) + "+";

        // Create the centered message with spacing
        String centeredMessage = String.format("|  %-" + borderLength + "s|", welcomeMessage);

        // Print the border, centered message, and border again
        System.out.println(cyanBold + border + reset);
        System.out.println(cyanBold + centeredMessage + reset);
        System.out.println(cyanBold + border + reset);

        System.out.println("Please choose your input method:");
        System.out.println("1. Manual input");
        System.out.println("2. Read guesses from a file");
        System.out.print("Enter your choice (1-2): ");
        int inputChoice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        printDoubleLine();

        // Read pre-supplied guesses from a file if the user chose that option
        ArrayList<String> preSuppliedGuesses = null;
        if (inputChoice == 2) {
            System.out.print("Enter the filename containing your pre-supplied guesses (eg. input.txt): ");
            String filename;
            do {
                filename = scanner.nextLine();
                preSuppliedGuesses = readPreSuppliedGuesses(filename);
                if (preSuppliedGuesses.isEmpty()) {
                    System.out.print("Invalid filename or empty file. Please enter a valid filename: ");
                }
            } while (preSuppliedGuesses.isEmpty());
        }


        // Prompt the user to select an AI difficulty level
        System.out.println("Please select an AI difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Enter your choice (1-3): ");
        int aiDifficultyChoice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        printDoubleLine();

        // Create the AI opponent based on the user's choice
        AIOpponent aiPlayer;
        String easyAIConfigFilePath = Paths.get("easy_ai_config.txt").toString();
        switch (aiDifficultyChoice) {
            case 1:
                aiPlayer = new EasyAI(easyAIConfigFilePath);
                break;
            case 2:
                aiPlayer = new MediumAI();
                break;
            case 3:
                aiPlayer = new HardAI();
                break;
            default:
                // If the user enters an invalid choice, default to Easy AI
                System.out.println("Invalid choice. Defaulting to Easy AI.");
                aiPlayer = new EasyAI(easyAIConfigFilePath);
                break;
        }

        // Create a new instance of the BullsAndCows game with the chosen AI opponent
        BullsAndCows game = new BullsAndCows(aiPlayer);

        // Start the game with the appropriate input method:
        // - If the user chose to read guesses from a file (inputChoice == 2), pass the preSuppliedGuesses ArrayList
        // - If the user chose manual input (inputChoice == 1), pass null for preSuppliedGuesses
        if (inputChoice == 2) {
            game.startGame(scanner, true, preSuppliedGuesses);
        } else {
            game.startGame(scanner, false, null);
        }

        // Close the scanner to release system resources
        scanner.close();
    }
}
