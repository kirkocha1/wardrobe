package com.kirill.kochnev.homewardrobe.presentation.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.WardrobeApplication;
import com.kirill.kochnev.homewardrobe.di.components.CollageComponent;
import com.kirill.kochnev.homewardrobe.enums.CollageMode;
import com.kirill.kochnev.homewardrobe.presentation.presenters.look.CollagePresenter;
import com.kirill.kochnev.homewardrobe.presentation.views.ICollageView;
import com.kirill.kochnev.homewardrobe.presentation.ui.views.CollageView;

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

    @InjectPresenter
    CollagePresenter presenter;

    @ProvidePresenter
    CollagePresenter providePresenter() {
        CollageComponent component = WardrobeApplication
                .getComponentHolder()
                .getCreateCollageComponent((HashSet<Long>) getArguments().getSerializable(THINGS_SET));
        return component.providePresenter();
    }

    public static CollageFragment createInstance(@NonNull final HashSet<Long> thingsId) {
        final CollageFragment fragment = new CollageFragment();
        final Bundle args = new Bundle();
        args.putSerializable(THINGS_SET, thingsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void constructView(@NonNull final SparseArray<Bitmap> cache, @NonNull final CollageMode mode) {
        container.inflateItems(mode, cache);
    }
}
