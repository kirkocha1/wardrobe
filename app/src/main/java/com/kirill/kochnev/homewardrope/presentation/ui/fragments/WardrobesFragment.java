package com.kirill.kochnev.homewardrope.presentation.ui.fragments;


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
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.di.components.WardrobeListComponent;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.presentation.presenters.wardrobe.WardrobesPresenter;
import com.kirill.kochnev.homewardrope.presentation.ui.activities.IDrawerController;
import com.kirill.kochnev.homewardrope.presentation.ui.activities.PutWardrobeActivity;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.WardrobesAdapter;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.base.FragmentToolbarDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.base.ListDelegate;
import com.kirill.kochnev.homewardrope.presentation.views.IWardrobeView;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.kirill.kochnev.homewardrope.AppConstants.FRAGMENT_MODE;
import static com.kirill.kochnev.homewardrope.presentation.ui.activities.PutWardrobeActivity.WARDROPE_ID;

/**
 * Created by kirill on 30.03.17.
 */

public class WardrobesFragment extends MvpAppCompatFragment implements IWardrobeView {
    public static final int REQUEST_CODE = 1;

    public static WardrobesFragment createInstance(@NonNull final ViewMode mode) {
        final WardrobesFragment fragment = new WardrobesFragment();
        final Bundle args = new Bundle();
        args.putInt(FRAGMENT_MODE, mode.getModeNum());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    private ListDelegate<Wardrobe> delegate;

    @NonNull
    private FragmentToolbarDelegate fragmentToolbarDelegate = new FragmentToolbarDelegate();

    @NonNull
    protected WardrobesAdapter adapter;

    @InjectPresenter
    WardrobesPresenter presenter;

    @ProvidePresenter
    WardrobesPresenter providePresenter() {
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
        fragmentToolbarDelegate.init(view, mode, ViewMode.WARDROBE_MODE, R.string.wardropes_title);
        delegate = new ListDelegate<>(view, adapter, presenter, mode, ViewMode.WARDROBE_MODE,
                v -> startActivityForResult(new Intent(getContext(), PutWardrobeActivity.class), REQUEST_CODE));

        fragmentToolbarDelegate.setMenuListener(v -> {
            if (getActivity() instanceof IDrawerController) {
                final IDrawerController drawer = (IDrawerController) getActivity();
                drawer.toggleDrawer();
            }
        });
        return view;

    }

    @Override
    public void navigateToAddUpdateWardropeView(Long id) {
        Intent intent = new Intent(getContext(), PutWardrobeActivity.class);
        intent.putExtra(WARDROPE_ID, id);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void navigateToThingsFilteredByWardrope(long id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack(CreationLookState.WARDROBES.toString());
        AnimationHelper.animateFragmentReplace(transaction, ThingsFragment.createInstance(ViewMode.LOOK_MODE, true, id), R.id.look_fragment_container);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            presenter.putListItem(data.getLongExtra(AppConstants.ADD_UPDATED_ID, -1));
        }
    }

    @Override
    public void onLoadFinished(List<Wardrobe> data) {
        delegate.onLoadFinished(data);
    }

    @Override
    public void invalidateListItem(Wardrobe model) {
        delegate.invalidateListItem(model);
    }

    @Override
    public void deleteListItem(Wardrobe model) {
        delegate.deleteListItem(model);
    }
}
