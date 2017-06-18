package com.kirill.kochnev.homewardrope.mvp.presenters.wardrope;

import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
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
public class AddUpdateWardropePresenter extends BaseMvpPresenter<IAddUpdateWardropeView> {

    public static final String TAG = "AddUpdateWardrope";

    @Inject
    IdBus idBus;

    @Inject
    StateBus stateBus;

    @Inject
    IAddUpdateWardropeInteractor interactor;

    private HashSet<Long> thingsSet = new HashSet<>();
    private HashSet<Long> looksSet = new HashSet<>();

    private boolean isEditableMode = false;


    public AddUpdateWardropePresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        initWardrope(id);
    }

    private void initWardrope(long id) {
        registerForThingIds();
        unsubscribeOnDestroy(interactor.getWardrope(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ward -> {
                    setHashSets(ward.getLookIds(), ward.getThingIds());
                    getViewState().initView(ward);

                }, e -> Log.e(TAG, e.getMessage())));
    }

    private void registerForThingIds() {
        unsubscribeOnDestroy(idBus.register(pairId -> {
            switch (pairId.first) {
                case LOOK_MODE:
                    updateSet(pairId.second, looksSet);
                    break;
                case THING_MODE:
                    updateSet(pairId.second, thingsSet);
                    break;
            }
            getViewState().setCount(thingsSet.size(), looksSet.size());
        }));
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
        Pair<HashSet<Long>, HashSet<Long>> startSets = interactor.getStartIds();
        if (startSets != null) {
            setHashSets(startSets.first, startSets.second);
            getViewState().setCount(thingsSet.size(), looksSet.size());
        }
    }

    private void setHashSets(HashSet<Long> looksSet, HashSet<Long> thingsSet) {
        this.thingsSet = new HashSet<>(thingsSet);
        this.looksSet = new HashSet<>(looksSet);
    }

    public void save(String name) {
        unsubscribeOnDestroy(interactor.saveWardrope(name, thingsSet, looksSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Log.d(TAG, "put wardrope");
                    getViewState().onSave();
                }, e -> Log.e(TAG, e.getMessage())));
    }
}
