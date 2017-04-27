package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class FirstStepCreationLookPresenter extends BaseMvpPresenter<IFirstStepCreationLookView> {

    private Look model;

    @Inject
    AbstractLookRepository looks;

    public FirstStepCreationLookPresenter() {
        WardropeApplication.getComponent().inject(this);
        model = new Look();
    }

    public void addThingId(long id) {
        Set<Long> thingsSet = model.getThingIds();
        int length = thingsSet.size();
        thingsSet.add(id);
        if (length == thingsSet.size()) {
            thingsSet.remove(id);
        } else {
            Toast.makeText(WardropeApplication.getContext(), "thing with id: " + id + " was added to look", Toast.LENGTH_SHORT).show();
        }

    }

    public void createLook() {
        getViewState().openCollageFragment(model.getThingIds());
    }

}
