package com.kirill.kochnev.homewardrope.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.ui.fragments.LooksFragment;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

/**
 * Created by kirill on 11.05.17.
 */

public class WardrobePagerAdapter extends FragmentPagerAdapter {

    public static final int LOOKS_FRAGMENT_ID = 1;
    public static final int THINGS_FRAGMENT_ID = 0;
    public static final int FRAGMENTS_COUNT = 2;

    private long wardrobeId;
    private Context context;


    public WardrobePagerAdapter(Context context, FragmentManager fm, long wardrobeId) {
        super(fm);
        this.context = context;
        this.wardrobeId = wardrobeId;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        switch (position) {
            case THINGS_FRAGMENT_ID:
                name = context.getString(R.string.things_title);
                break;
            case LOOKS_FRAGMENT_ID:
                name = context.getString(R.string.looks_title);
                break;
        }
        return name;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;
        switch (position) {
            case THINGS_FRAGMENT_ID:
                result = ThingsFragment.createInstance(ViewMode.WARDROPE_MODE, wardrobeId == AppConstants.DEFAULT_ID, wardrobeId);
                break;
            case LOOKS_FRAGMENT_ID:
                result = LooksFragment.newInstance(ViewMode.WARDROPE_MODE, wardrobeId == AppConstants.DEFAULT_ID, wardrobeId);
                break;
        }
        return result;
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
