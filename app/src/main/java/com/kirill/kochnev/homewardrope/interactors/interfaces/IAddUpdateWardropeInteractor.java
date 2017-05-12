package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IAddUpdateWardropeInteractor {

    Single<Wardrope> getWardrope(long id);

    Single<PutResult> saveWardrope(String name, HashSet<Long> thingIds);
}
