package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

@InjectViewState
public class AddUpdateWardropePresenter extends BaseMvpPresenter<IAddUpdateWardropeView> {

    public static final String TAG = "AddUpdateWardrope";

    @Inject
    protected AbstractWardropeRepository wardropes;

    private HashSet<Long> checkedSet = new HashSet<>();

    private Wardrope wardrope;

    public AddUpdateWardropePresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        if (id == -1) {
            wardrope = new Wardrope();
        } else {
            initWardrope(id);
        }
    }

    private void initWardrope(long id) {
        unsubscribeOnDestroy(wardropes.getItem(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ward -> {
                    wardrope = ward;
                    checkedSet = wardrope.getThingIds();
                    getViewState().initView(wardrope);
                }));
    }

    public void addThingId(long id) {
        int length = checkedSet.size();
        checkedSet.add(id);
        if (length == checkedSet.size()) {
            checkedSet.remove(id);
        }
        getViewState().setCount(checkedSet.size());
    }

    public void save(String name) {
        wardrope.setName(name);
        unsubscribeOnDestroy(wardropes.putWardropeWithThings(wardrope, checkedSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Log.d(TAG, "put wardrope");
                    getViewState().onSave();
                }, e -> e.printStackTrace()));

    }

}
