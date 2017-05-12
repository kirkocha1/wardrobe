package com.kirill.kochnev.homewardrope.repositories.interfaces;

import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public interface IRepository<M> {

    Single<PutResult> putItem(M model);

    Single<M> getItem(long id);

    Single<DeleteResult> deletItem(M model);

    Single<List<M>> query(long id);

    Single<List<M>> query();

    Single<List<M>> query(ISpecification filterSpecification);

}
