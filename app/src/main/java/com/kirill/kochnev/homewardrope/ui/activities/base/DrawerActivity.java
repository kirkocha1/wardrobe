package com.kirill.kochnev.homewardrope.ui.activities.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.activities.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class DrawerActivity extends BaseActionBarActivity implements DrawerLayout.DrawerListener {

    public static final String TAG = "DrawerActivity";

    @BindView(R.id.navigation_view)
    NavigationView navigation;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.drawer_content)
    FrameLayout content;

    Fragment fragment;

    @Override
    public void onInitUi(View baseLayout) {
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        content.addView(baseLayout);
        drawer.addDrawerListener(this);
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
        setContentView();
    }

    private void setContentView() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        AnimationHelper.animateFragmentReplace(transaction, fragment, R.id.content);
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

    protected void setFragmentContent(Fragment fragmentContent) {
        if (!fragmentContent.getClass().equals(fragment.getClass())) {
            fragment = fragmentContent;
        }
        toggleDrawer();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        setContentView();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
