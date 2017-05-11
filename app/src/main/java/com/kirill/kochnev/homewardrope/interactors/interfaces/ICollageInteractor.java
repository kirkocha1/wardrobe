package com.kirill.kochnev.homewardrope.interactors.interfaces;

import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface ICollageInteractor {

    Single<SparseArray<Bitmap>> getImages(HashSet<Long> thingIds);
}
