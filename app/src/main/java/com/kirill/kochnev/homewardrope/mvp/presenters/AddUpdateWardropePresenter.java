package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

@InjectViewState
public class AddUpdateWardropePresenter extends BaseMvpPresenter<IAddUpdateWardropeView> {

    @Inject
    protected AbstractWardropeRepository wardropes;

    private HashSet<Long> checkedSet = new HashSet<>();

    public AddUpdateWardropePresenter() {
        WardropeApplication.getComponent().inject(this);
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
        wardropes.putWardropeWithThings(name, checkedSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Toast.makeText(WardropeApplication.getContext(), "ADDD", Toast.LENGTH_SHORT).show();
                }, e -> e.printStackTrace());
    }

}
