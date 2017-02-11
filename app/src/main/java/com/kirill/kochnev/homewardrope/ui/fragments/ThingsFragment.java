package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.interfaces.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IParent;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment implements IThingsView{

    @InjectPresenter
    ThingsPresenter presenter;

    private IParent parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View baseView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, baseView);
        parent.setTitle(R.string.things_title);
        return baseView;
    }

    @Override
    public void initList(List<Thing> things) {
        list.setAdapter(new DbListAdapter<>(things));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (IParent) context;
    }

}
