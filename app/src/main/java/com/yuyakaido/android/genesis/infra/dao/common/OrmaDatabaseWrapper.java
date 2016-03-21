package com.yuyakaido.android.genesis.infra.dao.common;

import android.content.Context;

import com.github.gfx.android.orma.AccessThreadConstraint;
import com.yuyakaido.android.genesis.domain.entity.OrmaDatabase;
import com.yuyakaido.android.genesis.infra.constant.InfraConst;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class OrmaDatabaseWrapper {

    private OrmaDatabase database;

    public OrmaDatabaseWrapper(Context context) {
        database = OrmaDatabase.builder(context.getApplicationContext())
                .name(InfraConst.Database.DB_NAME)
                .readOnMainThread(AccessThreadConstraint.FATAL)
                .writeOnMainThread(AccessThreadConstraint.FATAL)
                .build();
    }

    public OrmaDatabase getDatabase() {
        return database;
    }

}
