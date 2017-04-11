package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.ThingsAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.ThingHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing, ThingHolder> implements IThingsView {
    public static final int WARDROPE_MODE = 1;
    public static final String FRAGMENT_MODE = "mode";
    private ViewMode mode;
    private long wardropeId;

    private IAddUpdateWardropeView wardropeView;

    public static ThingsFragment createInstance(int mode, long wardropeId) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_MODE, mode);
        bundle.putLong(WARDROPE_ID, wardropeId);

        fragment.setArguments(bundle);
        return fragment;
    }

    @InjectPresenter
    ThingsPresenter presenter;

    @ProvidePresenter
    ThingsPresenter providePresenter() {
        return new ThingsPresenter(mode, wardropeId);
    }

    @Override
    public void onCreationStart() {
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
    }

    @Override
    public void onInitUi() {
        setTitle(R.string.things_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
        addBtn.setActivated(mode != ViewMode.WARDROPE_MODE);
        addBtn.setVisibility(mode == ViewMode.WARDROPE_MODE ? View.GONE : View.VISIBLE);
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public BaseDbAdapter<Thing, ThingHolder> initAdapter() {
        return new ThingsAdapter(mode == ViewMode.WARDROPE_MODE);
    }

    @Override
    public void addThingId(long id) {
        wardropeView.addThingId(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAddUpdateWardropeView) {
            wardropeView = (IAddUpdateWardropeView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        wardropeView = null;
    }

    @Override
    public void addThingIdsToAdapter(HashSet<Long> set) {
        ((ThingsAdapter) adapter).setIds(set);
    }
}
