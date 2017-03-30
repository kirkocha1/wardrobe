package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class ListItemView extends FrameLayout {

    @BindView(R.id.item_check_box)
    CheckBox box;

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

    public void setImage(String filePath) {
        WardropeApplication.loadImage(filePath, pic);
    }

    public void setBoxVisibility(boolean isVisible) {
        box.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void toogleCheck() {
        box.setChecked(!box.isChecked());
    }

}

