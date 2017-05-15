package com.kirill.kochnev.homewardrope.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.utils.interfaces.ILoader;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Kirill Kochnev on 14.05.17.
 */

public class ImageLoader implements ILoader {

    private Picasso picasso;

    public ImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Uri uriToFile = url != null ? Uri.fromFile(new File(url)) : null;
        picasso.load(uriToFile).placeholder(R.drawable.image_placeholder).into(imageView);
    }
}
