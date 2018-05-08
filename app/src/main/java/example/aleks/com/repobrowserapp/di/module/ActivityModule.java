package example.aleks.com.repobrowserapp.di.module;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import example.aleks.com.repobrowserapp.di.annotation.ActivityScope;
import example.aleks.com.repobrowserapp.di.annotation.ViewModelKey;
import example.aleks.com.repobrowserapp.presentation.main.IMainNavigator;
import example.aleks.com.repobrowserapp.presentation.main.MainActivity;
import example.aleks.com.repobrowserapp.presentation.main.MainViewModel;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class ActivityModule {

    @Binds
    @ActivityScope
    public abstract IMainNavigator bindsMainNavigator(MainActivity mainActivity);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindsMainViewModel(MainViewModel mainViewModel);
}
