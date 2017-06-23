package com.kirill.kochnev.homewardrope.interactors.interfaces;

import android.util.Pair;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IAddUpdateWardropeInteractor {

    Single<Wardrope> getWardrope(long id);

    Single<RepoResult> saveWardrope(String name, @NotNull HashSet<Long> thingIds, @NotNull HashSet<Long> lookIds);

    Pair<HashSet<Long>, HashSet<Long>> getStartIds();
}
