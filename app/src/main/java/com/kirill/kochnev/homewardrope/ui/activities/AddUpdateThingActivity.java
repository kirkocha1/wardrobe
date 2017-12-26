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

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;
import com.kirill.kochnev.homewardrope.ui.activities.base.ActivityToolbarDelegate;
import com.kirill.kochnev.homewardrope.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter.THINGS_ID;

public class AddUpdateThingActivity extends MvpAppCompatActivity implements IAddUpdateThingView {

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

    @ProvidePresenter
    AddUpdateThingPresenter providePresenter() {
        return new AddUpdateThingPresenter(getIntent().getLongExtra(THINGS_ID, -1));
    }

    private boolean isEditMode;
    private ActivityToolbarDelegate activityToolbarDelegate = new ActivityToolbarDelegate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEditMode = getIntent().getBooleanExtra(IS_EDIT, true);
        long thingId = getIntent().getLongExtra(THINGS_ID, -1);
        boolean isNew = thingId == AppConstants.DEFAULT_ID;
        final View view = View.inflate(this, R.layout.activity_add_or_update_thing, null);
        setContentView(view);
        ButterKnife.bind(this);
        activityToolbarDelegate.init(view, thingId == -1 ? getString(R.string.new_thing_title) : "",
                v -> {
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                });
        initBtns(isNew);
    }

    private void initBtns(boolean isNew) {
        edit.setVisibility(isNew ? View.GONE : View.VISIBLE);
        changeMode(isNew, false);
        edit.setOnClickListener(v -> {
            isEditMode = !isEditMode;
            changeMode(isEditMode);
        });
        captureBtn.setOnClickListener(v -> presenter.createUri());
        save.setOnClickListener(v -> presenter.saveThing(name.getText().toString(), tag.getText().toString()));
    }

    private void changeMode(boolean isEditMode, boolean isAnimate) {
        name.setEnabled(isEditMode);
        tag.setEnabled(isEditMode);
        captureBtn.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        save.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        if (isAnimate) {
            AnimationHelper.hideShowAnimation(this, save, !isEditMode);
            AnimationHelper.hideShowAnimation(this, captureBtn, !isEditMode);
        }
    }

    private void changeMode(boolean isEditMode) {
        changeMode(isEditMode, true);
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
    public void showThing(Thing thing) {
        activityToolbarDelegate.updateTitle(thing.getName() == null ? getString(R.string.no_name) : thing.getName());
        pic.setImageBitmap(thing.getBitmap());
        this.tag.setText(thing.getTag());
        this.name.setText(thing.getName());
    }
}
