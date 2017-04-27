package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.ThingsAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.ThingHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.BaseDbListFragment;

import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends BaseDbListFragment<Thing, ThingHolder> implements IThingsView {
    public static final int WARDROPE_MODE = 1;
    public static final int LOOK_MODE = 2;
    public static final String FRAGMENT_MODE = "mode";
    public static final String FRAGMENT_IS_EDIT = "is_edit";

    private ViewMode mode;
    private long wardropeId;
    private boolean isEdit;
    private IAddUpdateWardropeView wardropeView;
    private IFirstStepCreationLookView lookCreationView;

    public static ThingsFragment createInstance(int mode, boolean isEdit, long wardropeId) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_MODE, mode);
        bundle.putLong(WARDROPE_ID, wardropeId);
        bundle.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @InjectPresenter
    ThingsPresenter presenter;

    @ProvidePresenter
    ThingsPresenter providePresenter() {
        return new ThingsPresenter(mode, isEdit, wardropeId);
    }

    @Override
    public void onCreationStart() {
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
    }

    @Override
    public void onInitUi() {
        setTitle(R.string.things_title);
        addBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
        addBtn.setActivated(mode == ViewMode.DEFAULT);
        addBtn.setVisibility(mode == ViewMode.DEFAULT ? View.VISIBLE : View.GONE);
    }

    @Override
    public BaseDbListPresenter getPresenter() {
        return presenter;
    }

    @Override
    public BaseDbAdapter<Thing, ThingHolder> initAdapter() {
        return new ThingsAdapter();
    }

    public void setEditableMode(boolean mode) {
        adapter.clear();
        presenter.updateModeState(mode);
    }

    @Override
    public void addThingId(long id) {
        if (wardropeView != null) {
            wardropeView.addThingId(id);
        } else {
            lookCreationView.addThingId(id);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAddUpdateWardropeView) {
            wardropeView = (IAddUpdateWardropeView) context;
        } else if (context instanceof IFirstStepCreationLookView) {
            lookCreationView = (IFirstStepCreationLookView) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        wardropeView = null;
    }


    @Override
    public void setEditMode(boolean isEditMode) {
        ((ThingsAdapter) adapter).setEdit(isEditMode);
    }

    @Override
    public void addThingIdsToAdapter(HashSet<Long> set) {
        ((ThingsAdapter) adapter).setIds(set);
    }
}
