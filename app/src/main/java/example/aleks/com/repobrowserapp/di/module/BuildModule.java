package example.aleks.com.repobrowserapp.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.aleks.com.repobrowserapp.presentation.main.MainActivity;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class BuildModule {

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    public abstract MainActivity bindsMainActivity();
}
