package io.github.vertanzil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.exit;
public class Main {

    public static void main(String[] args) {
        ArrayList<String> FileExtension = new ArrayList<>();

        //SPECIFY THE START AND FINISHING DIRECTORS OF WHERE YOU WOULD LIKE TO LOOK FOR AND MOVE FILES TOO.
        String WorkingDirectory = ""; // Replace with the actual path
        String EndDirectory = ""; // Replace with the actual path

        //SPECIFY THE FILES USED TO LOOK FOR.
        FileExtension.add(".mkv");
        FileExtension.add(".avi");
        FileExtension.add(".mp4");

        try {
            findFiles(new File(WorkingDirectory), FileExtension, new File(EndDirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Menu() {
        System.out.print("1 ). Show matching files" + System.lineSeparator());
        System.out.print("2 ). Show Non-matching files" + System.lineSeparator());
        System.out.print("3 ). Move matching files" + System.lineSeparator());
        System.out.print("4 ). Delete Non-matching files" + System.lineSeparator());
        System.out.print("quit ). exits the program" + System.lineSeparator());
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static void findFiles(File directory, ArrayList<String> fileexts, File EndDirectory) throws IOException {
        int count = 0;
        File[] files = directory.listFiles();
        ArrayList<File> Matching = new ArrayList<>();
        ArrayList<File> NotMatching = new ArrayList<>();
        if (fileexts.isEmpty()) {
            System.out.print("No File types specified." + System.lineSeparator());
            exit(1);
        } else {
            System.out.print("File extensions specified" + " " + Arrays.toString(fileexts.toArray()) + System.lineSeparator());
            if (Objects.requireNonNull(files).length == 0) {
                System.out.print("No files found.");
            } else {
                for (File file : files) {
                    if (file.isDirectory()) {
                        for (File subfile : Objects.requireNonNull(file.listFiles())) {
                            //System.out.print(file.getAbsolutePath());
                            for (String str : fileexts) {
                                if (subfile.getAbsoluteFile().getName().endsWith(str)) {
                                    count += 1;
                                    Matching.add(new File(subfile.getAbsolutePath().toLowerCase()));
                                } else {
                                    NotMatching.add(new File(subfile.getAbsolutePath().toLowerCase()));
                                }
                            }
                        }
                    }
                }

                System.out.print(count + " files found, matching the given file extensions" + System.lineSeparator());
                Scanner scanner = new Scanner(System.in);
                String input;
                Menu();
                do {
                    System.out.print("Enter a word (or 'quit' to exit): ");
                    input = scanner.nextLine();

                    switch (input) {
                        case "1" -> {
                            for (File mf : Matching) {
                                System.out.print(mf.getAbsolutePath() + System.lineSeparator());
                            }
                            Menu();
                        }
                        case "2" -> {
                            for (File nmf : NotMatching) {
                                System.out.print(nmf.getAbsolutePath() + System.lineSeparator());
                            }
                            Menu();
                        }
                        case "3" -> {
                            //MOVE FILES
                            for (File file: Matching){
                                Files.move(Paths.get(file.getAbsolutePath()), Paths.get(EndDirectory.getAbsolutePath() + "//" + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                                System.out.print(file.getAbsolutePath() + " moved to " + EndDirectory.getAbsolutePath() + "//" + file.getName() + System.lineSeparator());
                            }
                            System.out.print("Complete");
                            Menu();
                        }
                        case "4" -> {
                            //DELETE FILES
                            for (File file: NotMatching){
                                Files.delete(Paths.get(file.getAbsolutePath()));
                                System.out.print(file.getAbsolutePath()  + " deleted" + System.lineSeparator());
                            }

                            Menu();
                        }
                    }
                } while (!input.equalsIgnoreCase("quit"));
                scanner.close();
                System.out.println("Exiting...");
            }
        }
    }
}