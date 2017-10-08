package com.hafiizh.themovie.model.toprated;

import java.util.List;

/**
 * Created by HAFIIZH on 9/12/2017.
 */

public class TopRatedResponse {
    private String page;
    private String total_results;
    private String total_pages;
    private List<TopRatedSource> results;

    public String getPage() {
        return page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public List<TopRatedSource> getResults() {
        return results;
    }
}
