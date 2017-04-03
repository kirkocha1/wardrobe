package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.List;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropeFragment extends BaseDbListFragment<Wardrope> implements IWardropeView {


    @InjectPresenter
    WardropePresenter presenter;

    @Override
    public void initList(List<Wardrope> models) {
        blankImg.setVisibility(models == null || models.size() == 0 ? View.VISIBLE : View.GONE);
        adapter.setData(models);
        isInit = true;
        isLoading = false;
    }

    @Override
    public void onLoadFinished(List<Wardrope> data) {
        list.post(() -> {
            isLoading = false;
            adapter.addData(data);
        });
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
    public void openUpdateActivity(Intent intent) {

    }
}
