package com.kirill.kochnev.homewardrope.db.models;

import android.support.v7.widget.RecyclerView;

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


    public Thing(String name) {
        id = null;
        this.name = name;
        this.creationDate = new Date();
    }

    public Thing() {
        this.creationDate = new Date();
    }

    @Generated(hash = 948112464)
    public Thing(Long id, Date creationDate, String name) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
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

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {
        holder.item.setName(name);
    }
}
