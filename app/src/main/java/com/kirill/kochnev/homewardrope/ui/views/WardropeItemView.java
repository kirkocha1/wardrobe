package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.utils.interfaces.ILoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 20.04.17.
 */

public class WardropeItemView extends FrameLayout {

    @Inject
    ILoader imageLoader;

    @BindView(R.id.wardrope_icon)
    ImageView pic;

    @BindView(R.id.wardrope_item_name)
    TextView name;

    @BindView(R.id.things_count)
    TextView thingsCount;

    @BindView(R.id.looks_count)
    TextView looksCount;

    public WardropeItemView(Context context) {
        super(context);
        initUi(context);
    }

    public WardropeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi(context);
    }

    private void initUi(Context context) {
        WardropeApplication.getComponentHolder().getMainComponent().inject(this);
        LayoutInflater.from(context).inflate(R.layout.wardrope_item_view, this, true);
        ButterKnife.bind(this);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setImage(String filePath) {
        imageLoader.loadImage(filePath, pic);
    }

    public void setThingsCount(int thingsCount) {
        this.thingsCount.setText(String.valueOf(thingsCount));
    }

    public void setLooksCount(int looksCount) {
        this.looksCount.setText(String.valueOf(looksCount));
    }
}
