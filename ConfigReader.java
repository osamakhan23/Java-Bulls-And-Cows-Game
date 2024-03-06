import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {
    private int maxTurns;
    private int secretCodeLength;

    public ConfigReader(String configFilePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    if (maxTurns == 0) {
                        maxTurns = Integer.parseInt(line);
                    } else if (secretCodeLength == 0) {
                        secretCodeLength = Integer.parseInt(line);
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading config file: " + e.getMessage());
            maxTurns = 7; // Default value
            secretCodeLength = 4; // Default value
        }
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public int getSecretCodeLength() {
        return secretCodeLength;
    }
}