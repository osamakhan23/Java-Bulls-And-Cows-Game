import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class HardAI extends AIOpponent {
    private List<String> possibleCodes;

    public HardAI() {
        possibleCodes = new ArrayList<>();
        generateAllPossibleCodes();
    }

    // Generate all possible codes using digits from 0-9
    private void generateAllPossibleCodes() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == j) continue;
                for (int k = 0; k < 10; k++) {
                    if (k == i || k == j) continue;
                    for (int l = 0; l < 10; l++) {
                        if (l == i || l == j || l == k) continue;
                        possibleCodes.add("" + i + j + k + l);
                    }
                }
            }
        }
    }

    @Override
    public Guess makeAGuess() {
        String aiGuess;
        if (getGuessCount() == 0) {
            // First guess is random
            aiGuess = generateRandomGuess();
        } else {
            // Subsequent guesses are intelligent
            aiGuess = getNextIntelligentGuess();
        }
        Guess guess = new Guess(aiGuess);
        addGuess(guess);
        return guess;
    }

    private String getNextIntelligentGuess() {
        Map<String, Integer> candidateCounts = new HashMap<>();
        // Initialize the count for all possible codes to 0
        for (String candidate : possibleCodes) {
            candidateCounts.put(candidate, 0);
        }
        // Count the number of possible codes that are consistent with each previous guess
        for (String candidate : possibleCodes) {
            for (int i = 0; i < getGuessCount(); i++) {
                Guess guess = getPreviousGuesses().get(i);
                int cows = guess.getCows();
                int bulls = guess.getBulls();
                Guess candidateGuess = new Guess(candidate);
                candidateGuess.calculateCowsAndBulls(guess.getGuess());
                if (candidateGuess.getCows() != cows || candidateGuess.getBulls() != bulls) {
                    candidateCounts.put(candidate, candidateCounts.get(candidate) + 1);
                }
            }
        }
        // Find the candidate(s) with the minimum count of inconsistent guesses
        int minCandidateCount = Integer.MAX_VALUE;
        List<String> minCandidateList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : candidateCounts.entrySet()) {
            String candidate = entry.getKey();
            int count = entry.getValue();
            if (count < minCandidateCount) {
                minCandidateCount = count;
                minCandidateList = new ArrayList<>();
                minCandidateList.add(candidate);
            } else if (count == minCandidateCount) {
                minCandidateList.add(candidate);
            }
        }
        // Choose a candidate randomly from the list of minimum count candidates
        return minCandidateList.get(new Random().nextInt(minCandidateList.size()));
    }
}