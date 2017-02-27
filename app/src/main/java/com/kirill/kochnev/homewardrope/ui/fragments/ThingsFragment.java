package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IHolderModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddOrUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IParent;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing> implements IThingsView {

    @InjectPresenter
    ThingsPresenter presenter;

    private IParent parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        parent.setTitle(R.string.things_title);
        addBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddOrUpdateThingActivity.class));
        });
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void initList(List<Thing> models) {
        adapter.setData(models);
        isInit = true;
        isLoading = false;
    }

    @Override
    public void onLoadFinished(List<Thing> data) {
        list.post(() -> {
            isLoading = false;
            adapter.addData(data);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (IParent) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
