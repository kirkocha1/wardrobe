package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;
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
    public static final String COLLAGE_FRAGMENT_TAG = "collage";
    @BindView(R.id.creation_look_main_container)
    LinearLayout root;

    @BindView(R.id.all_things)
    FloatingActionButton allThings;

    @BindView(R.id.create_look)
    FloatingActionButton create;

    @BindView(R.id.look_fragment_container)
    FrameLayout container;

    @BindView(R.id.save_collage)
    FloatingActionButton save;

    Fragment fragment;

    @InjectPresenter
    CreationLookPresenter presenter;
    private View dialogView;

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
            initFragment(ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, AppConstants.DEFAULT_ID), null);
        });
        save.setOnClickListener(v -> {
            if (dialogView == null) {
                dialogView = getLayoutInflater().inflate(R.layout.name_tag_view, null);
            }
            DialogHelper.showOKCancelDialog(this, "Выберите имя", dialogView, (dialog, which) -> {
                String name = ((TextView) dialogView.findViewById(R.id.new_thing_name)).getText().toString();
                String tag = ((TextView) dialogView.findViewById(R.id.new_thing_tag)).getText().toString();
                presenter.processLook(name, tag, container.getDrawingCache());
                dialog.dismiss();
            }, null);
        });
        getFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getFragmentManager().getBackStackEntryCount() - 1;
            String transactionName = getFragmentManager().getBackStackEntryAt(count).getName();
            if (transactionName != null) {
                switch (transactionName) {
                    case START_FRAGMENT_TAG:
                        changeBtnsState(true);
                        break;
                    case COLLAGE_FRAGMENT_TAG:
                        changeBtnsState(false);
                        create.hide();
                        break;
                }
            } else {
                changeBtnsState(false);
                allThings.setVisibility(View.GONE);
            }
        });
        initFragment(WardropesFragment.createInstance(ViewMode.LOOK_MODE), START_FRAGMENT_TAG);
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
        initFragment(CollageFragment.createInstance(thingIds), COLLAGE_FRAGMENT_TAG);
        save.show();
    }

    @Override
    public void onSuccess() {
        finish();
    }


    private void changeBtnsState(boolean isStart) {
        create.setVisibility(isStart ? View.GONE : View.VISIBLE);
        allThings.setVisibility(isStart ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        save.hide();
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
