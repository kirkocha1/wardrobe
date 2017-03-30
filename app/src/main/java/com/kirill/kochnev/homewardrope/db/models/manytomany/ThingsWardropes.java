package com.kirill.kochnev.homewardrope.db.models.manytomany;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kirill on 30.03.17.
 */

@Entity
public class ThingsWardropes {

    @Id
    private long id;

    private long wardropeId;

    private long thingId;

    @Generated(hash = 1995222356)
    public ThingsWardropes(long id, long wardropeId, long thingId) {
        this.id = id;
        this.wardropeId = wardropeId;
        this.thingId = thingId;
    }

    @Generated(hash = 66473543)
    public ThingsWardropes() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWardropeId() {
        return this.wardropeId;
    }

    public void setWardropeId(long wardropeId) {
        this.wardropeId = wardropeId;
    }

    public long getThingId() {
        return this.thingId;
    }

    public void setThingId(long thingId) {
        this.thingId = thingId;
    }
}
