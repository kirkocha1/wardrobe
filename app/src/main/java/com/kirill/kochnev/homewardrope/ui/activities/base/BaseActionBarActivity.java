package com.kirill.kochnev.homewardrope.ui.activities.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.databinding.ActivityBaseActionBarBinding;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IActionBarController;


public abstract class BaseActionBarActivity extends MvpAppCompatActivity implements IActionBarController {

    private ActivityBaseActionBarBinding binding;
    private Consumer<View> menuClick;

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
                menuClick.accept(v);
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
            super.setContentView(binding.getRoot());
        }
    }


    //TODO CONTRACT should call setContentView on child class
    public abstract void onInitUi(View baseLayout);

    public abstract boolean isMenuActive();

    public abstract boolean isSearchActive();

    public void setBackButtonEnabled(boolean isEnable) {
        binding.backAction.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    public void setTitle(@StringRes int resId) {
        binding.title.setText(getString(resId));
    }

    public void setTitleText(String title) {
        binding.title.setText(title);
    }


    public void setMenuClickListener(Consumer<View> listener) {
        menuClick = listener;
    }

    public interface Consumer<T> {
        void accept(T t);
    }

}
