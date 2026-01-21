package io.github.vertanzil.tvmover;

import io.github.vertanzil.tvmover.other.LoggerConfig;
import io.github.vertanzil.tvmover.other.CommandMenu;
import io.github.vertanzil.tvmover.other.ScanResult;

import io.github.vertanzil.tvmover.operations.FileScanner;
import io.github.vertanzil.tvmover.operations.EpisodeParser;
import io.github.vertanzil.tvmover.operations.FileRenamer;

import io.github.vertanzil.tvmover.config.Config;
import io.github.vertanzil.tvmover.config.ConfigLoader;

import java.io.File;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Setup logging
        LoggerConfig.setup();

        // Load config.json
        Config config = ConfigLoader.load("src/config.json");

        File start = new File(config.getSource());
        File end = new File(config.getDestination());
        ArrayList<String> extensions = new ArrayList<>(config.getExtensions());

        // Core components
        FileScanner scanner = new FileScanner();
        EpisodeParser parser = new EpisodeParser();
        FileRenamer renamer = new FileRenamer();

        // Initial scan
        ScanResult result = scanner.scan(start, end, extensions);

        // Launch menu
        CommandMenu menu = new CommandMenu(
                scanner,
                parser,
                renamer,
                result,
                start,
                end,
                extensions
        );

        menu.run();
    }
}