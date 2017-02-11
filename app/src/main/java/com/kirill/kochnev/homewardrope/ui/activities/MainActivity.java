package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.activities.base.DrawerActivity;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

public class MainActivity extends DrawerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ThingsFragment());
        initUi();
    }

    private void initUi() {
        adjustNavigation(R.menu.navigation_menu, R.layout.navigation_header);
    }

    @Override
    public void openItem(int menuItemId) {
        setFragmentContent(new ThingsFragment());
    }
}
