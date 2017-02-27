package com.kirill.kochnev.homewardrope.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateThingView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddOrUpdateThingActivity extends MvpAppCompatActivity implements IAddUpdateThingView {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 2;
    @BindView(R.id.new_thing_name)
    EditText name;

    @BindView(R.id.shoot_btn)
    Button captureBtn;

    @BindView(R.id.photo_thing)
    ImageView pic;

    @BindView(R.id.thing_save_btn)
    Button save;

    @InjectPresenter
    AddUpdateThingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_update_thing);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        captureBtn.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        save.setOnClickListener(v -> {
            presenter.saveThing(name.getText().toString());
        });
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchTakeAndSaveFullPictureIntent() {
        presenter.createUri();
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
            presenter.processCropImage((Bitmap) data.getExtras().get("data"));
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            presenter.processImage();
        }
    }


}
