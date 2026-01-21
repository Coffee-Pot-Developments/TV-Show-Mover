package io.github.vertanzil.tvmover.operations;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class FileMover {

    private static final Logger logger = Logger.getLogger("TVMover");

    public void moveFiles(ArrayList<File> files, File endDirectory, boolean dryRun) {

        try {
            Files.createDirectories(endDirectory.toPath());
        } catch (IOException e) {
            System.out.println("Could not create destination directory.");
            logger.severe("Failed to create destination directory: " + e.getMessage());
            return;
        }

        for (File file : files) {
            Path target = endDirectory.toPath().resolve(file.getName());

            if (dryRun) {
                System.out.println("[DRY RUN] Would move:");
                System.out.println("  From: " + file.getAbsolutePath());
                System.out.println("  To:   " + target);
                continue;
            }

            try {
                Files.move(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved: " + file.getAbsolutePath());
                logger.info("Moved file: " + file.getAbsolutePath() + " → " + target);
            } catch (IOException e) {
                System.out.println("Failed to move: " + file.getAbsolutePath());
                logger.severe("Failed to move: " + file.getAbsolutePath() + " Reason: " + e.getMessage());
            }
        }
    }
}