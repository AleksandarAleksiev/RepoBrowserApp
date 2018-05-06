package example.aleks.com.repobrowserapp.di.module;

import dagger.Binds;
import dagger.Module;
import example.aleks.com.repobrowserapp.presentation.main.MainActivity;
import example.aleks.com.repobrowserapp.presentation.main.IMainPresenter;
import example.aleks.com.repobrowserapp.presentation.main.IMainView;
import example.aleks.com.repobrowserapp.presentation.main.MainPresenter;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class ActivityModule {

    @Binds
    public abstract IMainPresenter bindMainPresenter(MainPresenter mainPresenter);

    @Binds
    public abstract IMainView bindsMainView(MainActivity mainActivity);
}
