package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.ui.adapters.WardropePagerAdapter;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.03.17.
 */

public class AddUpdateWardropeActivity extends BaseActionBarActivity implements IAddUpdateWardropeView {

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
    TextView countView;

    @InjectPresenter
    AddUpdateWardropePresenter presenter;

    private long wardropeId;

    private ThingsFragment fragment;

    @ProvidePresenter
    AddUpdateWardropePresenter providePresenter() {
        return new AddUpdateWardropePresenter(wardropeId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wardropeId = getIntent().getLongExtra(WARDROPE_ID, -1);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitUi(View baseLayout) {
        setTitleText(wardropeId == -1 ? "новый гардероб" : "");
        setBackButtonEnabled(true);
        setContentView(View.inflate(this, R.layout.activity_add_update_wardrope, null));
        ButterKnife.bind(this, baseLayout);
        initBtns();
        pager.setAdapter(new WardropePagerAdapter(getSupportFragmentManager(), wardropeId));
    }

    private void initBtns() {
        select.setOnClickListener(v -> presenter.toggleMode());
        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
        select.setVisibility(wardropeId == -1 ? View.GONE : View.VISIBLE);
        save.setVisibility(wardropeId == -1 ? View.VISIBLE : View.GONE);
        name.setEnabled(wardropeId == -1);
    }

    @Override
    public boolean isMenuActive() {
        return false;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }

    @Override
    public void changeBtnsMode(boolean mode) {
        save.setVisibility(mode ? View.VISIBLE : View.GONE);
        AnimationHelper.hideShowAnimation(save, !mode);
        name.setEnabled(mode);
    }

    @Override
    public void setCount(int count) {
        countView.setText("" + count);
    }

    @Override
    public void initView(Wardrope wardrope) {
        setTitleText(wardrope.getName() == null ? "нет названия" : wardrope.getName());
        countView.setText(wardrope.getThingIds().size() + "");
        name.setText(wardrope.getName());
    }

    @Override
    public void onSave() {
        finish();
    }
}
