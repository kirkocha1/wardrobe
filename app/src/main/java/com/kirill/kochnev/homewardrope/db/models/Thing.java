package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

@Entity
public class Thing implements IHolderModel{

    @Id
    private Long id;

    private Date creationDate;

    private String name;

    private String tag;

    private String fullImagePath;

    private String iconImagePath;

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

}
