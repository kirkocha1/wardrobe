package com.kirill.kochnev.homewardrope.ui.activities;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.ui.views.CollageItemView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 21.04.17.
 */

public class AddUpdateLookActivity extends BaseActionBarActivity {

    @BindView(R.id.collage_view)
    LinearLayout collage;

    @BindView(R.id.image_one)
    CollageItemView pic;

    @BindView(R.id.image_two)
    CollageItemView pic1;

    @Inject
    AbstractThingRepository rep;


    @Override
    public void onInitUi(View baseLayout) {
        setContentView(R.layout.fragment_test);
        ButterKnife.bind(this);
        WardropeApplication.getComponent().inject(this);
        rep.getItem(1).subscribe(thing -> {
            WardropeApplication.loadImage(thing.getFullImagePath(), pic);
            WardropeApplication.loadImage(thing.getFullImagePath(), pic1);
        }, e -> Log.d("ERR", e.getMessage()));
    }

    @Override
    public boolean isMenuActive() {
        return false;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }
}
