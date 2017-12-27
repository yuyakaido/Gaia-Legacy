package com.yuyakaido.android.blueprint.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.yuyakaido.android.blueprint.BuildConfig;
import com.yuyakaido.android.blueprint.di.component.AppComponent;
import com.yuyakaido.android.blueprint.di.component.DaggerAppComponent;
import com.yuyakaido.android.blueprint.di.module.AppModule;
import com.yuyakaido.android.blueprint.infra.client.common.HttpClient;
import com.yuyakaido.android.blueprint.presentation.fragment.BaseFragment;

import java.io.InputStream;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class App extends Application {

    protected AppComponent appComponent;
    protected RefWatcher refWatcher;

    public static AppComponent getAppComponent(BaseFragment fragment) {
        return App.getAppComponent(fragment.getContext().getApplicationContext());
    }

    public static AppComponent getAppComponent(Context context) {
        App app = (App) context.getApplicationContext();
        return app.appComponent;
    }

    public static RefWatcher getRefWatcher(Context context) {
        App app = (App) context.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDagger();
        initializeLeakCanary();
        initializeStetho();
        initializeStrictMode();
    }

    protected void initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
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

}
