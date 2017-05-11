package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Look;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface ILooksInteractor {

    Single<List<Look>> getLooks(long id);

    Single<Boolean> deleteLook(Look model);
}
