package example.aleks.com.repobrowserapp.presentation.user.repoistories.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.IUserRepositoriesView;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.UserRepositoriesFragment;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.IUserRepositoriesPresenter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.UserRepositoriesPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

@Module
public abstract class UserRepositoriesFragmentModule {

    @ContributesAndroidInjector
    public abstract UserRepositoriesFragment bindUserRepositoriesFragment();

    @Binds
    public abstract IUserRepositoriesView bindsUserRepositoriesView(UserRepositoriesFragment userRepositoriesFragment);

    @Binds
    public abstract IUserRepositoriesPresenter bindUserRepositoriesPresenter(UserRepositoriesPresenter userRepositoriesPresenter);
}
