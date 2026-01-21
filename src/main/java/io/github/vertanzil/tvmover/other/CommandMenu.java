package io.github.vertanzil.tvmover.other;

import io.github.vertanzil.tvmover.operations.FileDeleter;
import io.github.vertanzil.tvmover.operations.FileMover;
import io.github.vertanzil.tvmover.operations.FileScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandMenu {

    private final FileScanner scanner;
    private ScanResult result;
    private final File start;
    private final File endDirectory;
    private final ArrayList<String> extensions;

    private final FileMover mover = new FileMover();
    private final FileDeleter deleter = new FileDeleter();

    public CommandMenu(FileScanner scanner,
                       ScanResult result,
                       File start,
                       File endDirectory,
                       ArrayList<String> extensions) {

        this.scanner = scanner;
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
                    mover.moveFiles(result.getMatching(), endDirectory, true);
                    System.out.print("Run move for real? (yes/no): ");
                    if (scannerInput.nextLine().equalsIgnoreCase("yes")) {
                        mover.moveFiles(result.getMatching(), endDirectory, false);
                    } else {
                        System.out.println("Move cancelled.");
                    }
                }

                case "mv" -> mover.moveFiles(result.getMatching(), endDirectory, false);

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
        System.out.println("[dm]  Dry-run move");
        System.out.println("[mv]  Move files");
        System.out.println("[dd]  Dry-run delete");
        System.out.println("[del] Delete files");
        System.out.println("[r]   Reload scan");
        System.out.println("[q]   Quit");
    }
}