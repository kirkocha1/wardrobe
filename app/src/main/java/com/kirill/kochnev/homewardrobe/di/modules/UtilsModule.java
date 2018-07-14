package com.kirill.kochnev.homewardrobe.di.modules;

import android.content.Context;

import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.utils.ImageProcessor;
import com.kirill.kochnev.homewardrobe.utils.ImageLoader;
import com.kirill.kochnev.homewardrobe.utils.bus.EditStateBus;
import com.kirill.kochnev.homewardrobe.utils.bus.EntityIdBus;
import com.kirill.kochnev.homewardrobe.utils.bus.IdBus;
import com.kirill.kochnev.homewardrobe.utils.bus.StateBus;
import com.kirill.kochnev.homewardrobe.utils.interfaces.ILoader;
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
    public IdBus provideIdBus() {
        return new EntityIdBus();
    }

    @Singleton
    @Provides
    public StateBus provideRxBus() {
        return new EditStateBus();
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
    public ImageProcessor provideImageHelper(Context context) {
        return new ImageProcessor(context);
    }

    @Singleton
    @Provides
    public ILoader provideImageLoader(Picasso picasso) {
        return new ImageLoader(picasso);
    }
}
