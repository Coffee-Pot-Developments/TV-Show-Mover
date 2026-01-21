package io.github.vertanzil.tvmover.other;

import io.github.vertanzil.tvmover.operations.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandMenu {

    private final FileScanner scanner;
    private final EpisodeParser parser;
    private final FileRenamer renamer;
    private final FolderOrganizer organizer;

    private ScanResult result;
    private final File start;
    private final File endDirectory;
    private final ArrayList<String> extensions;

    private final FileMover mover = new FileMover();
    private final FileDeleter deleter = new FileDeleter();

    public CommandMenu(FileScanner scanner,
                       EpisodeParser parser,
                       FileRenamer renamer,
                       ScanResult result,
                       File start,
                       File endDirectory,
                       ArrayList<String> extensions) {

        this.scanner = scanner;
        this.parser = parser;
        this.renamer = renamer;
        this.organizer = new FolderOrganizer();

        this.result = result;
        this.start = start;
        this.endDirectory = endDirectory;
        this.extensions = extensions;
    }

    public void run() {

        Scanner scannerInput = new Scanner(System.in);
        String input;

        printMenu();

        while (true) {
            System.out.print("> ");
            input = scannerInput.nextLine().trim().toLowerCase();

            switch (input) {

                case "h" -> printMenu();

                case "s" -> {
                    System.out.println("Matching: " + result.getMatching().size());
                    System.out.println("Non-matching: " + result.getNotMatching().size());
                    System.out.println("Source: " + start.getAbsolutePath());
                    System.out.println("Destination: " + endDirectory.getAbsolutePath());
                }

                case "m" -> result.getMatching().forEach(f -> System.out.println(f.getAbsolutePath()));

                case "n" -> result.getNotMatching().forEach(f -> System.out.println(f.getAbsolutePath()));

                case "dm" -> {
                    System.out.println("=== DRY RUN: Organise + Move ===");
                    dryRunOrganiseAndMove();
                    System.out.print("Run move for real? (yes/no): ");
                    if (scannerInput.nextLine().equalsIgnoreCase("yes")) {
                        organiseAndMove(false);
                    } else {
                        System.out.println("Move cancelled.");
                    }
                }

                case "mv" -> organiseAndMove(false);

                case "dd" -> {
                    deleter.deleteFiles(result.getNotMatching(), true);
                    System.out.print("Run delete for real? (type YES): ");
                    if (scannerInput.nextLine().equalsIgnoreCase("YES")) {
                        deleter.deleteFiles(result.getNotMatching(), false);
                    } else {
                        System.out.println("Delete cancelled.");
                    }
                }

                case "del" -> deleter.deleteFiles(result.getNotMatching(), false);

                case "r" -> {
                    System.out.println("Reloading scan...");
                    result = scanner.scan(start, endDirectory, extensions);
                    System.out.println("Reload complete.");
                }

                case "q" -> {
                    System.out.println("Goodbye.");
                    return;
                }

                default -> System.out.println("Unknown command. Type 'h' for help.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== TV Mover ===");
        System.out.println("[h]   Help / Show menu");
        System.out.println("[s]   Stats");
        System.out.println("[m]   Show matching files");
        System.out.println("[n]   Show non-matching files");
        System.out.println("[dm]  Dry-run organise + move");
        System.out.println("[mv]  Organise + move files");
        System.out.println("[dd]  Dry-run delete");
        System.out.println("[del] Delete files");
        System.out.println("[r]   Reload scan");
        System.out.println("[q]   Quit");
    }

    // ------------------------------------------------------------
    // DRY RUN ORGANISE + MOVE
    // ------------------------------------------------------------
    private void dryRunOrganiseAndMove() {
        for (File file : result.getMatching()) {

            EpisodeInfo info = parser.parse(file);
            if (info == null) {
                System.out.println("[SKIP] Could not parse: " + file.getName());
                continue;
            }

            File renamed = renamer.renameTemp(file, info);
            File destFolder = organizer.getDestinationFolder(endDirectory, info);
            File finalDest = new File(destFolder, renamed.getName());

            System.out.println("[DRY RUN]");
            System.out.println("  File: " + file.getName());
            System.out.println("  Parsed: " + info.getShowName() + " S" + info.getSeason() + "E" + info.getEpisodes());
            System.out.println("  New Name: " + renamed.getName());
            System.out.println("  Destination: " + finalDest.getAbsolutePath());
        }
    }

    // ------------------------------------------------------------
    // REAL ORGANISE + MOVE
    // ------------------------------------------------------------
    private void organiseAndMove(boolean dryRun) {

        for (File file : result.getMatching()) {

            EpisodeInfo info = parser.parse(file);
            if (info == null) {
                System.out.println("[SKIP] Could not parse: " + file.getName());
                continue;
            }

            File renamed = renamer.renameTemp(file, info);
            File destFolder = organizer.getDestinationFolder(endDirectory, info);
            File finalDest = new File(destFolder, renamed.getName());

            if (dryRun) {
                System.out.println("[DRY RUN] Would move:");
                System.out.println("  From: " + file.getAbsolutePath());
                System.out.println("  To:   " + finalDest.getAbsolutePath());
                continue;
            }

            // First rename locally
            File renamedTemp = new File(file.getParent(), renamed.getName());
            file.renameTo(renamedTemp);

            // Then move to final folder
            mover.moveFiles(new ArrayList<>(java.util.List.of(renamedTemp)), destFolder, false);
        }
    }
}