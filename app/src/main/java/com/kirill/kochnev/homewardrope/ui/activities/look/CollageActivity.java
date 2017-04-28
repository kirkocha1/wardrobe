package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.util.SparseArray;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;
import com.kirill.kochnev.homewardrope.ui.activities.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.ui.views.CollageView;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.ui.fragments.CollageFragment.THINGS_SET;

/**
 * Created by kirill on 28.04.17.
 */

public class CollageActivity extends BaseActionBarActivity implements ICollageView {

    @BindView(R.id.collage_container)
    CollageView conatiner;

    @BindView(R.id.create_look_btn)
    FloatingActionButton save;


    @InjectPresenter
    CollagePresenter presenter;

    @ProvidePresenter
    CollagePresenter providePresenter() {
        return new CollagePresenter((HashSet<Long>) getIntent().getSerializableExtra(THINGS_SET));
    }

    @Override
    public void onInitUi(View baseLayout) {
        setContentView(View.inflate(this, R.layout.fragment_collage, null));
        ButterKnife.bind(this);
        save.setOnClickListener(v -> {

        });
    }

    @Override
    public boolean isMenuActive() {
        return false;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }

    @Override
    public void constructView(SparseArray<Bitmap> cache, CollageMode mode) {
        conatiner.inflateItems(mode, cache);
    }
}
