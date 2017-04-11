package com.kirill.kochnev.homewardrope.ui.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.03.17.
 */

public class AddUpdateWardropeActivity extends BaseActionBarActivity implements IAddUpdateWardropeView {

    public static final String WARDROPE_ID = "wardrope_id";

    @BindView(R.id.frame_id)
    FrameLayout frame;

    @BindView(R.id.wardrope_show_frame)
    Button select;

    @BindView(R.id.hide_frame)
    Button hide;

    @BindView(R.id.save_wardrope)
    Button save;

    @BindView(R.id.new_wardrope_name)
    EditText name;

    @BindView(R.id.things_count)
    TextView countView;

    @InjectPresenter
    AddUpdateWardropePresenter presenter;

    private long wardropeId;

    private Fragment fragment;

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
        select.setOnClickListener(v -> showFragment());
        hide.setOnClickListener(v -> setItemsVisibility(true));
        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
    }

    @Override
    public boolean isMenuActive() {
        return false;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }


    private void showFragment() {
        if (fragment == null) {
            fragment = ThingsFragment.createInstance(ThingsFragment.WARDROPE_MODE, wardropeId);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_id, fragment)
                .commit();
        setItemsVisibility(false);

    }

    private void setItemsVisibility(boolean isHide) {
        hide.setVisibility(isHide ? View.GONE : View.VISIBLE);
        frame.setVisibility(isHide ? View.GONE : View.VISIBLE);
        select.setVisibility(isHide ? View.VISIBLE : View.GONE);

    }

    @Override
    public void setCount(int count) {
        countView.setText("" + count);
    }


    @Override
    public void addThingId(long id) {
        presenter.addThingId(id);
    }

    @Override
    public void initView(Wardrope wardrope) {
        setTitleText(wardrope.getName() == null ? "новый гардероб" : wardrope.getName());
        countView.setText(wardrope.getThingIds().size() + "");
        name.setText(wardrope.getName());
    }

    @Override
    public void onSave() {
        finish();
    }
}
