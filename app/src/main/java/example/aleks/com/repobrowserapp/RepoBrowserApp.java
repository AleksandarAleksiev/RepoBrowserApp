package example.aleks.com.repobrowserapp;

import android.app.Activity;
import android.app.Application;

import java.io.File;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import example.aleks.com.repobrowserapp.di.component.DaggerAppComponent;
import example.aleks.com.repobrowserapp.di.module.NetworkModule;

/**
 * Created by aleks on 05/05/2018.
 */

public class RepoBrowserApp extends Application implements HasActivityInjector {

    @Inject
    public DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .network(new NetworkModule(new File(getCacheDir(), "responses")))
                .build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
