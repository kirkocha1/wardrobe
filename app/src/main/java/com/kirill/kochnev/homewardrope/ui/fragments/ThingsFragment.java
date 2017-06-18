package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.ThingsAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.ThingHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing, ThingHolder> implements IThingsView {

    private ViewMode mode;
    private long wardropeId;
    private boolean isEdit;

    public static ThingsFragment createInstance(ViewMode mode, boolean isEdit, long wardropeId) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_MODE, mode.getModeNum());
        bundle.putLong(WARDROPE_ID, wardropeId);
        bundle.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @InjectPresenter
    ThingsPresenter presenter;

    @ProvidePresenter
    ThingsPresenter providePresenter() {
        return new ThingsPresenter(mode, isEdit, wardropeId);
    }

    @Override
    public void onCreationStart() {
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
    }

    @Override
    public void onInitUi() {
        if (mode == ViewMode.THING_MODE) {
            setTitle(R.string.things_title);
        }
        addBtn.setOnClickListener(v -> openUpdateActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
        addBtn.setActivated(mode == ViewMode.THING_MODE);
        addBtn.setVisibility(mode == ViewMode.THING_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public BaseDbAdapter<Thing, ThingHolder> initAdapter() {
        return new ThingsAdapter();
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        adapter.clear();
        ((ThingsAdapter) adapter).setEdit(isEditMode);
    }

    @Override
    public void addThingIdsToAdapter(HashSet<Long> set) {
        ((ThingsAdapter) adapter).setIds(set);
    }

}
