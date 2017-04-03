package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;
import com.kirill.kochnev.homewardrope.ui.fragments.ThingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.03.17.
 */

public class AddUpdateWardropeActivity extends MvpAppCompatActivity implements IAddUpdateWardropeView{

    @BindView(R.id.frame_id)
    FrameLayout frame;

    @BindView(R.id.wardrope_show_frame)
    Button select;

    @BindView(R.id.save_wardrope)
    Button save;

    @BindView(R.id.new_wardrope_name)
    EditText name;

    @BindView(R.id.things_count)
    TextView countView;

    @InjectPresenter
    AddUpdateWardropePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        thingsId = getIntent().getLongExtra(THINGS_ID, -1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_wardrope);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        select.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_id, ThingsFragment.createInstance(ThingsFragment.WARDROPE_MODE))
                    .commit();
        });

        save.setOnClickListener(v -> presenter.save(name.getText().toString()));
    }

    @Override
    public void setCount(int count) {
        countView.setText("" + count);
    }


    @Override
    public void addThingId(long id) {
        presenter.addThingId(id);
    }
}
