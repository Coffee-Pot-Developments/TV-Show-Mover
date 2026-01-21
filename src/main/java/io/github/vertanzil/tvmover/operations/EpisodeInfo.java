package io.github.vertanzil.tvmover.operations;

import java.util.List;

public class EpisodeInfo {

    private final String showName;
    private final int season;
    private final List<Integer> episodes;
    private final String extension;

    public EpisodeInfo(String showName, int season, List<Integer> episodes, String extension) {
        this.showName = showName;
        this.season = season;
        this.episodes = episodes;
        this.extension = extension;
    }

    public String getShowName() {
        return showName;
    }

    public int getSeason() {
        return season;
    }

    public List<Integer> getEpisodes() {
        return episodes;
    }

    public String getExtension() {
        return extension;
    }

    public boolean isMultiEpisode() {
        return episodes.size() > 1;
    }
}