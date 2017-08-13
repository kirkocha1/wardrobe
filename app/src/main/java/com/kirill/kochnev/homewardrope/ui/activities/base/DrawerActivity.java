package com.kirill.kochnev.homewardrope.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.fragments.WardropesFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class DrawerActivity extends BaseActionBarActivity {

    public static final String TAG = "DrawerActivity";
    public static final String FRAGMENT_STATE_TAG = "current_fragment_tag";

    @BindView(R.id.navigation_view)
    NavigationView navigation;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.drawer_content)
    FrameLayout content;

    private String currentFragmentName;

    Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentFragmentName = savedInstanceState.getString(FRAGMENT_STATE_TAG, WardropesFragment.class.getName());
        }
    }

    @Override
    public void onInitUi(View baseLayout) {
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        content.addView(baseLayout);
        drawer.addDrawerListener(new DrawerDelegate());
        navigation.setNavigationItemSelectedListener(menu -> {
            openItem(menu.getItemId());
            return false;
        });
        setMenuClickListener(v -> toggleDrawer());
    }

    @Override
    public boolean isMenuActive() {
        return true;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }

    public abstract void openItem(int menuItemId);

    public void setContentView(Fragment fragment) {
        this.fragment = fragment;
        currentFragmentName = fragment.getClass().getName();
        setContentView();
    }

    private void setContentView() {
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FRAGMENT_STATE_TAG, currentFragmentName);
    }

    protected void setFragmentContent(Fragment fragmentContent) {
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            setContentView();
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }
}
