package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.ui.activities.base.DrawerActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.LooksFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.WardropesFragment;

public class MainActivity extends DrawerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(WardropesFragment.createInstance(ViewMode.WARDROPE_MODE));
        initUi();
    }

    private void initUi() {
        adjustNavigation(R.menu.navigation_menu, R.layout.navigation_header);
    }

    @Override
    public void openItem(int menuItemId) {
        switch (menuItemId) {
            case R.id.nav_things:
                setFragmentContent(ThingsFragment.createInstance(AppConstants.DEFAULT_ID, false, AppConstants.DEFAULT_ID));
                break;
            case R.id.nav_wardropes:
                setFragmentContent(WardropesFragment.createInstance(ViewMode.WARDROPE_MODE));
                break;
            case R.id.nav_looks:
                setFragmentContent(new LooksFragment());
                break;

            default:
                setFragmentContent(new WardropesFragment());
        }
    }
}
