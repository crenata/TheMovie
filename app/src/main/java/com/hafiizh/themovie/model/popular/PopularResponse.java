package com.hafiizh.themovie.model.popular;

import java.util.List;

/**
 * Created by HAFIIZH on 9/2/2017.
 */

public class PopularResponse {
    private String page;
    private String total_results;
    private String total_pages;
    private List<PopularSource> results;

    public String getPage() {
        return page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public List<PopularSource> getResults() {
        return results;
    }
}
