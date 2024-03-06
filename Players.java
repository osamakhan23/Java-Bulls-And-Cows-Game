import java.util.Random;

/**
 * Represents a player in the Bulls and Cows game. This class is responsible for generating and storing the player's secret code.
 */
public class Players {
    protected String secretCode;

    // Generates a random secret code consisting of 4 unique digits.
    public void generateSecretCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < 4) {
            int digit = random.nextInt(10);
            String digitStr = Integer.toString(digit);
            if (sb.indexOf(digitStr) == -1) {
                sb.append(digitStr);
            }
        }

        secretCode = sb.toString();
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getSecretCode() {
        return secretCode;
    }

    /**
     * Generates a random guess consisting of 4 unique digits.
     *
     * @return A random guess string.
     */
    protected String generateRandomGuess() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < 4) {
            int digit = random.nextInt(10);
            String digitStr = Integer.toString(digit);
            if (sb.indexOf(digitStr) == -1) {
                sb.append(digitStr);
            }
        }

        return sb.toString();
    }

    /**
     * Checks if the given guess is already in the AI opponent's previous guesses.
     *
     * @param guess      The guess string to check.
     * @param guessCount The number of previous guesses.
     * @return True if the guess is repeated, false otherwise.
     */
    protected boolean isRepeatedGuess(String guess, Guess[] previousGuesses, int guessCount) {
        for (int i = 0; i < guessCount; i++) {
            if (previousGuesses[i].getGuess().equals(guess)) {
                return true;
            }
        }
        return false;
    }
}