package example.aleks.com.repobrowserapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.aleks.com.repobrowserapp.di.annotation.ActivityScope;
import example.aleks.com.repobrowserapp.presentation.main.MainActivity;
import example.aleks.com.repobrowserapp.presentation.main.di.MainActivityModule;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class BuildModule {

    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    @ActivityScope
    public abstract MainActivity bindsMainActivity();
}
