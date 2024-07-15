package org.example.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertNotNull; // assertTrue, assertEquals, assertNotNull

import java.util.HashMap;

public class LoadEnvTest {

    @Test
    @DisplayName("Validates that Credentials load from .env file")
    void loadEnvTest() {
        final HashMap credentials = LoadEnv.fromFilePath("example_env.txt");
        assertNotNull(credentials);
        System.out.println("accessKeyId: " + credentials.get("accessKeyId"));
        System.out.println("secretAccessKey: " + credentials.get("secretAccessKey"));
        assertNotNull(credentials.get("accessKeyId"));
        assertNotNull(credentials.get("secretAccessKey"));
    }
}
