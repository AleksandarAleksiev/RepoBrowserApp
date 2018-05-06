package example.aleks.com.repobrowserapp.presentation.main.di;

import dagger.Binds;
import dagger.Module;
import example.aleks.com.repobrowserapp.di.annotation.ActivityScope;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.AuthenticateInteractor;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.presentation.main.IMainNavigator;
import example.aleks.com.repobrowserapp.presentation.main.MainActivity;
import example.aleks.com.repobrowserapp.presentation.main.IMainPresenter;
import example.aleks.com.repobrowserapp.presentation.main.IMainView;
import example.aleks.com.repobrowserapp.presentation.main.MainPresenter;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class MainActivityModule {

    @Binds
    public abstract IMainPresenter bindMainPresenter(MainPresenter mainPresenter);

    @Binds
    public abstract IMainView bindsMainView(MainActivity mainActivity);

    @Binds
    @ActivityScope
    public abstract IMainNavigator bindsMainNavigator(MainActivity mainActivity);
}
