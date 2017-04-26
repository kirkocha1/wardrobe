package com.kirill.kochnev.homewardrope.repositories.interfaces;

import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public interface IRepository<M> {

    Single<Boolean> putItem(M model);

    Single<M> getItem(long id);

    Single<Boolean> deletItem(M model);

    Single<List<M>> query(long id);

    Single<List<M>> query();

    Single<List<M>> query(ISpecification filterSpecification);

}
