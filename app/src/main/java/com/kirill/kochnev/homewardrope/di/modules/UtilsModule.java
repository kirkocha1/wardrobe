package com.kirill.kochnev.homewardrope.di.modules;

import android.content.Context;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.utils.ImageLoader;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.RxBus;
import com.kirill.kochnev.homewardrope.utils.interfaces.ILoader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 28.04.17.
 */

@Module
public class UtilsModule {

    @Singleton
    @Provides
    public IdBus provideRxBus() {
        return new RxBus();
    }

    @Singleton
    @Provides
    public Picasso providePicasso(Context context) {
        return new Picasso.Builder(context)
                .memoryCache(new LruCache(15 * AppConstants.BYTES_MB_COEFF))
                .build();
    }

    @Singleton
    @Provides
    public ILoader provideImageLoader(Picasso picasso) {
        return new ImageLoader(picasso);
    }
}
