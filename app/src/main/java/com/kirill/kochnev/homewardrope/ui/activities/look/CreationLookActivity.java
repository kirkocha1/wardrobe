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
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
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
            initFragment(ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, AppConstants.DEFAULT_ID), CreationLookState.ALL_THINGS.toString());
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
                presenter.resolveBtnsState(CreationLookState.valueOf(transactionName));
            }
        });

        initFragment(WardropesFragment.createInstance(ViewMode.LOOK_MODE), CreationLookState.START.toString());
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
        initFragment(CollageFragment.createInstance(thingIds), CreationLookState.COLLAGE.toString());
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onBackPressed() {
        presenter.clearIds();
        super.onBackPressed();
    }

    @Override
    public void setBtnsState(boolean isCreateVisible, boolean isSaveVisible, boolean isAllThingsVisible) {
        create.setVisibility(isCreateVisible ? View.VISIBLE : View.GONE);
        allThings.setVisibility(isAllThingsVisible ? View.VISIBLE : View.GONE);
        save.setVisibility(isSaveVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(boolean isMin) {
        DialogHelper.showErrorSnackBar(this, isMin ? R.string.looks_error_message_min : R.string.looks_error_message_max, root);
    }
}
