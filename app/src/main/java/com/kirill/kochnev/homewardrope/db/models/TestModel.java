package com.kirill.kochnev.homewardrope.db.models;

import java.util.Date;

/**
 * Created by kirill on 03.04.17.
 */

public class TestModel {

    private Long id;

    private Date creationDate;

    private String name;

    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

