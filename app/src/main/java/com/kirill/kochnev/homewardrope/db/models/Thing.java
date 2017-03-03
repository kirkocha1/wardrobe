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

    private String filePath;


    public Thing(String name) {
        id = null;
        this.name = name;
        this.creationDate = new Date();
    }

    public Thing() {
        this.creationDate = new Date();
    }

    @Generated(hash = 2035203015)
    public Thing(Long id, Date creationDate, String name, String tag,
            String filePath) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.tag = tag;
        this.filePath = filePath;
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



    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        holder.item.setImage(filePath);
    }

}
