package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.look.CreationLookActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.LooksAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksFragment extends BaseDbListFragment<Look, LookHolder> implements ILooksView {

    public static LooksFragment newInstance(ViewMode mode, boolean isEdit, long wardropeId) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        args.putLong(WARDROPE_ID, wardropeId);
        args.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        LooksFragment fragment = new LooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    LooksPresenter presenter;

    @ProvidePresenter
    LooksPresenter providePresenter() {
        ViewMode mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        long wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        boolean isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        return new LooksPresenter(mode, isEdit, wardropeId);
    }



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
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), CreationLookActivity.class)));
        addBtn.setActivated(true);
    }
}
