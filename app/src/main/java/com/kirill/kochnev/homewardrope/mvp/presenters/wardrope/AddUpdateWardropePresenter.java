package com.kirill.kochnev.homewardrope.mvp.presenters.wardrope;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;

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
    private boolean isEditableMode = false;

    @Inject
    IdBus bus;

    @Inject
    IAddUpdateWardropeInteractor interactor;

    private HashSet<Long> thingsSet = new HashSet<>();

    public AddUpdateWardropePresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        initWardrope(id);
    }

    @Override
    public void attachView(IAddUpdateWardropeView view) {
        super.attachView(view);
    }

    private void initWardrope(long id) {
        registerForThingIds();
        unsubscribeOnDestroy(interactor.getWardrope(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ward -> {
                    thingsSet = ward.getThingIds();
                    getViewState().initView(ward);

                }, e -> Log.e(TAG, e.getMessage())));
    }

    private void registerForThingIds() {
        unsubscribeOnDestroy(bus.register(id -> {
            int length = thingsSet.size();
            thingsSet.add(id);
            if (length == thingsSet.size()) {
                thingsSet.remove(id);
            }
            getViewState().setCount(thingsSet.size());
        }));
    }

    public void toggleMode() {
        isEditableMode = !isEditableMode;
        getViewState().changeFragmentMode(isEditableMode);
    }

    public void save(String name) {
        unsubscribeOnDestroy(interactor.saveWardrope(name, thingsSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Log.d(TAG, "put wardrope");
                    getViewState().onSave();
                }, e -> Log.e(TAG, e.getMessage())));

    }

}
