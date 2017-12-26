package com.kirill.kochnev.homewardrope.mvp.presenters.wardrobe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.AddUpdateWardrobeInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardrobeView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;

import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

@InjectViewState
public class AddUpdateWardrobePresenter extends MvpPresenter<IAddUpdateWardrobeView> {

    public static final String TAG = "AddUpdateWardrope";

    private IdBus idBus;

    private StateBus stateBus;

    private AddUpdateWardrobeInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    private HashSet<Long> thingsSet = new HashSet<>();
    private HashSet<Long> looksSet = new HashSet<>();
    private boolean isEditableMode = false;

    @Inject
    public AddUpdateWardrobePresenter(
            @Named("wardrobeId") long id,
            AddUpdateWardrobeInteractor interactor,
            StateBus stateBus,
            IdBus idBus
    ) {
        this.interactor = interactor;
        this.idBus = idBus;
        this.stateBus = stateBus;
        initWardrope(id);
    }

    private void initWardrope(long id) {
        registerForThingIds();
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getWardrobe(id)
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
        stateBus.passData(new Pair<>(ViewMode.WARDROBE_MODE, isEditableMode));
        getViewState().changeBtnsMode(isEditableMode);
    }

    private void returnInitialState() {
        interactor.getWardrobe().subscribe(wardrope -> {
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
                        .saveWardrobe(new Wardrobe(name, thingsSet, looksSet))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                    Log.d(TAG, "put wardrobe");
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
