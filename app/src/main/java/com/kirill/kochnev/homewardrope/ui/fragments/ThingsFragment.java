package com.kirill.kochnev.homewardrope.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.di.components.ThingListComponent;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.ThingsAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.ThingHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ListDelegate;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_IS_EDIT;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter.THINGS_ID;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity.IS_EDIT;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by Kirill Kochnev on 24.02.17.
 */

public class ThingsFragment extends MvpAppCompatFragment implements IThingsView {
    public static final int REQUEST_CODE = 1;
    public static ThingsFragment createInstance(ViewMode mode, boolean isEdit, long wardropeId) {
        ThingsFragment fragment = new ThingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_MODE, mode.getModeNum());
        bundle.putLong(WARDROPE_ID, wardropeId);
        bundle.putBoolean(FRAGMENT_IS_EDIT, isEdit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.list_items)
    protected RecyclerView list;

    @BindView(R.id.add)
    protected FloatingActionButton addBtn;

    @BindView(R.id.blank_image)
    protected RelativeLayout blankImg;

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @NonNull
    private BaseDbAdapter<Thing, ThingHolder> adapter;

    @NonNull
    private ListDelegate<Thing, ThingHolder> delegate;

    private ViewMode mode;
    private long wardropeId;
    private boolean isEdit;


    @InjectPresenter
    ThingsPresenter presenter;

    @ProvidePresenter
    ThingsPresenter providePresenter() {
        ThingListComponent component = WardropeApplication
                .getComponentHolder()
                .getThingListComponent(wardropeId, isEdit, mode);
        return component.providePresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE, AppConstants.DEFAULT_ID));
        wardropeId = getArguments().getLong(WARDROPE_ID, AppConstants.DEFAULT_ID);
        isEdit = getArguments().getBoolean(FRAGMENT_IS_EDIT);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_things, container, false);
        ButterKnife.bind(this, v);
        adapter = new ThingsAdapter();
        delegate = new ListDelegate<>(list, adapter, addBtn, presenter, blankImg, mode, ViewMode.THING_MODE, new GridLayoutManager(getContext(), 2));
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mode == ViewMode.THING_MODE) {
            title.setText(R.string.things_title);
            toolbar.setVisibility(View.VISIBLE);
        }
        addBtn.setOnClickListener(v -> openUpdateActivity(new Intent(getContext(), AddUpdateThingActivity.class)));
        addBtn.setActivated(mode == ViewMode.THING_MODE);
        addBtn.setVisibility(mode == ViewMode.THING_MODE ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WardropeApplication.getComponentHolder().clearThingListComponent();
    }

    @Override
    public void setEditMode(boolean isEditMode) {
        adapter.clear();
        ((ThingsAdapter) adapter).setEdit(isEditMode);
    }

    @Override
    public void addThingIdsToAdapter(HashSet<Long> set) {
        ((ThingsAdapter) adapter).setIds(set);
    }

    @Override
    public void navigateToAddUpdateThingView(boolean isEdit, Long id) {
        Intent intent = new Intent(getContext(), AddUpdateThingActivity.class);
        intent.putExtra(THINGS_ID, id);
        intent.putExtra(IS_EDIT, isEdit);
        openUpdateActivity(intent);
    }

    @Override
    public void onLoadFinished(List<Thing> data) {
        delegate.onLoadFinished(data);
    }

    @Override
    public void invalidateListItem(Thing model) {
        delegate.invalidateListItem(model);
    }

    @Override
    public void deleteListItem(Thing model) {
        delegate.deleteListItem(model);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            presenter.addOrUpdateListItem(data.getLongExtra(AppConstants.ADD_UPDATED_ID, -1));
        }
    }

    public void openUpdateActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }
}
