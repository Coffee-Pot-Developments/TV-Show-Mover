package io.github.vertanzil.tvmover.operations;

import java.io.File;
import java.util.List;

public class FileRenamer {

    public String buildNewName(EpisodeInfo info) {

        StringBuilder sb = new StringBuilder();
        sb.append(info.getShowName());
        sb.append(" - S");
        sb.append(String.format("%02d", info.getSeason()));
        sb.append("E");

        List<Integer> eps = info.getEpisodes();
        sb.append(String.format("%02d", eps.get(0)));

        if (eps.size() > 1) {
            sb.append("-");
            sb.append(String.format("%02d", eps.get(1)));
        }

        sb.append(info.getExtension());
        return sb.toString();
    }

    public File renameTemp(File file, EpisodeInfo info) {
        String newName = buildNewName(info);
        return new File(file.getParent(), newName);
    }
}