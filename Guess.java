/**
 * The Guess class represents an individual guess made by a player during the Bulls and Cows game.
 * It holds information about the guess, such as the number of cows and bulls, and provides methods for:
 * 1. Validating a guess to ensure it's a 4-digit number with unique digits.
 * 2. Calculating the number of cows and bulls in a guess compared to the secret code.
 * 3. Checking if the guess is correct (equal to the secret code).

 * This class is used by the BullsAndCows class to manage and process guesses during the game.
 */
public class Guess {
    private String guess;
    private int cows;
    private int bulls;

    public Guess(String guess) {
        this.guess = guess;
    }

    /**
     * Checks if the guess is valid by verifying that it is 4 characters long and that each character is a unique digit
     *
     * @param guess The guess to be validated
     * @return True if the guess is valid, false otherwise
     */
    public boolean isValidGuess(String guess) {
        if (guess.length() != 4) {
            return false;
        }
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
            for (int j = i + 1; j < guess.length(); j++) {
                if (c == guess.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculates the number of cows and bulls in the guess compared to the secret code
     *
     * @param secretCode The secret code that the guess is being compared to
     */
    public void calculateCowsAndBulls(String secretCode) {
        cows = 0;
        bulls = 0;
        for (int i = 0; i < guess.length(); i++) {
            char guessDigit = guess.charAt(i);
            for (int j = 0; j < secretCode.length(); j++) {
                char secretDigit = secretCode.charAt(j);
                if (guessDigit == secretDigit) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Checks if the guess is equal to the secret code
     *
     * @param secretCode The secret code that the guess is being compared to
     * @return True if the guess is equal to the secret code, false otherwise
     */
    public boolean isCorrect(String secretCode) {
        return guess.equals(secretCode);
    }

    public int getCows() {
        return cows;
    }

    public int getBulls() {
        return bulls;
    }

    public String getGuess() {
        return guess;
    }
}