package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.mvp.presenters.FirstStepCreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.ui.activities.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.CollageFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

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

    Fragment fragment;

    @InjectPresenter
    FirstStepCreationLookPresenter presenter;

    @Override
    public void onInitUi(View baseLayout) {
        setContentView(View.inflate(this, R.layout.activity_first_step_creation_look, null));
        setBackButtonEnabled(true);
        setTitleText(getString(R.string.looks_creation_title));
        ButterKnife.bind(this);
        initFragment(ThingsFragment.createInstance(ThingsFragment.LOOK_MODE, true, AppConstants.DEFAULT_ID));
        create.setOnClickListener(v -> {
            presenter.createLook();
        });
        allThings.setOnClickListener(v -> {
        });
    }

    private void initFragment(Fragment fragment) {
        this.fragment = fragment;
        getFragmentManager().beginTransaction()
                .replace(R.id.look_fragment_container, fragment)
                .commit();
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
}
