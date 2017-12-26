package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;
import com.kirill.kochnev.homewardrope.ui.activities.base.ActivityToolbarDelegate;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 03.05.17.
 */

public class UpdateLookActivity extends MvpAppCompatActivity implements IUpdateLook {

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

    public static Intent createIntent(Context context, long lookId) {
        Intent intent = new Intent(context, UpdateLookActivity.class);
        intent.putExtra(LOOK_ID, lookId);
        return intent;
    }

    private ActivityToolbarDelegate activityToolbarDelegate = new ActivityToolbarDelegate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_update_look, null);
        setContentView(view);
        ButterKnife.bind(this);
        activityToolbarDelegate.init(view, "",
                v -> {
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                });
        name.setEnabled(false);
        tag.setEnabled(false);
        select.setOnClickListener(v -> {
            boolean isVisible = save.getVisibility() != View.INVISIBLE;
            changeBtnStatus(isVisible);
        });
        save.setOnClickListener(v -> {
            presenter.saveLook(name.getText().toString(), tag.getText().toString());
        });
        captureBtn.setOnClickListener(v -> {
            presenter.updateClick();
        });
    }

    @Override
    public void goToUpdateLookScreen(Look look) {
        startActivityForResult(CreationLookActivity.createIntent(this, look.getId(), look.getWardropeId() == null ?
                AppConstants.DEFAULT_ID : look.getWardropeId()), UPDATE_LOOK_CODE);
    }

    private void changeBtnStatus(boolean isVisible) {
        AnimationHelper.hideShowAnimation(this, captureBtn, isVisible);
        AnimationHelper.hideShowAnimation(this, save, isVisible);
        name.setEnabled(!isVisible);
        tag.setEnabled(!isVisible);
    }

    @Override
    public void onSave(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setLookData(Look look) {
        activityToolbarDelegate.updateTitle(look.getName());
        name.setText(look.getName());
        tag.setText(look.getTag());
        pic.setImageBitmap(look.getBitmap());
    }
}
