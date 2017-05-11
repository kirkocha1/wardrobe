package com.kirill.kochnev.homewardrope.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.ui.fragments.LooksFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

/**
 * Created by kirill on 11.05.17.
 */

public class WardropePagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;
    private long wardropeId;


    public WardropePagerAdapter(FragmentManager fm, long wardropeId) {
        super(fm);
        this.wardropeId = wardropeId;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        switch (position) {
            case 0:
                name = "Вещи";
                break;
            case 1:
                name = "Образы";
                break;
        }
        return name;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragment != null && fragment instanceof ThingsFragment ) {
                    return fragment;
                }
                fragment = ThingsFragment.createInstance(ViewMode.WARDROPE_MODE, wardropeId == -1, wardropeId);
                break;
            case 1:
                fragment = new LooksFragment();
                break;

        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
