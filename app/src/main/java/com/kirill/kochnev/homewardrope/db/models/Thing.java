package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.db.models.manytomany.ThingsWardropes;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.kirill.kochnev.homewardrope.db.models.manytomany.ThingsWardropesDao;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

@Entity
public class Thing implements IDbModel {

    @Id
    private Long id;

    @ToMany(referencedJoinProperty = "thingId")
    private List<ThingsWardropes> thingsWardropes;

    private Date creationDate;

    private String name;

    private String tag;

    private String fullImagePath;

    private String iconImagePath;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 359674910)
    private transient ThingDao myDao;

    public Thing(String name) {
        id = null;
        this.name = name;
        this.creationDate = new Date();
    }

    public Thing() {
        this.creationDate = new Date();
    }

    @Generated(hash = 2104068066)
    public Thing(Long id, Date creationDate, String name, String tag,
            String fullImagePath, String iconImagePath) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.tag = tag;
        this.fullImagePath = fullImagePath;
        this.iconImagePath = iconImagePath;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullImagePath() {
        return this.fullImagePath;
    }

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {
        holder.item.setName(name);
        holder.item.setImage(iconImagePath);
    }

    public String getIconImagePath() {
        return this.iconImagePath;
    }

    public void setIconImagePath(String iconImagePath) {
        this.iconImagePath = iconImagePath;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2020594078)
    public List<ThingsWardropes> getThingsWardropes() {
        if (thingsWardropes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ThingsWardropesDao targetDao = daoSession.getThingsWardropesDao();
            List<ThingsWardropes> thingsWardropesNew = targetDao
                    ._queryThing_ThingsWardropes(id);
            synchronized (this) {
                if (thingsWardropes == null) {
                    thingsWardropes = thingsWardropesNew;
                }
            }
        }
        return thingsWardropes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1052105978)
    public synchronized void resetThingsWardropes() {
        thingsWardropes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1726781403)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getThingDao() : null;
    }

}
