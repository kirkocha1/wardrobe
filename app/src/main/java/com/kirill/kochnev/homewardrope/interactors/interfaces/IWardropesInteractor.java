package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IWardropesInteractor {

    Single<DeleteResult> deleteWardropes(Wardrope model);

    Single<List<Wardrope>> getWardropes(long id);

}
