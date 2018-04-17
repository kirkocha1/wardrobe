package com.kirill.kochnev.homewardrope.db.models;

/**
 * Created by kirill on 24.04.17.
 */

public abstract class DbModel implements IDbModel {

    protected Long id;
    protected String creationDate;
    protected String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
