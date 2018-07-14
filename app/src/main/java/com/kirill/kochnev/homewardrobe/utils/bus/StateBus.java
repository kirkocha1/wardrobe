package com.kirill.kochnev.homewardrobe.utils.bus;

import android.util.Pair;

import com.kirill.kochnev.homewardrobe.enums.ViewMode;

/**
 * Created by Kirill Kochnev on 15.05.17.
 */

public interface StateBus extends IBus<Pair<ViewMode, Boolean>> {
}
