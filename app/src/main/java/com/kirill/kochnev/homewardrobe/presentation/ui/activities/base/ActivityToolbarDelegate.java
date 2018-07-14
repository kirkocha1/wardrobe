package com.kirill.kochnev.homewardrobe.presentation.ui.activities.base;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrobe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityToolbarDelegate {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.back_action)
    ImageView backButton;


    public void init(@NonNull final View view, @NonNull final String titleRes, @NonNull final View.OnClickListener listener) {
        ButterKnife.bind(this, view);
        backButton.setOnClickListener(listener);
        title.setText(titleRes);
    }

    public void updateTitle(@NonNull final String title) {
        this.title.setText(title);
    }

}
