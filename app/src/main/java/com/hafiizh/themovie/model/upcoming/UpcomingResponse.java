package com.hafiizh.themovie.model.upcoming;

import java.util.List;

/**
 * Created by HAFIIZH on 9/12/2017.
 */

public class UpcomingResponse {
    private List<UpcomingSource> results;
    private String page;
    private String total_results;
    private Dates dates;
    private String total_pages;

    public List<UpcomingSource> getResults() {
        return results;
    }

    public String getPage() {
        return page;
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
}
