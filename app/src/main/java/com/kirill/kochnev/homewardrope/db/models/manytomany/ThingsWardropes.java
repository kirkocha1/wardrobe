package com.kirill.kochnev.homewardrope.db.models.manytomany;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by kirill on 30.03.17.
 */

@Entity
public class ThingsWardropes {

    @Id
    private Long id;

    @Property(nameInDb = "wardropeId")
    private long wardropeId;

    @Property(nameInDb = "thingId")
    private long thingId;

    @Generated(hash = 1425303745)
    public ThingsWardropes(Long id, long wardropeId, long thingId) {
        this.id = id;
        this.wardropeId = wardropeId;
        this.thingId = thingId;
    }

    @Generated(hash = 66473543)
    public ThingsWardropes() {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
