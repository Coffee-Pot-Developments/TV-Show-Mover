package io.github.vertanzil.tvmover.config;

import java.util.List;

public class Config {

    private String source;
    private String destination;
    private List<String> extensions;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}