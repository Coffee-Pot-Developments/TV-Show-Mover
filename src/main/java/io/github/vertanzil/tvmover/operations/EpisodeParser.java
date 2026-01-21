package io.github.vertanzil.tvmover.operations;

import java.io.File;
import java.util.*;
import java.util.regex.*;

public class EpisodeParser {

    private static final Pattern SXXEXX =
            Pattern.compile("(?i)(.*?)[ ._-]*S(\\d{1,2})E(\\d{2})(?:E(\\d{2}))?");

    private static final Pattern X_FORMAT =
            Pattern.compile("(?i)(.*?)[ ._-]*(\\d{1,2})x(\\d{2})");

    private static final Pattern THREE_DIGIT =
            Pattern.compile("(?i)(.*?)[ ._-]*(\\d)(\\d{2})");

    public EpisodeInfo parse(File file) {

        String name = file.getName();
        String extension = name.substring(name.lastIndexOf('.'));

        Matcher m;

        // SxxExx or SxxExxExx
        m = SXXEXX.matcher(name);
        if (m.find()) {
            String show = cleanShowName(m.group(1));
            int season = Integer.parseInt(m.group(2));
            int ep1 = Integer.parseInt(m.group(3));
            List<Integer> eps = new ArrayList<>();
            eps.add(ep1);

            if (m.group(4) != null) {
                eps.add(Integer.parseInt(m.group(4)));
            }

            return new EpisodeInfo(show, season, eps, extension);
        }

        // 1x02
        m = X_FORMAT.matcher(name);
        if (m.find()) {
            String show = cleanShowName(m.group(1));
            int season = Integer.parseInt(m.group(2));
            int ep = Integer.parseInt(m.group(3));
            return new EpisodeInfo(show, season, List.of(ep), extension);
        }

        // 102 → S01E02
        m = THREE_DIGIT.matcher(name);
        if (m.find()) {
            String show = cleanShowName(m.group(1));
            int season = Integer.parseInt(m.group(2));
            int ep = Integer.parseInt(m.group(3));
            return new EpisodeInfo(show, season, List.of(ep), extension);
        }

        return null; // Not an episode
    }

    private String cleanShowName(String raw) {
        return raw.replace('.', ' ')
                .replace('_', ' ')
                .trim()
                .replaceAll(" +", " ");
    }
}