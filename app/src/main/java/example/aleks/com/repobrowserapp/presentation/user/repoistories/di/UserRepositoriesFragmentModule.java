package example.aleks.com.repobrowserapp.presentation.user.repoistories.di;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import example.aleks.com.repobrowserapp.di.annotation.ViewModelKey;
import example.aleks.com.repobrowserapp.domain.interactor.user.repos.IUserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.interactor.user.repos.UserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.repository.user.git.IUserGitReposRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.UserGitReposRepository;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.IUserRepositoriesView;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.UserRepositoriesFragment;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.UserRepositoriesViewModel;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.IUserRepositoriesPresenter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.UserRepositoriesPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

@Module
public abstract class UserRepositoriesFragmentModule {

    @ContributesAndroidInjector(modules = {UserRepositoriesProviderModule.class})
    public abstract UserRepositoriesFragment bindUserRepositoriesFragment();

    @Binds
    public abstract IUserGitReposRepository bindsUserGitReposRepository(UserGitReposRepository userGitReposRepository);

    @Binds
    public abstract IUserRepositoriesView bindsUserRepositoriesView(UserRepositoriesFragment userRepositoriesFragment);

    @Binds
    public abstract IUserRepositoriesPresenter bindUserRepositoriesPresenter(UserRepositoriesPresenter userRepositoriesPresenter);

    @Binds
    public abstract IUserRepositoriesInteractor bindUserRepositoriesInteractor(UserRepositoriesInteractor userRepositoriesInteractor);

    @Binds
    @IntoMap
    @ViewModelKey(UserRepositoriesViewModel.class)
    public abstract ViewModel bindUserRepositoriesViewModel(UserRepositoriesViewModel userRepositoriesViewModel);
}
