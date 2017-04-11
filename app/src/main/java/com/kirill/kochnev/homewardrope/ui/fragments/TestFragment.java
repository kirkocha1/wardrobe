package com.kirill.kochnev.homewardrope.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.views.TestView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public class TestFragment extends Fragment {

    @BindView(R.id.collage_view)
    FrameLayout collage;

    TestView pic;
    TestView pic1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        pic = new TestView(getContext());
        pic1 = new TestView(getContext());
        pic.setMaxWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        pic1.setMaxWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));

//        pic.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        pic1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        collage.addView(pic);
        collage.addView(pic1);
    }
}
