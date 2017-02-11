package com.kirill.kochnev.homewardrope.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

@Entity
public class Wardrope {
    @Id
    private long id;

    private Date creationDate;

    private String name;

    public Wardrope(String name, long id) {
        this.name = name;
        this.id = id;
        this.creationDate = new Date();
    }

    public Wardrope() {
        this.creationDate = new Date();
    }

    @Generated(hash = 1120298132)
    public Wardrope(long id, Date creationDate, String name) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
