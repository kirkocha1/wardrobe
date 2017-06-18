package com.kirill.kochnev.homewardrope.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter.THINGS_ID;

public class AddUpdateThingActivity extends BaseActionBarActivity implements IAddUpdateThingView {

    private static final int REQUEST_TAKE_PHOTO = 2;
    public static final String IS_EDIT = "is_edit";


    @BindView(R.id.new_name)
    EditText name;

    @BindView(R.id.new_tag)
    EditText tag;

    @BindView(R.id.shoot_btn)
    FloatingActionButton captureBtn;

    @BindView(R.id.photo_thing)
    ImageView pic;

    @BindView(R.id.thing_save_btn)
    FloatingActionButton save;

    @BindView(R.id.things_show_frame)
    FloatingActionButton edit;

    @InjectPresenter
    AddUpdateThingPresenter presenter;

    public static Intent createIntent(long thingsId, boolean isEditMode) {
        Intent intent = new Intent(WardropeApplication.getContext(), AddUpdateThingActivity.class);
        intent.putExtra(THINGS_ID, thingsId);
        intent.putExtra(IS_EDIT, isEditMode);
        return intent;
    }


    @ProvidePresenter
    AddUpdateThingPresenter providePresenter() {
        return new AddUpdateThingPresenter(thingsId);
    }

    private long thingsId;
    private boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thingsId = getIntent().getLongExtra(THINGS_ID, -1);
        isEditMode = getIntent().getBooleanExtra(IS_EDIT, true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitUi(View baseLayout) {
        setBackButtonEnabled(true);
        setTitleText(thingsId == -1 ? "новая вещь" : "");
        setContentView(View.inflate(this, R.layout.activity_add_or_update_thing, null));
        ButterKnife.bind(this, baseLayout);
        initBtns();
    }

    private void initBtns() {
        changeMode(isEditMode || thingsId == AppConstants.DEFAULT_ID);
        edit.setVisibility(thingsId == AppConstants.DEFAULT_ID ? View.GONE : View.VISIBLE);
        edit.setOnClickListener(v -> {
            isEditMode = !isEditMode;
            changeMode(isEditMode);
        });
        captureBtn.setOnClickListener(v -> presenter.createUri());
        save.setOnClickListener(v -> presenter.saveThing(name.getText().toString(), tag.getText().toString()));
    }

    private void changeMode(boolean isEditMode) {
        name.setEnabled(isEditMode);
        tag.setEnabled(isEditMode);
        captureBtn.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        save.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        AnimationHelper.hideShowAnimation(save, !isEditMode);
        AnimationHelper.hideShowAnimation(captureBtn, !isEditMode);
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
    public void setImageUri(Uri uri) {
        pic.setImageURI(uri);
    }

    @Override
    public void onSave(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setImage(Bitmap bitmap) {
        pic.setImageBitmap(bitmap);
        pic.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendMakePhotoIntent(Uri uri) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            presenter.processImage();
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void updateView(String name, String tag, Bitmap image) {
        setTitleText(name == null ? "без имени" : name);
        pic.setImageBitmap(image);
        this.tag.setText(tag);
        this.name.setText(name);
    }
}
