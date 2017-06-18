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
import com.kirill.kochnev.homewardrope.mvp.presenters.look.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.utils.ImageHelper.makeImage;

/**
 * Created by kirill on 03.05.17.
 */

public class UpdateLookActivity extends BaseActionBarActivity implements IUpdateLook {

    public static final String LOOK_ID = "look_id";
    public static final int UPDATE_LOOK_CODE = 2;

    @BindView(R.id.looks_show_frame)
    FloatingActionButton select;

    @BindView(R.id.new_name)
    EditText name;

    @BindView(R.id.new_tag)
    EditText tag;

    @BindView(R.id.look_shoot_btn)
    FloatingActionButton captureBtn;

    @BindView(R.id.look_save_btn)
    FloatingActionButton save;

    @BindView(R.id.photo_look)
    ImageView pic;

    @InjectPresenter
    UpdateLookPresenter presenter;

    @ProvidePresenter
    UpdateLookPresenter providePresenter() {
        return new UpdateLookPresenter(getIntent().getLongExtra(LOOK_ID, AppConstants.DEFAULT_ID));
    }

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
        select.setOnClickListener(v -> {
            boolean isVisible = save.getVisibility() != View.INVISIBLE;
            changeBtnStatus(isVisible);
        });
        save.setOnClickListener(v -> {
            presenter.saveLook(name.getText().toString(), tag.getText().toString());
        });
        captureBtn.setOnClickListener(v -> {
            startActivityForResult(CreationLookActivity.createIntent(getIntent().getLongExtra(LOOK_ID, -1), -1), UPDATE_LOOK_CODE);
        });
    }

    private void changeBtnStatus(boolean isVisible) {
        AnimationHelper.hideShowAnimation(captureBtn, isVisible);
        AnimationHelper.hideShowAnimation(save, isVisible);
        name.setEnabled(!isVisible);
        tag.setEnabled(!isVisible);
    }

    @Override
    public void onSave(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
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
        setTitleText(look.getName());
        name.setText(look.getName());
        tag.setText(look.getTag());
        pic.setImageBitmap(makeImage(look.getFullImagePath()));
    }
}
