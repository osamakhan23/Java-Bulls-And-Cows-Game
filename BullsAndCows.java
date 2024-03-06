import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the main game class for the Bulls and Cows game.
 * It manages the game state, processes user input, and handles AI opponents.
 * The class interacts with the following other classes:
 * - Players: Represents both the user and AI opponents in the game.
 * - User: A subclass of Players representing the human player.
 * - AIOpponent: A subclass of Players representing the AI opponent.
 * - Guess: Represents a single guess made by a player and holds the methods to calculate cows and bulls.
 * <p>
 * The BullsAndCows class is responsible for:
 * 1. Initializing the game state and setting up the players.
 * 2. Running the main game loop, which includes prompting user input, processing AI guesses, and checking for game completion.
 * 3. Saving the game results to a file, if the user chooses to do so.
 */

public class BullsAndCows {
    // Class variables to store computer and user players, user and AI guesses, and other game state variables
    private Players computerPlayer;
    private Players userPlayer;
    private Guess userCurrentGuess;
    private Guess aiCurrentGuess;
    private int guessCount;
    private boolean userWon;
    private boolean aiWon;
    private Guess[] previousGuesses;
    private static final int MAX_TURNS = 7;

    public BullsAndCows() {
        this(new AIOpponent(), null);
    }

    public BullsAndCows(AIOpponent aiOpponent) {
        this(aiOpponent, null);
    }

    public BullsAndCows(AIOpponent aiOpponent, String configFilePath) {
        if (configFilePath != null && aiOpponent instanceof EasyAI) {
            this.computerPlayer = new EasyAI(configFilePath);
            this.previousGuesses = new Guess[((EasyAI) computerPlayer).getMaxTurns()];
        } else {
            this.computerPlayer = aiOpponent;
            this.previousGuesses = new Guess[MAX_TURNS];
        }
        this.userPlayer = new Users();
        this.userCurrentGuess = new Guess("");
        this.aiCurrentGuess = new Guess("");
        this.guessCount = 0;
        this.userWon = false;
        this.aiWon = false;
    }

    // Function to save the game results to a file
    public void saveGameResultsToFile(String filename, ArrayList<String> gameResults) throws IOException {
        // Add the .txt extension to the filename if not present
        if (!filename.endsWith(".txt")) {
            filename += ".txt";
        }
        // Create a FileWriter to write the game results to the file
        FileWriter writer = new FileWriter(filename);

        // Write the secret codes and the list of guesses to the file
        writer.write("Player's secret code: " + userPlayer.getSecretCode() + "\n");
        writer.write("Computer's secret code: " + computerPlayer.getSecretCode() + "\n\n");

        writer.write("Guesses:\n");
        for (String result : gameResults) {
            writer.write(result);
        }
        writer.write("\n");

        // Write the final game result to the file
        if (userWon) {
            writer.write("Congratulations! You have guessed the secret code!");
        } else if (aiWon) {
            writer.write("The AI opponent has guessed your secret code!");
        } else {
            writer.write("It's a draw! Both players have run out of guesses.");
        }

        // Close the FileWriter
        writer.close();

        // Print a message to inform the user that the game results have been saved
        System.out.println("Game results saved to file: " + filename);
        GameLauncher.printDoubleLine();
    }

