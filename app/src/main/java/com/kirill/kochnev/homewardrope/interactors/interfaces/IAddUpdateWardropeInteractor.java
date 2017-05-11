package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IAddUpdateWardropeInteractor {

    Single<Wardrope> getWardrope(long id);

    Single<Object> saveWardrope(String name, HashSet<Long> thingIds);
}
