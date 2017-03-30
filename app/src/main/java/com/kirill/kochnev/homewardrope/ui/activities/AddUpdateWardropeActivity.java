package com.kirill.kochnev.homewardrope.ui.activities;

import android.os.Bundle;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateWardropeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.03.17.
 */

public class AddUpdateWardropeActivity extends MvpAppCompatActivity implements IAddUpdateWardropeView{

    @BindView(R.id.wardrope_save_btn)
    Button save;

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

    }

}
