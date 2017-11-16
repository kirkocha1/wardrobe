package com.kirill.kochnev.homewardrope.mvp.presenters.wardrope;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.AddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

@InjectViewState
public class AddUpdateWardropePresenter extends MvpPresenter<IAddUpdateWardropeView> {

    public static final String TAG = "AddUpdateWardrope";

    @Inject
    IdBus idBus;

    @Inject
    StateBus stateBus;

    @Inject
    AddUpdateWardropeInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    private HashSet<Long> thingsSet = new HashSet<>();
    private HashSet<Long> looksSet = new HashSet<>();
    private boolean isEditableMode = false;


    public AddUpdateWardropePresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        initWardrope(id);
    }

    private void initWardrope(long id) {
        registerForThingIds();
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getWardrope(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ward -> {
                                    setHashSets(ward.getLookIds(), ward.getThingIds());
                                    getViewState().initView(ward);

                                },
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    private void registerForThingIds() {
        disposableDelegate.addToCompositeDisposable(
                idBus.register(pairId -> {
                    switch (pairId.first) {
                        case LOOK_MODE:
                            updateSet(pairId.second, looksSet);
                            break;
                        case THING_MODE:
                            updateSet(pairId.second, thingsSet);
                            break;
                    }
                    getViewState().setCount(thingsSet.size(), looksSet.size());
                })
        );
    }

    private void updateSet(long id, HashSet<Long> set) {
        int length = set.size();
        set.add(id);
        if (length == set.size()) {
            set.remove(id);
        }
    }

    public void toggleMode() {
        isEditableMode = !isEditableMode;
        if (!isEditableMode) {
            returnInitialState();
        }
        stateBus.passData(new Pair<>(ViewMode.WARDROPE_MODE, isEditableMode));
        getViewState().changeBtnsMode(isEditableMode);
    }

    private void returnInitialState() {
        interactor.getWardrope().subscribe(wardrope -> {
            setHashSets(wardrope.getLookIds(), wardrope.getThingIds());
            getViewState().setCount(thingsSet.size(), looksSet.size());
        });
    }

    private void setHashSets(HashSet<Long> looksSet, HashSet<Long> thingsSet) {
        this.thingsSet = new HashSet<>(thingsSet);
        this.looksSet = new HashSet<>(looksSet);
    }

    public void save(String name) {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .saveWardrope(new Wardrope(name, thingsSet, looksSet))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                    Log.d(TAG, "put wardrope");
                                    Intent intent = new Intent();
                                    intent.putExtra(AppConstants.ADD_UPDATED_ID, result.getId());
                                    getViewState().onSave(intent);
                                },
                                e -> Log.e(TAG, e.getMessage()))
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
