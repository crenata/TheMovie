package com.hafiizh.themovie.model.genre;

import java.util.List;

/**
 * Created by HAFIIZH on 9/10/2017.
 */

public class GenreResponse {
    private String id;
    private String page;
    private List<GenreSource> results;
    private String total_pages;
    private String total_results;

    public String getId() {
        return id;
    }

    public String getPage() {
        return page;
    }

    public List<GenreSource> getResults() {
        return results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }
}
