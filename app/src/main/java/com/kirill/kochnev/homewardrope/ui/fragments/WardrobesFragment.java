package com.kirill.kochnev.homewardrope.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardrobeApplication;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.di.components.WardrobeListComponent;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.WardropesPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardrobeActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.WardrobesAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardrobeHolder;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ListDelegate;
import com.kirill.kochnev.homewardrope.ui.fragments.base.ToolbarDelegate;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardrobeActivity.WARDROPE_ID;

/**
 * Created by kirill on 30.03.17.
 */

public class WardrobesFragment extends MvpAppCompatFragment implements IWardropeView {
    public static final int REQUEST_CODE = 1;

    public static WardrobesFragment createInstance(@NonNull final ViewMode mode) {
        final WardrobesFragment fragment = new WardrobesFragment();
        final Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    private ListDelegate<Wardrope, WardrobeHolder> delegate;

    @NonNull
    private ToolbarDelegate toolbarDelegate = new ToolbarDelegate();

    @NonNull
    protected WardrobesAdapter adapter;

    @InjectPresenter
    WardropesPresenter presenter;

    @ProvidePresenter
    WardropesPresenter providePresenter() {
        WardrobeListComponent component = WardrobeApplication
                .getComponentHolder()
                .getWardrobeComponent(ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE)));
        return component.providePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wardrobes, container, false);
        final ViewMode mode = ViewMode.getByNum(getArguments().getInt(FRAGMENT_MODE));
        adapter = new WardrobesAdapter();
        toolbarDelegate.init(view, mode, ViewMode.WARDROPE_MODE, R.string.wardropes_title);
        delegate = new ListDelegate<>(view, adapter, presenter, mode, ViewMode.WARDROPE_MODE,
                v -> openUpdateActivity(new Intent(getContext(), AddUpdateWardrobeActivity.class)));
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WardrobeApplication.getComponentHolder().clearWardrobeComponent();
    }

    @Override
    public void navigateToAddUpdateWardropeView(Long id) {
        Intent intent = new Intent(getContext(), AddUpdateWardrobeActivity.class);
        intent.putExtra(WARDROPE_ID, id);
        openUpdateActivity(intent);
    }


    @Override
    public void setThingsByWardrope(long id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack(CreationLookState.WARDROPES.toString());
        AnimationHelper.animateFragmentReplace(transaction, ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, id), R.id.look_fragment_container);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            presenter.addOrUpdateListItem(data.getLongExtra(AppConstants.ADD_UPDATED_ID, -1));
        }
    }

    public void openUpdateActivity(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onLoadFinished(List<Wardrope> data) {
        delegate.onLoadFinished(data);
    }

    @Override
    public void invalidateListItem(Wardrope model) {
        delegate.invalidateListItem(model);
    }

    @Override
    public void deleteListItem(Wardrope model) {
        delegate.deleteListItem(model);
    }
}
