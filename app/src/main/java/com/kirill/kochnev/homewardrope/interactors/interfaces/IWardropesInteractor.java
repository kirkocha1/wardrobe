package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IWardropesInteractor {

    Single<Boolean> deleteWardropes(Wardrope model);

    Single<List<Wardrope>> getWardropes(long id);

}
