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
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateThingView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter.THINGS_ID;

public class AddUpdateThingActivity extends BaseActionBarActivity implements IAddUpdateThingView {

    private static final int REQUEST_TAKE_PHOTO = 2;

    @BindView(R.id.new_thing_name)
    EditText name;
    @BindView(R.id.new_thing_tag)
    EditText tag;
    @BindView(R.id.shoot_btn)
    Button captureBtn;

    @BindView(R.id.photo_thing)
    ImageView pic;

    @BindView(R.id.thing_save_btn)
    Button save;

    @InjectPresenter
    AddUpdateThingPresenter presenter;

    @ProvidePresenter
    AddUpdateThingPresenter providePresenter() {
        return new AddUpdateThingPresenter(thingsId);
    }

    private long thingsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thingsId = getIntent().getLongExtra(THINGS_ID, -1);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInitUi(View baseLayout) {
        setBackButtonEnabled(true);
        setTitleText(thingsId == -1 ? "новая вещь" : "");
        setContentView(View.inflate(this, R.layout.activity_add_or_update_thing, null));
        ButterKnife.bind(this, baseLayout);
        captureBtn.setOnClickListener(v -> presenter.createUri());
        save.setOnClickListener(v -> presenter.saveThing(name.getText().toString(), tag.getText().toString()));
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
    public void onSave() {
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
