package com.kirill.kochnev.homewardrope.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.ui.views.CollageItemView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class TestFragment extends Fragment {

    @BindView(R.id.collage_view)
    LinearLayout collage;

    @BindView(R.id.image_one)
    CollageItemView pic;

    @BindView(R.id.image_two)
    CollageItemView pic1;

    @Inject
    AbstractThingRepository rep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WardropeApplication.getComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        rep.getItem(1).subscribe(thing -> {
            WardropeApplication.loadImage(thing.getFullImagePath(), pic);
            WardropeApplication.loadImage(thing.getFullImagePath(), pic1);
        });
    }
}
