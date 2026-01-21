package io.github.vertanzil.tvmover;


import io.github.vertanzil.tvmover.operations.FileScanner;
import io.github.vertanzil.tvmover.other.CommandMenu;
import io.github.vertanzil.tvmover.other.LoggerConfig;
import io.github.vertanzil.tvmover.other.ScanResult;

import java.io.File;
import java.util.ArrayList;
    public class Main {

        public static void main(String[] args) {

            LoggerConfig.setup();

            ArrayList<String> extensions = new ArrayList<>();
            extensions.add(".mkv");
            extensions.add(".avi");
            extensions.add(".mp4");

            String workingDirectory = "d:\\test";
            String endDirectory = "d:\\test\\complete";

            File start = new File(workingDirectory);
            File end = new File(endDirectory);

            FileScanner scanner = new FileScanner();
            ScanResult result = scanner.scan(start, end, extensions);

            CommandMenu menu = new CommandMenu(scanner, result, start, end, extensions);
            menu.run();
        }
    }