package com.kirill.kochnev.homewardrope.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.kirill.kochnev.homewardrope.db.WardropeSqlHelper;
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
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropesStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropesStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropesStorIOSQLitePutResolver;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.models.WardropeStorIOSQLiteDeleteResolver;
import com.kirill.kochnev.homewardrope.db.models.WardropeStorIOSQLiteGetResolver;
import com.kirill.kochnev.homewardrope.db.models.WardropeStorIOSQLitePutResolver;
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

    @Singleton
    @Provides
    SQLiteOpenHelper provideDbHelper() {
        return new WardropeSqlHelper(context);
    }

    @Singleton
    @Provides
    StorIOSQLite provideStorioSql(SQLiteOpenHelper helper) {
        return DefaultStorIOSQLite.builder().sqliteOpenHelper(helper)
                .addTypeMapping(Wardrope.class, SQLiteTypeMapping.<Wardrope>builder()
                        .putResolver(new WardropeStorIOSQLitePutResolver())
                        .getResolver(new WardropeStorIOSQLiteGetResolver())
                        .deleteResolver(new WardropeStorIOSQLiteDeleteResolver())
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
                .addTypeMapping(ThingsWardropes.class, SQLiteTypeMapping.<ThingsWardropes>builder()
                        .putResolver(new ThingsWardropesStorIOSQLitePutResolver())
                        .getResolver(new ThingsWardropesStorIOSQLiteGetResolver())
                        .deleteResolver(new ThingsWardropesStorIOSQLiteDeleteResolver()).build())

                .addTypeMapping(LooksThings.class, SQLiteTypeMapping.<LooksThings>builder()
                        .putResolver(new LooksThingsStorIOSQLitePutResolver())
                        .getResolver(new LooksThingsStorIOSQLiteGetResolver())
                        .deleteResolver(new LooksThingsStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }
}
