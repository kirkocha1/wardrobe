package com.kirill.kochnev.homewardrope.presentation.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.LooksFragment;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.presentation.ui.fragments.WardrobesFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements IDrawerController {

    public static final String TAG = "MainActivity";
    public static final String FRAGMENT_STATE_TAG = "current_fragment_tag";

    @BindView(R.id.navigation_view)
    NavigationView navigation;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private String currentFragmentName;

    @Nullable
    private Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        initUi(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FRAGMENT_STATE_TAG, currentFragmentName);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setContentView(@NonNull final Fragment fragment) {
        this.fragment = fragment;
        currentFragmentName = fragment.getClass().getName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AnimationHelper.animateFragmentReplace(transaction, fragment, R.id.content, currentFragmentName);
        getFragmentManager().executePendingTransactions();
        Log.d(TAG, "set fragment " + fragment.getClass());
    }

    public void adjustNavigation(@MenuRes int menu, @LayoutRes int layoutId) {
        navigation.inflateHeaderView(layoutId);
        navigation.inflateMenu(menu);
    }

    public void toggleDrawer() {
        if (!drawer.isDrawerOpen(navigation)) {
            drawer.openDrawer(navigation);
        } else {
            drawer.closeDrawer(navigation);
        }
    }

    public void openItem(int menuItemId) {
        switch (menuItemId) {
            case R.id.nav_things:
                setFragmentContent(ThingsFragment.createInstance(ViewMode.THING_MODE, false, AppConstants.DEFAULT_ID));
                break;
            case R.id.nav_wardropes:
                setFragmentContent(WardrobesFragment.createInstance(ViewMode.WARDROBE_MODE));
                break;
            case R.id.nav_looks:
                setFragmentContent(LooksFragment.newInstance(ViewMode.LOOK_MODE, false, AppConstants.DEFAULT_ID));
                break;

            default:
                setFragmentContent(new WardrobesFragment());
        }
    }

    private void initUi(@Nullable final Bundle savedInstanceState) {
        drawer.addDrawerListener(new DrawerDelegate());
        navigation.setNavigationItemSelectedListener(menu -> {
            openItem(menu.getItemId());
            return false;
        });
        adjustNavigation(R.menu.navigation_menu, R.layout.navigation_header);
        if (savedInstanceState != null) {
            currentFragmentName = savedInstanceState.getString(FRAGMENT_STATE_TAG, WardrobesFragment.class.getName());
        } else {
            setContentView(WardrobesFragment.createInstance(ViewMode.WARDROBE_MODE));
        }
    }


    private void setFragmentContent(@NonNull final Fragment fragmentContent) {
        if (!currentFragmentName.equals(fragmentContent.getClass().getName())) {
            currentFragmentName = fragmentContent.getClass().getName();
            fragment = fragmentContent;
        } else {
            if (fragment == null) {
                fragment = getSupportFragmentManager().findFragmentByTag(currentFragmentName);
            }
        }
        toggleDrawer();
    }

    private class DrawerDelegate implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (fragment != null) {
                setContentView(fragment);
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }

}
