package com.kirill.kochnev.homewardrope.di;

import android.content.Context;

import com.kirill.kochnev.homewardrope.db.WardropeSqlHelper;
import com.kirill.kochnev.homewardrope.db.models.DaoMaster;
import com.kirill.kochnev.homewardrope.db.models.DaoSession;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.models.WardropeDao;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Module
public class GreenDaoModule {

    private Context context;

    public GreenDaoModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession() {
        DaoMaster.DevOpenHelper helper = new WardropeSqlHelper(context, "wardrope.db");
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }

    @Provides
    @Singleton
    ThingDao provideThingsDao(DaoSession session) {
        return session.getThingDao();
    }

    @Provides
    @Singleton
    WardropeDao provideWardropesDao(DaoSession session) {
        return session.getWardropeDao();
    }
}
