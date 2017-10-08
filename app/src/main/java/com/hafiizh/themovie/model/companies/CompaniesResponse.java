package com.hafiizh.themovie.model.companies;

import java.util.List;

/**
 * Created by HAFIIZH on 9/10/2017.
 */

public class CompaniesResponse {
    private String id;
    private String page;
    private List<CompaniesSource> results;
    private String total_pages;
    private String total_results;

    public String getId() {
        return id;
    }

    public String getPage() {
        return page;
    }

    public List<CompaniesSource> getResults() {
        return results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }
}
