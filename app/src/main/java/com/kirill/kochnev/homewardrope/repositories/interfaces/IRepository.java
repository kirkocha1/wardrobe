package com.kirill.kochnev.homewardrope.repositories.interfaces;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public interface IRepository<M> {

    int LIMIT = 20;

    Single<List<M>> getNextList(long id);

    Single<Boolean> putItem(M model);

    Single<M> getItem(long id);

    Single<Boolean> deletItem(M model);


}
