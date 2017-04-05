package com.kirill.kochnev.homewardrope.di;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.kirill.kochnev.homewardrope.db.WardropeSqlHelper;
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
                .addTypeMapping(ThingsWardropes.class, SQLiteTypeMapping.<ThingsWardropes>builder()
                        .putResolver(new ThingsWardropesStorIOSQLitePutResolver())
                        .getResolver(new ThingsWardropesStorIOSQLiteGetResolver())
                        .deleteResolver(new ThingsWardropesStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }
}
