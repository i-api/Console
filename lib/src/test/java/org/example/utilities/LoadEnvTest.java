package org.example.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertNotNull; // assertTrue, assertEquals, assertNotNull

public class LoadEnvTest {

    @Test
    @DisplayName("Validates that Credentials load from .env file")
    void loadEnvTest() {
        java.util.HashMap credentials = LoadEnv.fromFilePath("example_env.txt"); // env belongs in <Package>/src or <Package>/lib of gradle folder
        System.out.println("accessKeyId: " + credentials.get("accessKeyId"));
        System.out.println("secretAccessKey: " + credentials.get("secretAccessKey"));
        assertNotNull(credentials.get("accessKeyId"));
        assertNotNull(credentials.get("secretAccessKey"));
    }
}
