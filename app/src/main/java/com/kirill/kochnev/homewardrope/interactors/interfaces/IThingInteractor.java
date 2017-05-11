package com.kirill.kochnev.homewardrope.interactors.interfaces;

import com.kirill.kochnev.homewardrope.db.models.Thing;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IThingInteractor {

    Single<Boolean> deleteThings(Thing model);

    Single<List<Thing>> getThingsByWardrope(long id, long filterId);

    Single<HashSet<Long>> getWardropeThingIds(long filterId);

}
