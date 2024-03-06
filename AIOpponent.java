import java.util.ArrayList;

/**
 * The AIOpponent class is a subclass of the Players class and represents the computer player in
 * the Bulls and Cows game. It provides additional functionality to generate and manage the AI
 * opponent's guesses during the game. This class includes methods for making a guess, checking if
 * a guess has been repeated, and adding a guess to the previous guesses array.

 * The AIOpponent class is used by the BullsAndCows class to manage the AI opponent's game state
 * and generate guesses as the game progresses.
 */
public class AIOpponent extends Players {
    // Maximum number of guesses allowed.
    private static final int MAX_GUESSES = 7;
    // Keeps track of the number of guesses made.
    private int guessCount;
    // Stores previous guesses made by the AI opponent.
    private ArrayList<Guess> previousGuesses;

    // Constructor to initialize the AI opponent's attributes.
    public AIOpponent() {
        guessCount = 0;
        previousGuesses = new ArrayList<>(MAX_GUESSES);
    }

    // Generates a new guess for the AI opponent, ensuring that it's not a repeated guess.
    public Guess makeAGuess() {
        String aiGuess;
        do {
            aiGuess = generateRandomGuess();
        } while (isRepeatedGuess(aiGuess, guessCount));

        Guess guess = new Guess(aiGuess);
        addGuess(guess);
        return guess;
    }

    // Adds a new guess to the AI opponent's previous guesses.
    protected void addGuess(Guess guess) {
        previousGuesses.add(guess);
        guessCount++;
    }


    // Checks if the given guess is already in the AI opponent's previous guesses.
    protected boolean isRepeatedGuess(String guess, int guessCount) {
        for (int i = 0; i < guessCount; i++) {
            if (previousGuesses.get(i).getGuess().equals(guess)) {
                return true;
            }
        }
        return false;
    }
    protected ArrayList<Guess> getPreviousGuesses() {
        return previousGuesses;
    }


    // Returns the number of guesses made by the AI opponent.
    protected int getGuessCount() {
        return guessCount;
    }
}