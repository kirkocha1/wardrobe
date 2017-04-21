package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateLookActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.LooksAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksFragment extends BaseDbListFragment<Look, LookHolder> implements ILooksView {

    @InjectPresenter
    LooksPresenter presenter;

    @Override
    public BaseDbAdapter<Look, LookHolder> initAdapter() {
        return new LooksAdapter();
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onInitUi() {
        setTitle(R.string.looks_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateLookActivity.class)));
    }
}
