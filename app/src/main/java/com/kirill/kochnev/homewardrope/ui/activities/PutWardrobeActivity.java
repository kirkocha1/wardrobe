package com.kirill.kochnev.homewardrope.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardrobeApplication;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.di.components.PutWardrobeComponent;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrobe.PutWardrobePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IPutWardrobeView;
import com.kirill.kochnev.homewardrope.ui.activities.base.ActivityToolbarDelegate;
import com.kirill.kochnev.homewardrope.ui.adapters.WardrobePagerAdapter;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.03.17.
 */

public class PutWardrobeActivity extends MvpAppCompatActivity implements IPutWardrobeView {

    public static final String WARDROPE_ID = "wardrope_id";

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.wardrope_show_frame)
    FloatingActionButton select;

    @BindView(R.id.save_wardrope)
    FloatingActionButton save;

    @BindView(R.id.new_wardrope_name)
    EditText name;

    @BindView(R.id.things_count)
    TextView thingsCount;

    @BindView(R.id.looks_count)
    TextView looksCount;

    @InjectPresenter
    PutWardrobePresenter presenter;

    private ActivityToolbarDelegate activityToolbarDelegate = new ActivityToolbarDelegate();

    @ProvidePresenter
    PutWardrobePresenter providePresenter() {
        final long wardropeId = getIntent().getLongExtra(WARDROPE_ID, AppConstants.DEFAULT_ID);
        final PutWardrobeComponent component = WardrobeApplication.getComponentHolder().getAddUpdateWardrobeComponent(wardropeId);
        return component.providePresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long wardropeId = getIntent().getLongExtra(WARDROPE_ID, AppConstants.DEFAULT_ID);
        final View view = View.inflate(this, R.layout.activity_add_update_wardrobe, null);
        setContentView(view);
        ButterKnife.bind(this);
        activityToolbarDelegate.init(view, wardropeId == AppConstants.DEFAULT_ID ? getString(R.string.new_wardrobe_title) : "",
                v -> {
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                });
        initBtns(wardropeId);
        pager.setAdapter(new WardrobePagerAdapter(this, getSupportFragmentManager(), wardropeId));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WardrobeApplication.getComponentHolder().clearAddUpdateWardrobeComponent();
    }

    private void initBtns(long wardropeId) {
        select.setOnClickListener(v -> presenter.toggleMode());
        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
        select.setVisibility(wardropeId == AppConstants.DEFAULT_ID ? View.GONE : View.VISIBLE);
        save.setVisibility(wardropeId == AppConstants.DEFAULT_ID ? View.VISIBLE : View.GONE);
        name.setEnabled(wardropeId == AppConstants.DEFAULT_ID);
    }

    @Override
    public void changeBtnsMode(boolean mode) {
        AnimationHelper.hideShowAnimation(this, save, !mode);
        name.setEnabled(mode);
    }

    @Override
    public void setCount(int thingsCount, int looksCount) {
        this.thingsCount.setText(String.valueOf(thingsCount));
        this.looksCount.setText(String.valueOf(looksCount));
    }

    @Override
    public void initView(Wardrobe wardrobe) {
        setCount(wardrobe.getThingsCount(), wardrobe.getLookIds().size());
        name.setText(wardrobe.getName());
        activityToolbarDelegate.updateTitle(wardrobe.getName());
    }

    @Override
    public void onSave(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }
}