    // Function to start the game with the given input method and list of pre-supplied guesses
    public void startGame(Scanner scanner, boolean usePreSuppliedGuesses, ArrayList<String> preSuppliedGuesses) {
        // Generate a secret code for the computer player
        computerPlayer.generateSecretCode();

        // Prompt the user to enter their secret code
        System.out.print("Enter your secret code: ");
        String userSecretCode;

        // Validate the user's secret code and ask for a new one if it's invalid
        do {
            userSecretCode = scanner.nextLine();
            if (!userCurrentGuess.isValidGuess(userSecretCode)) {
                System.out.print("Invalid guess! Please enter a valid 4-digit code with unique digits: ");
            }
        } while (!userCurrentGuess.isValidGuess(userSecretCode));

        // Set the user's secret code and print a double line
        userPlayer.setSecretCode(userSecretCode);
        GameLauncher.printDoubleLine();

        // Initialize turn count and game result flags
        int turnCount = 0;
        userWon = false;
        aiWon = false;

        // Create an ArrayList to store the game results
        ArrayList<String> gameResults = new ArrayList<>();

        // Initialize an index to keep track of pre-supplied guesses
        int preSuppliedGuessesIndex = 0;

        int maxTurns = MAX_TURNS;
        if (computerPlayer instanceof EasyAI) {
            maxTurns = ((EasyAI) computerPlayer).getMaxTurns();
        }

        // Game loop: continue while there are turns left and neither player has won
        while (turnCount < maxTurns && !userWon && !aiWon) {
            turnCount++;

            String guess;

            // Check if pre-supplied guesses are being used and there are still pre-supplied guesses left
            if (usePreSuppliedGuesses && preSuppliedGuessesIndex < preSuppliedGuesses.size()) {
                // Use the next pre-supplied guess
                guess = preSuppliedGuesses.get(preSuppliedGuessesIndex++);
                System.out.println("Using pre-supplied Guess: " + guess);
            } else {
                // Prompt the user to enter their guess
                System.out.print("Enter your guess: ");
                guess = scanner.nextLine();
                GameLauncher.printDoubleLine();
            }

            // Create a new Guess object for the user's guess and calculate the cows and bulls
            userCurrentGuess = new Guess(guess);
            userCurrentGuess.calculateCowsAndBulls(computerPlayer.getSecretCode());

            // Check if the user's guess is correct
            if (userCurrentGuess.isCorrect(computerPlayer.getSecretCode())) {
                userWon = true;
            } else {
                // Print the user's guess and the cows and bulls for that guess
                System.out.println("User's Guess: " + guess);
                System.out.println("Cows: " + userCurrentGuess.getCows() + ", Bulls: " + userCurrentGuess.getBulls());
                System.out.println(" ");
            }

            // Add the user's guess and results to the gameResults list
            gameResults.add("Guess #" + turnCount + " (Player): " + guess + " (Cows: " + userCurrentGuess.getCows() + ", Bulls: " + userCurrentGuess.getBulls() + ")\n");

            // If the user hasn't won yet, let the AI make a guess
            if (!userWon) {
                // Make a guess using the AI opponent's method
                aiCurrentGuess = ((AIOpponent) computerPlayer).makeAGuess();

                // Print the AI's guess and calculate the cows and bulls
                System.out.println("AI's Guess: " + aiCurrentGuess.getGuess());
                aiCurrentGuess.calculateCowsAndBulls(userPlayer.getSecretCode());

                // Check if the AI's guess is correct
                if (aiCurrentGuess.isCorrect(userPlayer.getSecretCode())) {
                    aiWon = true;
                } else {
                    // Print the cows and bulls for the AI's guess
                    System.out.println("Cows: " + aiCurrentGuess.getCows() + ", Bulls: " + aiCurrentGuess.getBulls());
                    GameLauncher.printDoubleLine();
                }

                // Add the AI's guess and results to the gameResults list
                gameResults.add("Guess #" + turnCount + " (AI): " + aiCurrentGuess.getGuess() + " (Cows: " + aiCurrentGuess.getCows() + ", Bulls: " + aiCurrentGuess.getBulls() + ")\n");
            }
        }

        // Print the final game result based on userWon and aiWon flags
        if (userWon) {
            System.out.println("\033[1;36mCongratulations! You have guessed the secret code!\033[0m");
        } else if (aiWon) {
            System.out.println("\033[1;36mThe AI opponent has guessed your secret code!\033[0m");
        } else {
            System.out.println("\033[1;36mIt's a draw! Both players have run out of guesses.\033[0m");
        }

        // Ask the user if they want to save the game results to a file
        System.out.print("Do you want to save the game results to a file? (Y/N): ");
        String saveGameResults = scanner.nextLine();

        // If the user wants to save the game results
        if (saveGameResults.equalsIgnoreCase("Y")) {
            // Prompt the user for a filename
            System.out.print("Enter filename to save game results: ");
            String filename = scanner.nextLine();

            // Try to save the game results to the specified file
            try {
                saveGameResultsToFile(filename, gameResults);
            } catch (IOException e) {
                // Print an error message if there's a problem writing to the file
                System.out.println("Error while writing to file: " + filename);
            }
        }

        // Print a thank-you message and a decorative border
        System.out.println("Thank you for playing Bulls and Cows!");
        System.out.println("\033[1;36m+" + "=".repeat(40) + "+\n|" + "-".repeat(15) + " * * * " + "-".repeat(15) + "|\n+" + "=".repeat(40) + "+\033[0m");

    }

}