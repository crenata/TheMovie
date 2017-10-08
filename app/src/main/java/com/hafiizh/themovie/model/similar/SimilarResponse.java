package com.hafiizh.themovie.model.similar;

import java.util.List;

/**
 * Created by HAFIIZH on 9/9/2017.
 */

public class SimilarResponse {
    private String page;
    private List<SimilarSource> results;
    private String total_pages;
    private String total_results;

    public String getPage() {
        return page;
    }

    public List<SimilarSource> getResults() {
        return results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }
}
