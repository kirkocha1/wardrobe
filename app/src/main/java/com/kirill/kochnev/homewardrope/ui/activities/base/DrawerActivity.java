package com.kirill.kochnev.homewardrope.ui.activities.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IParent;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class DrawerActivity extends AppCompatActivity implements DrawerLayout.DrawerListener, IParent {

    public static final String TAG = "DrawerActivity";

    @BindView(R.id.navigation_view)
    NavigationView navigation;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.menu_btn)
    ImageView menu;

    @BindView(R.id.title)
    TextView title;

    Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        drawer.addDrawerListener(this);
        setSupportActionBar(toolbar);

        navigation.setNavigationItemSelectedListener(menu -> {
            openItem(menu.getItemId());
            return false;
        });
        menu.setOnClickListener(v -> toggleDrawer());
    }

    public abstract void openItem(int menuItemId);

    public void setContentView(Fragment fragment) {
        this.fragment = fragment;
        setContentView();
    }

    public void setTitle(@StringRes int titleId) {
        title.setText(getResources().getString(titleId));
    }

    private void setContentView() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
        transaction.replace(R.id.content, fragment).commit();
        manager.executePendingTransactions();
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
