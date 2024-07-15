package org.example.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


// CHECKSTYLE:OFF
// In the future, upgrade to aws credentials provider chain
// https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials-chain.html
// CHECKSTYLE:ON
/**
usage:
LoadEnv.fromFilePath().get("username")
 */
final class LoadEnv {
    private LoadEnv() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static HashMap<String, String> fromFilePath(final String... args) {
        final String envFilePath;
        if (args.length == 0) {
            envFilePath = ".env";
        } else if (args.length == 1) {
            envFilePath = args[0];
        } else {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        final HashMap<String, String> envVariables = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(envFilePath), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    final String key = parts[0].trim();
                    final String value = parts[1].trim();
                    envVariables.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return envVariables;
    }
}
