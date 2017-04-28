package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropesPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.WardropesAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardropeHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

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
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
