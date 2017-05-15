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

    public static final int LOOKS_FRAGMENT_ID = 1;
    public static final int THINGS_FRAGMENT_ID = 0;
    public static final int FRAGMENTS_COUNT = 2;

    private long wardropeId;

    public WardropePagerAdapter(FragmentManager fm, long wardropeId) {
        super(fm);
        this.wardropeId = wardropeId;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        switch (position) {
            case THINGS_FRAGMENT_ID:
                name = "Вещи";
                break;
            case LOOKS_FRAGMENT_ID:
                name = "Образы";
                break;
        }
        return name;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;
        switch (position) {
            case THINGS_FRAGMENT_ID:
                result = ThingsFragment.createInstance(ViewMode.WARDROPE_MODE, false, wardropeId);
                break;
            case LOOKS_FRAGMENT_ID:
                result = LooksFragment.newInstance(ViewMode.WARDROPE_MODE, false, wardropeId);
                break;
        }
        return result;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
