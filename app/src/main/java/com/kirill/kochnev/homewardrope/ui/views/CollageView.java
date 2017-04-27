package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.CollageMode;

import java.util.List;

/**
 * Created by kirill on 27.04.17.
 */

public class CollageView extends FrameLayout {

    private CollageMode mode;
    private List<Thing> things;
    private ViewGroup view;

    public CollageView(@NonNull Context context, CollageMode mode) {
        super(context);
        this.mode = mode;
        initUi(context);
    }


    private void initUi(Context context) {
        view = (ViewGroup) LayoutInflater.from(context).inflate(mode.getLayout(), null, false);
        addView(view);

    }

    public void setThings(List<Thing> things) {
        this.things = things;
        try {
            for (int i = 0; i < things.size(); i++) {
                CollageItemView item = (CollageItemView) view.findViewById(mode.getViewIds()[i]);
                WardropeApplication.loadImage(things.get(i).getFullImagePath(), item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
