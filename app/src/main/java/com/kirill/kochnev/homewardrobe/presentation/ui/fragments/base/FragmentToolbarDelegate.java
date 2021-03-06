package com.kirill.kochnev.homewardrobe.presentation.ui.fragments.base;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentToolbarDelegate {

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.menu_btn)
    protected ImageView menuBtn;

    public void init(
            @NonNull View view,
            @NonNull ViewMode mode,
            @NonNull ViewMode mainMode,
            @StringRes int titleRes
    ) {
        ButterKnife.bind(this, view);
        if (mode == mainMode) {
            title.setText(titleRes);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    public void setMenuListener(@NonNull final View.OnClickListener listener) {
        menuBtn.setOnClickListener(listener);
    }
}
