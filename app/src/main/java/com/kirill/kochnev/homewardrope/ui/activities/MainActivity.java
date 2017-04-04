package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.activities.base.DrawerActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.TestFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.WardropeFragment;

public class MainActivity extends DrawerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestFragment());
        initUi();
    }

    private void initUi() {
        adjustNavigation(R.menu.navigation_menu, R.layout.navigation_header);
    }

    @Override
    public void openItem(int menuItemId) {
        switch (menuItemId) {
            case R.id.nav_things:
                setFragmentContent(ThingsFragment.createInstance(-1, -1));
                break;
            case R.id.nav_wardropes:
                setFragmentContent(new WardropeFragment());
                break;
            default:
                setFragmentContent(new TestFragment());
        }
    }
}
