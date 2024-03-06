/**
 * Represents a medium AI opponent in the Bulls and Cows game. The MediumAI class extends the AIOpponent class and overrides the makeAGuess method to generate random guesses without repetition.
 */
public class MediumAI extends AIOpponent {

    /**
     * Overrides the parent class method to make a random guess without repetition.
     *
     * @return A random guess as a Guess object that hasn't been made before.
     */
    @Override
    public Guess makeAGuess() {
        String aiGuess;
        do {
            aiGuess = generateRandomGuess(); // generate a random guess
        } while (isRepeatedGuess(aiGuess, getGuessCount())); // repeat if the guess has been made before

        Guess guess = new Guess(aiGuess);
        addGuess(guess); // add the guess to the list of previous guesses
        return guess;
    }
}