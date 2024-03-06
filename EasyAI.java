import java.util.Random;

public class EasyAI extends AIOpponent {
    private int maxTurns;
    private int secretCodeLength;

    public EasyAI(String configFilePath) {
        ConfigReader configReader = new ConfigReader(configFilePath);
        maxTurns = configReader.getMaxTurns();
        secretCodeLength = configReader.getSecretCodeLength();
        generateSecretCode(secretCodeLength);
    }

    public EasyAI() {
        // Provide default values for maxTurns and secretCodeLength
        maxTurns = 7;
        secretCodeLength = 4;
        generateSecretCode(secretCodeLength);
    }

    // Add methods to get the values, so you can use them in the BullsAndCows class
    public int getMaxTurns() {
        return maxTurns;
    }

    public int getSecretCodeLength() {
        return secretCodeLength;
    }

    @Override
    public void generateSecretCode() {
        generateSecretCode(secretCodeLength);
    }

    private void generateSecretCode(int secretCodeLength) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < secretCodeLength) {
            int digit = random.nextInt(10);
            String digitStr = Integer.toString(digit);
            if (sb.indexOf(digitStr) == -1) {
                sb.append(digitStr);
            }
        }

        secretCode = sb.toString();
    }
}