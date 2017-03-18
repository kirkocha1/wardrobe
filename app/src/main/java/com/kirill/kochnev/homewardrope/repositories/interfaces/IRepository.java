package com.kirill.kochnev.homewardrope.repositories.interfaces;

import com.kirill.kochnev.homewardrope.db.models.IHolderModel;

import java.util.List;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public interface IRepository<M extends IHolderModel> {

    List<M> getNextList(long id);

    void putItem(M model);

    M getItem(long id);

}
