package com.yuyakaido.android.genesis.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yuyakaido.android.genesis.BuildConfig;
import com.yuyakaido.android.genesis.infra.client.common.HttpClient;
import com.yuyakaido.android.genesis.presentation.fragment.BaseFragment;

import java.io.InputStream;

import jp.wasabeef.takt.Takt;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class Genesis extends Application {

    protected GenesisComponent genesisComponent;
    protected RefWatcher refWatcher;

    public static GenesisComponent getGenesisComponent(BaseFragment fragment) {
        return Genesis.getGenesisComponent(fragment.getContext().getApplicationContext());
    }

    public static GenesisComponent getGenesisComponent(Context context) {
        Genesis genesis = (Genesis) context.getApplicationContext();
        return genesis.genesisComponent;
    }

    public static RefWatcher getRefWatcher(Context context) {
        Genesis genesis = (Genesis) context.getApplicationContext();
        return genesis.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
        initializeTakt();
        initializeLeakCanary();
        initializeStetho();
        initializeStrictMode();
    }

    @Override
    public void onTerminate() {
        terminateTalk();
        super.onTerminate();
    }

    protected void initializeDagger() {
        genesisComponent = DaggerGenesisComponent.builder()
                .genesisModule(new GenesisModule(this))
                .build();
    }

    protected void initializeTakt() {
        if (BuildConfig.DEBUG) {
            Takt.stock(this).play();
        }
    }

    protected void initializeLeakCanary() {
        refWatcher = LeakCanary.install(this);
    }

    protected void initializeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(HttpClient.getInstance()));
    }

    protected void initializeStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }

    protected void terminateTalk() {
        if (BuildConfig.DEBUG) {
            Takt.finish();
        }
    }

}
