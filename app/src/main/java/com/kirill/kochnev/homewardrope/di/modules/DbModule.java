package com.kirill.kochnev.homewardrope.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.kirill.kochnev.homewardrope.db.WardrobeSqlHelper;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LookStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.LookStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.LookStorIOSQLitePutResolver;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.db.models.LooksThingsStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.LooksThingsStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.LooksThingsStorIOSQLitePutResolver;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingStorIOSQLitePutResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardrobes;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardrobesStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardrobesStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardrobesStorIOSQLitePutResolver;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.db.models.WardrobeStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.WardrobeStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.WardrobeStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 03.04.17.
 */

@Module
public class DbModule {

    private Context context;

    public DbModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    SQLiteOpenHelper provideDbHelper() {
        return new WardrobeSqlHelper(context);
    }

    @Singleton
    @Provides
    StorIOSQLite provideStorioSql(SQLiteOpenHelper helper) {
        return DefaultStorIOSQLite.builder().sqliteOpenHelper(helper)
                .addTypeMapping(Wardrobe.class, SQLiteTypeMapping.<Wardrobe>builder()
                        .putResolver(new WardrobeStorIOSQLitePutResolver())
                        .getResolver(new WardrobeStorIOSQLiteGetResolver())
                        .deleteResolver(new WardrobeStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Thing.class, SQLiteTypeMapping.<Thing>builder()
                        .putResolver(new ThingStorIOSQLitePutResolver())
                        .getResolver(new ThingStorIOSQLiteGetResolver())
                        .deleteResolver(new ThingStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(Look.class, SQLiteTypeMapping.<Look>builder()
                        .putResolver(new LookStorIOSQLitePutResolver())
                        .getResolver(new LookStorIOSQLiteGetResolver())
                        .deleteResolver(new LookStorIOSQLiteDeleteResolver())
                        .build())
                .addTypeMapping(ThingsWardrobes.class, SQLiteTypeMapping.<ThingsWardrobes>builder()
                        .putResolver(new ThingsWardrobesStorIOSQLitePutResolver())
                        .getResolver(new ThingsWardrobesStorIOSQLiteGetResolver())
                        .deleteResolver(new ThingsWardrobesStorIOSQLiteDeleteResolver()).build())

                .addTypeMapping(LooksThings.class, SQLiteTypeMapping.<LooksThings>builder()
                        .putResolver(new LooksThingsStorIOSQLitePutResolver())
                        .getResolver(new LooksThingsStorIOSQLiteGetResolver())
                        .deleteResolver(new LooksThingsStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }
}
