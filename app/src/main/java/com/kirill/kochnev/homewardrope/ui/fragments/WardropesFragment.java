package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropesPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.WardropesAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardropeHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropesFragment extends BaseDbListFragment<Wardrope, WardropeHolder> implements IWardropeView {

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
        setTitle(R.string.wardropes_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateWardropeActivity.class)));
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }
}
