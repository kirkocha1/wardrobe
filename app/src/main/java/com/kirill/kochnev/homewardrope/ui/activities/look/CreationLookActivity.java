package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.ui.activities.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.CollageFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.WardropesFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 27.04.17.
 */

public class CreationLookActivity extends BaseActionBarActivity implements IFirstStepCreationLookView {

    @BindView(R.id.all_things)
    Button allThings;

    @BindView(R.id.create)
    Button create;

    @BindView(R.id.look_fragment_container)
    FrameLayout container;

    @BindView(R.id.save_collage)
    Button save;

    Fragment fragment;

    @InjectPresenter
    CreationLookPresenter presenter;

    @Override
    public void onInitUi(View baseLayout) {
        setContentView(View.inflate(this, R.layout.activity_first_step_creation_look, null));
        setBackButtonEnabled(true);
        setTitleText(getString(R.string.looks_creation_title));
        ButterKnife.bind(this);
        container.setDrawingCacheEnabled(true);
        create.setOnClickListener(v -> {
            presenter.startCreationProcess();
        });
        allThings.setOnClickListener(v -> {
            initFragment(ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, AppConstants.DEFAULT_ID));
        });
        save.setOnClickListener(v -> {
            presenter.processLook(container.getDrawingCache());
        });

        initFragment(WardropesFragment.createInstance(ViewMode.LOOK_MODE));
    }

    private void initFragment(Fragment fragment) {
        this.fragment = fragment;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        AnimationHelper.animateFragmentReplace(transaction, fragment, R.id.look_fragment_container);
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
    public void addThingId(long id) {
        presenter.addThingId(id);
    }

    @Override
    public void openCollageFragment(HashSet<Long> thingIds) {
        initFragment(CollageFragment.createInstance(thingIds));
    }

    @Override
    public void onSuccess() {
        finish();
    }
}
