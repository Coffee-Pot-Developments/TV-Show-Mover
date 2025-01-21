package io.github.vertanzil;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.exit;
public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> FileExtension = new ArrayList<>();
        String WorkingDirectory = "D:\\The Simpsons\\ZZZ - Unsorted"; // Replace with the actual path
        String EndDirectory = "D:\\The Simpsons\\ZZZ - Unsorted"; // Replace with the actual path
        FileExtension.add(".mkv");
        FileExtension.add(".avi");
        FileExtension.add(".mp4");
        findFiles(new File(WorkingDirectory), FileExtension);
    }

    public static void Menu() {
        System.out.print("1 ). Show matching files" + System.lineSeparator());
        System.out.print("2 ). Show Non-matching files" + System.lineSeparator());
        System.out.print("3 ). Move matching files" + System.lineSeparator());
        System.out.print("4 ). Delete Non-matching files" + System.lineSeparator());
        System.out.print("quit ). exits the program" + System.lineSeparator());
    }
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static void findFiles(File directory, ArrayList<String> fileexts) throws IOException {
        int count = 0;
        File[] files = directory.listFiles();
        ArrayList<File> Matching = new ArrayList<>();
        ArrayList<File> NotMatching = new ArrayList<>();

        if (fileexts.isEmpty()) {
            System.out.print("No file extensions specified, please set these in the main method.");
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


                do {
                    System.out.print("Enter a word (or 'quit' to exit): ");
                    input = scanner.nextLine();

                    switch(input){

                        case "1":
                            for (File mf : Matching){
                                System.out.print(mf.getAbsolutePath() + System.lineSeparator());
                            }
                            System.lineSeparator();
                            System.lineSeparator();
                            Menu();
                            break;

                        case "2":

                            for (File nmf : NotMatching){
                                System.out.print(nmf.getAbsolutePath() + System.lineSeparator());
                            }
                            System.lineSeparator();
                            System.lineSeparator();
                            Menu();
                            break;

                        case "3":
                            System.out.print("Not implemented.");
                            System.lineSeparator();
                            Menu();
                            break;

                        case "4":
                            System.out.print("Not implemented..");
                            System.lineSeparator();
                            Menu();
                            break;
                    }
                } while (!input.equalsIgnoreCase("quit"));
                scanner.close();
                System.out.println("Exiting...");
            }
        }
    }
}