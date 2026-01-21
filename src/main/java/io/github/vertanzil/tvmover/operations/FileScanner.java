package io.github.vertanzil.tvmover.operations;

import io.github.vertanzil.tvmover.other.ScanResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

public class FileScanner {

    private static final Logger logger = Logger.getLogger("TVMover");

    public ScanResult scan(File start, File endDirectory, ArrayList<String> extensions) {

        ArrayList<File> matching = new ArrayList<>();
        ArrayList<File> notMatching = new ArrayList<>();

        findFiles(start, endDirectory, extensions, matching, notMatching);

        matching.sort(Comparator.comparing(f -> f.getAbsolutePath().toLowerCase()));
        notMatching.sort(Comparator.comparing(f -> f.getAbsolutePath().toLowerCase()));

        logger.info("Scan complete. " + matching.size() + " matching, " + notMatching.size() + " non-matching.");

        return new ScanResult(matching, notMatching);
    }

    private void findFiles(File directory,
                           File endDirectory,
                           ArrayList<String> extensions,
                           ArrayList<File> matching,
                           ArrayList<File> notMatching) {

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {

            if (file.isDirectory() &&
                    file.getAbsolutePath().equals(endDirectory.getAbsolutePath())) {
                continue;
            }

            if (file.isDirectory()) {
                findFiles(file, endDirectory, extensions, matching, notMatching);
                continue;
            }

            String name = file.getName().toLowerCase();
            boolean isMatch = extensions.stream().anyMatch(name::endsWith);

            if (isMatch) matching.add(file);
            else notMatching.add(file);
        }
    }
}