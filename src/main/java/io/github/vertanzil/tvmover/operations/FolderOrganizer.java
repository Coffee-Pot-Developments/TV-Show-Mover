package io.github.vertanzil.tvmover.operations;

import java.io.File;

public class FolderOrganizer {

    public File getDestinationFolder(File base, EpisodeInfo info) {

        File showFolder = new File(base, info.getShowName());
        File seasonFolder = new File(showFolder, "Season " + String.format("%02d", info.getSeason()));

        if (!seasonFolder.exists()) {
            seasonFolder.mkdirs();
        }

        return seasonFolder;
    }
}