package com.kirill.kochnev.homewardrope.db.models;

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
    private Long wardropeId;

    @Property(nameInDb = "thingId")
    private Long thingId;

    @Generated(hash = 1173295568)
    public ThingsWardropes(Long id, Long wardropeId, Long thingId) {
        this.id = id;
        this.wardropeId = wardropeId;
        this.thingId = thingId;
    }

    @Generated(hash = 66473543)
    public ThingsWardropes() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWardropeId() {
        return this.wardropeId;
    }

    public void setWardropeId(Long wardropeId) {
        this.wardropeId = wardropeId;
    }

    public Long getThingId() {
        return this.thingId;
    }

    public void setThingId(Long thingId) {
        this.thingId = thingId;
    }
}
