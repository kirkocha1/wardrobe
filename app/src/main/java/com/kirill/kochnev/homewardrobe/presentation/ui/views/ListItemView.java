package com.kirill.kochnev.homewardrobe.presentation.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.WardrobeApplication;
import com.kirill.kochnev.homewardrobe.utils.interfaces.ILoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class ListItemView extends FrameLayout {

    @Inject
    ILoader imageLoader;

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
        WardrobeApplication.getComponentHolder().getMainComponent().inject(this);
        LayoutInflater.from(context).inflate(R.layout.view_list_item, this, true);
        ButterKnife.bind(this);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setImage(String filePath) {
        imageLoader.loadImage(filePath, pic);
    }

    public void setBoxVisibility(boolean isVisible) {
        box.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void toogleCheck() {
        box.setChecked(!box.isChecked());
    }

    public void setCheck(boolean isChecked) {
        box.setChecked(isChecked);
    }


}

