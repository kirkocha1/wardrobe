package com.kirill.kochnev.homewardrope.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;
import com.kirill.kochnev.homewardrope.ui.views.CollageView;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 27.04.17.
 */

public class CollageFragment extends MvpAppCompatFragment implements ICollageView {

    public static final String THINGS_SET = "things_set";

    @BindView(R.id.collage_container)
    CollageView container;

    @BindView(R.id.title)
    protected TextView title;

    @InjectPresenter
    CollagePresenter presenter;

    @ProvidePresenter
    CollagePresenter providePresenter() {
        return new CollagePresenter((HashSet<Long>) getArguments().getSerializable(THINGS_SET));
    }

    public static CollageFragment createInstance(HashSet<Long> thingsId) {
        CollageFragment fragment = new CollageFragment();
        Bundle agrs = new Bundle();
        agrs.putSerializable(THINGS_SET, thingsId);
        fragment.setArguments(agrs);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collage, container, false);
        ButterKnife.bind(this, view);
        title.setText(R.string.looks_title);
        return view;
    }

    @Override
    public void constructView(SparseArray<Bitmap> cache, CollageMode mode) {
        container.inflateItems(mode, cache);
    }
}
