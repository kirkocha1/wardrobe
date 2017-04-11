package com.kirill.kochnev.homewardrope.ui.fragments;

import android.app.Fragment;
import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class TestFragment extends Fragment {

    @BindView(R.id.test_image)
    ImageView pic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        pic.setTag("TEST");
        pic.setOnLongClickListener(v -> {
            ClipData.Item item = new ClipData.Item(v.getTag().toString());
            ClipData dragData = new ClipData(v.getTag().toString(), new String[]{"text/plain"}, item);

            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(pic);
            v.startDrag(dragData, myShadow, null, 0);
            return true;
        });
    }
}
