package com.retuerm.android.blockbuster.utility;

/**
 * Created by max on 18/05/2017.
 */

public class MovieTrailer {

    private String key;
    private String name;
    private String site;
    private String type;

    public MovieTrailer(String id, String name, String site, String type) {
        this.key = id;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
