package com.hafiizh.themovie.model.nowplaying;

import java.util.List;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class NowPlayingResponse {
    private List<NowPlayingSource> results;
    private String id;
    private String total_results;
    private Dates dates;
    private String total_pages;

    public List<NowPlayingSource> getResults() {
        return results;
    }

    public String getId() {
        return id;
    }

    public String getTotal_results() {
        return total_results;
    }

    public Dates getDates() {
        return dates;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public class Dates {
        private String maximum;
        private String minimum;

        public String getMaximum() {
            return maximum;
        }

        public String getMinimum() {
            return minimum;
        }
    }
}
