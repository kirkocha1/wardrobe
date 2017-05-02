package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
import com.kirill.kochnev.homewardrope.utils.DialogHelper;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 27.04.17.
 */

public class CreationLookActivity extends BaseActionBarActivity implements IFirstStepCreationLookView {

    public static final String START_FRAGMENT_TAG = "start";

    @BindView(R.id.creation_look_main_container)
    LinearLayout root;

    @BindView(R.id.all_things)
    FloatingActionButton allThings;

    @BindView(R.id.create)
    FloatingActionButton create;

    @BindView(R.id.look_fragment_container)
    FrameLayout container;

    @BindView(R.id.save_collage)
    FloatingActionButton save;

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
            presenter.clearIds();
            initFragment(ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, AppConstants.DEFAULT_ID), START_FRAGMENT_TAG);
        });
        save.setOnClickListener(v -> {
            presenter.processLook(container.getDrawingCache());
        });

        initFragment(WardropesFragment.createInstance(ViewMode.LOOK_MODE), null);
    }

    private void initFragment(Fragment fragment, String transactionName) {
        this.fragment = fragment;
        dropBackStack();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(transactionName);
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
        initFragment(CollageFragment.createInstance(thingIds), null);
        changeBtnsVisibillity(false);
    }

    @Override
    public void onSuccess() {
        finish();
    }

    private void changeBtnsVisibillity(boolean isBack) {
        save.setVisibility(isBack ? View.GONE : View.VISIBLE);
        create.setVisibility(isBack ? View.VISIBLE : View.GONE);
        allThings.setVisibility(isBack ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        changeBtnsVisibillity(true);
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            presenter.clearIds();
            super.onBackPressed();
        }
    }

    private void dropBackStack() {
        for (int i = 0; i < getFragmentManager().getBackStackEntryCount() - 1; i++) {
            getFragmentManager().popBackStackImmediate();
        }

    }

    @Override
    public void showError(boolean isMin) {
        DialogHelper.showErrorSnackBar(this, isMin ? R.string.looks_error_message_min : R.string.looks_error_message_max, root);
    }
}
