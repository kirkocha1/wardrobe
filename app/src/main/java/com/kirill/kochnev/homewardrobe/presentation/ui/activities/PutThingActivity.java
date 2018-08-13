package com.kirill.kochnev.homewardrobe.presentation.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.WardrobeApplication;
import com.kirill.kochnev.homewardrobe.db.models.Thing;
import com.kirill.kochnev.homewardrobe.di.components.PutThingComponent;
import com.kirill.kochnev.homewardrobe.presentation.presenters.thing.PutThingPresenter;
import com.kirill.kochnev.homewardrobe.presentation.ui.activities.base.PermissonDelegate;
import com.kirill.kochnev.homewardrobe.presentation.views.IPutThingView;
import com.kirill.kochnev.homewardrobe.presentation.ui.activities.base.ActivityToolbarDelegate;
import com.kirill.kochnev.homewardrobe.utils.AnimationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrobe.presentation.presenters.thing.ThingsPresenter.THINGS_ID;

public class PutThingActivity extends MvpAppCompatActivity implements IPutThingView {

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
    PutThingPresenter presenter;

    @ProvidePresenter
    PutThingPresenter providePresenter() {
        final PutThingComponent component = WardrobeApplication.getComponentHolder()
                .getAddUpdateThingComponent(getIntent().getLongExtra(THINGS_ID, AppConstants.DEFAULT_ID));
        return component.providePresenter();
    }

    @NonNull private final PermissonDelegate permissonDelegate = new PermissonDelegate(this);
    @NonNull private final ActivityToolbarDelegate activityToolbarDelegate = new ActivityToolbarDelegate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean isEditMode = getIntent().getBooleanExtra(IS_EDIT, true);
        final long thingId = getIntent().getLongExtra(THINGS_ID, -1);
        final boolean isNew = thingId == AppConstants.DEFAULT_ID;
        final View view = View.inflate(this, R.layout.activity_add_or_update_thing, null);
        setContentView(view);
        ButterKnife.bind(this);
        activityToolbarDelegate.init(view, thingId == -1 ? getString(R.string.new_thing_title) : "",
                v -> {
                    setResult(RESULT_CANCELED);
                    onBackPressed();
                });
        initBtns(isNew, isEditMode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WardrobeApplication.getComponentHolder().clearAddUpdateThingComponent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissonDelegate.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                permisson -> {
                    switch (permisson) {
                        case Manifest.permission.CAMERA:
                            presenter.createUri();
                            break;
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            presenter.saveThing(name.getText().toString(), tag.getText().toString());
                            break;
                    }
                },
                permission -> {
                }
        );
    }

    private void initBtns(final boolean isNew, final boolean isEditMode) {
        edit.setVisibility(isNew ? View.GONE : View.VISIBLE);
        if (isEditMode) {
            changeMode(false);
        }
        edit.setOnClickListener(v -> {
            changeMode(true);
        });
        captureBtn.setOnClickListener(v -> permissonDelegate.askPermisson(Manifest.permission.CAMERA));
        save.setOnClickListener(v -> permissonDelegate.askPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private void changeMode(boolean isAnimate) {
        final boolean isEditMode = !name.isEnabled();
        name.setEnabled(isEditMode);
        tag.setEnabled(isEditMode);
        captureBtn.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        save.setVisibility(isEditMode ? View.VISIBLE : View.INVISIBLE);
        if (isAnimate) {
            AnimationHelper.hideShowAnimation(this, save, !isEditMode);
            AnimationHelper.hideShowAnimation(this, captureBtn, !isEditMode);
        }
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
