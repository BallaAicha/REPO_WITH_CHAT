package org.etutoria.usersservice.Config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    private static final Dotenv dotenv = Dotenv.load();
    public static String getEnv(String key) {
        return dotenv.get(key);
    }
}