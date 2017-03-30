package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing> implements IThingsView {

    public static final String FRAGMENT_MODE = "mode";
    private int mode;
    private IAddUpdateWardropeView wardropeView;

    public static ThingsFragment createInstance(int mode) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_MODE, mode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @InjectPresenter
    ThingsPresenter presenter;

    @ProvidePresenter
    ThingsPresenter providePresenter() {
        return new ThingsPresenter(mode);
    }

    @Override
    public void onInitUi() {
        setTitle(R.string.things_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
        addBtn.setActivated(mode != 1);
        addBtn.setVisibility(mode == 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void initList(List<Thing> models, boolean isWardropeMode) {
        blankImg.setVisibility(models.size() == 0 ? View.VISIBLE : View.GONE);
        adapter.setWardropeMode(isWardropeMode);
        adapter.setData(models);
        isInit = true;
        isLoading = false;
    }

    @Override
    public void initList(List<Thing> models) {

    }

    @Override
    public void addThingId(long id) {
        wardropeView.addThingId(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        wardropeView = (IAddUpdateWardropeView) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        wardropeView = null;
    }

    @Override
    public void openUpdateActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onCreationStart() {
        mode = getArguments().getInt(FRAGMENT_MODE, -1);
    }
}
