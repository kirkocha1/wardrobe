package com.kirill.kochnev.homewardrope.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.databinding.ActivityBaseActionBarBinding;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IActionBarController;

import rx.functions.Action1;

public abstract class BaseActionBarActivity extends AppCompatActivity implements IActionBarController {

    private ActivityBaseActionBarBinding binding;
    private Action1<View> menuClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    private void initUi() {
        View view = getLayoutInflater().inflate(R.layout.activity_base_action_bar, null);
        binding = DataBindingUtil.bind(view);
        binding.menuBtn.setVisibility(isMenuActive() ? View.VISIBLE : View.GONE);
        binding.backAction.setVisibility(isMenuActive() ? View.GONE : View.VISIBLE);
        binding.backAction.setOnClickListener(v -> onBackPressed());
        binding.search.setVisibility(isSearchActive() ? View.VISIBLE : View.GONE);
        binding.menuBtn.setOnClickListener(v -> {
            if (menuClick != null) {
                menuClick.call(v);
            }
        });
        onInitUi(view);
        setSupportActionBar(binding.toolbar);
    }


    @Override
    public void setContentView(View view) {
        setContentView(false, view);
    }

    public void setContentView(boolean isParent, View view) {
        if (isParent) {
            super.setContentView(view);
        } else {
            binding.content.addView(view);
        }
    }


    //TODO CONTRACT should call setContentView on child class
    public abstract void onInitUi(View baseLayout);

    public abstract boolean isMenuActive();

    public abstract boolean isSearchActive();

    public void setTitle(@StringRes int resId) {
        binding.title.setText(getString(resId));
    }

    public void setTitleText(String title) {
        binding.title.setText(title);
    }


    public void setMenuClickListener(Action1<View> listener) {
        menuClick = listener;
    }
}
