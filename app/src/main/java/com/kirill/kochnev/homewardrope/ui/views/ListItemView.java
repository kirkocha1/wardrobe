package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrope.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class ListItemView extends FrameLayout {

    @BindView(R.id.small_pic)
    ImageView pic;

    @BindView(R.id.item_name)
    TextView name;

    public ListItemView(Context context) {
        super(context);
        initUi(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi(context);
    }

    private void initUi(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_list_item, this, true);
        ButterKnife.bind(this);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setImage(@DrawableRes int resourceId) {
        pic.setImageResource(resourceId);
    }
}
