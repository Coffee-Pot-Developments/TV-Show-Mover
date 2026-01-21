package io.github.vertanzil.tvmover.other;

import java.util.logging.*;

public class LoggerConfig {

    public static void setup() {
        LogManager.getLogManager().reset();

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        ch.setFormatter(new SimpleFormatter());

        Logger root = Logger.getLogger("");
        root.addHandler(ch);
        root.setLevel(Level.ALL);
    }
}