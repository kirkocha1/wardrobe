package com.kirill.kochnev.homewardrope.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.WardropesPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.WardropesAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardropeHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropesFragment extends BaseDbListFragment<Wardrope, WardropeHolder> implements IWardropeView {

    public static WardropesFragment createInstance(ViewMode mode) {
        WardropesFragment fragment = new WardropesFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    WardropesPresenter presenter;

    @ProvidePresenter
    WardropesPresenter providePresenter() {
        return new WardropesPresenter(ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE)));
    }

    @Override
    public BaseDbAdapter<Wardrope, WardropeHolder> initAdapter() {
        return new WardropesAdapter();
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onInitUi() {
        ViewMode mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE));
        if (mode == ViewMode.WARDROPE_MODE) {
            setTitle(R.string.wardropes_title);
        }
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateWardropeActivity.class)));
        addBtn.setActivated(mode == ViewMode.WARDROPE_MODE);
        addBtn.setVisibility(mode == ViewMode.WARDROPE_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setThingsByWardrope(long id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack(CreationLookState.WARDROPES.toString());
        AnimationHelper.animateFragmentReplace(transaction, ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, id), R.id.look_fragment_container);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
