package io.github.vertanzil.tvmover.other;

import java.io.File;
import java.util.ArrayList;

public class ScanResult {

    private final ArrayList<File> matching;
    private final ArrayList<File> notMatching;

    public ScanResult(ArrayList<File> matching, ArrayList<File> notMatching) {
        this.matching = matching;
        this.notMatching = notMatching;
    }

    public ArrayList<File> getMatching() {
        return matching;
    }

    public ArrayList<File> getNotMatching() {
        return notMatching;
    }
}