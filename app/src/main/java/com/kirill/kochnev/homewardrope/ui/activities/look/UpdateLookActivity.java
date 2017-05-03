package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.utils.ImageHelper.makeImage;

/**
 * Created by kirill on 03.05.17.
 */

public class UpdateLookActivity extends BaseActionBarActivity implements IUpdateLook {

    public static final String LOOK_ID = "look_id";

    @BindView(R.id.looks_show_frame)
    FloatingActionButton select;

    @BindView(R.id.new_name)
    EditText name;

    @BindView(R.id.new_tag)
    EditText tag;

    @BindView(R.id.photo_look)
    ImageView pic;

    @InjectPresenter
    UpdateLookPresenter presenter;

    @ProvidePresenter
    UpdateLookPresenter providePresenter() {
        return new UpdateLookPresenter(getIntent().getLongExtra(LOOK_ID, AppConstants.DEFAULT_ID));
    }

    public static final String WARDROPE_ID = "wardrope_id";


    public static Intent createIntent(long lookId) {
        Intent intent = new Intent(WardropeApplication.getContext(), UpdateLookActivity.class);
        intent.putExtra(LOOK_ID, lookId);
        return intent;
    }

    @Override
    public void onInitUi(View baseLayout) {
        setBackButtonEnabled(true);
        setContentView(View.inflate(this, R.layout.activity_update_look, null));
        ButterKnife.bind(this, baseLayout);
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
    public void setLookData(Look look) {
        name.setText(look.getName());
        tag.setText(look.getTag());
        pic.setImageBitmap(makeImage(look.getFullImagePath()));
    }
}
