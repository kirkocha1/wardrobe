package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.ui.activities.base.ActivityToolbarDelegate;
import com.kirill.kochnev.homewardrope.ui.fragments.CollageFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.WardrobesFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;
import com.kirill.kochnev.homewardrope.utils.DialogHelper;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 27.04.17.
 */

public class CreationLookActivity extends MvpAppCompatActivity implements IFirstStepCreationLookView {

    public static final String LOOK_ID = "look_id";
    public static final String WARDROPE_ID = "wardrope_id";

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

    @InjectPresenter
    CreationLookPresenter presenter;

    @ProvidePresenter
    CreationLookPresenter providePresenter() {
        return new CreationLookPresenter(getIntent().getLongExtra(LOOK_ID, -1));
    }

    public static Intent createIntent(Context context, long lookId, long wardropeId) {
        Intent intent = new Intent(context, CreationLookActivity.class);
        intent.putExtra(LOOK_ID, lookId);
        intent.putExtra(WARDROPE_ID, wardropeId);
        return intent;
    }

    private ActivityToolbarDelegate activityToolbarDelegate = new ActivityToolbarDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_first_step_creation_look, null);
        setContentView(view);
        ButterKnife.bind(this);
        container.setDrawingCacheEnabled(true);
        activityToolbarDelegate.init(view, getString(R.string.looks_creation_title),
                v -> {
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                });
        create.setOnClickListener(v -> presenter.startCreationProcess());
        save.setOnClickListener(v -> presenter.save());
        allThings.setOnClickListener(v -> {
            presenter.clearIds(true);
            initFragment(ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, AppConstants.DEFAULT_ID), CreationLookState.ALL_THINGS);
        });

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getBackStackEntryCount() - 1;
            CreationLookState transactionState = count == AppConstants.DEFAULT_ID ? CreationLookState.START :
                    CreationLookState.valueOf(getSupportFragmentManager().getBackStackEntryAt(count).getName());
            presenter.resolveBtnsState(transactionState);
        });
        initFragment(WardrobesFragment.createInstance(ViewMode.LOOK_MODE), CreationLookState.START);
    }

    @Override
    public void showSaveDialog(String oldName, String oldTag) {
        View dialogView = getLayoutInflater().inflate(R.layout.name_tag_view, null);
        TextView nameView = (TextView) dialogView.findViewById(R.id.new_name);
        TextView tagView = (TextView) dialogView.findViewById(R.id.new_tag);
        if (oldName != null) {
            nameView.setText(oldName);
        }
        if (oldTag != null) {
            tagView.setText(oldTag);
        }
        DialogHelper.showOKCancelDialog(this, getString(R.string.look_dialog_chooser_title), dialogView, (dialog, which) -> {
            String name = nameView.getText().toString();
            String tag = tagView.getText().toString();
            presenter.processLook(name, tag, container.getDrawingCache());
            dialog.dismiss();
        }, null);

    }

    private void initFragment(Fragment fragment, CreationLookState state) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (state != CreationLookState.START) {
            transaction.addToBackStack(state.toString());
        }
        AnimationHelper.animateFragmentReplace(transaction, fragment, R.id.look_fragment_container);
    }

    @Override
    public void openCollageFragment(HashSet<Long> thingIds) {
        initFragment(CollageFragment.createInstance(thingIds), CreationLookState.COLLAGE);
    }

    @Override
    public void onSuccess(Intent intent) {
        setResult(RESULT_OK, intent);
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
