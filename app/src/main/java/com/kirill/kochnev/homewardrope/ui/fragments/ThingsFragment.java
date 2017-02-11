package com.kirill.kochnev.homewardrope.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IParent;

public class ThingsFragment extends Fragment {

    private IParent parent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (IParent) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent.setTitle(R.string.things_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_things, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }
}
