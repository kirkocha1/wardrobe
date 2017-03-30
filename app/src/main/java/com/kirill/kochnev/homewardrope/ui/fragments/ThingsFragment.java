package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing> implements IThingsView {

    @InjectPresenter
    ThingsPresenter presenter;

    @Override
    public void onInitUi() {
        setTitle(R.string.things_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void initList(List<Thing> models) {
        blankImg.setVisibility(models.size() == 0 ? View.VISIBLE : View.GONE);
        adapter.setData(models);
        isInit = true;
        isLoading = false;
    }


    @Override
    public void openUpdateActivity(Intent intent) {
        startActivity(intent);
    }

}
