package io.github.vertanzil.tvmover.config;

import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {

    public static Config load(String path) {
        try {
            String json = Files.readString(Path.of(path));
            return new Gson().fromJson(json, Config.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + e.getMessage(), e);
        }
    }
}