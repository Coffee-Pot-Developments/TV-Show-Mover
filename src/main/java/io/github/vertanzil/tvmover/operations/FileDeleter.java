package io.github.vertanzil.tvmover.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FileDeleter {

    private static final Logger logger = Logger.getLogger("TVMover");

    public void deleteFiles(ArrayList<File> files, boolean dryRun) {

        for (File file : files) {

            if (dryRun) {
                System.out.println("[DRY RUN] Would delete: " + file.getAbsolutePath());
                continue;
            }

            try {
                Files.delete(file.toPath());
                System.out.println("Deleted: " + file.getAbsolutePath());
                logger.warning("Deleted file: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Failed to delete: " + file.getAbsolutePath());
                logger.severe("Failed to delete: " + file.getAbsolutePath() + " Reason: " + e.getMessage());
            }
        }
    }
}

