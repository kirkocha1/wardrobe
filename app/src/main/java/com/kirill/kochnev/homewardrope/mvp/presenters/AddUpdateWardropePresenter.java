package com.kirill.kochnev.homewardrope.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import java.util.HashSet;

import javax.inject.Inject;

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

}
