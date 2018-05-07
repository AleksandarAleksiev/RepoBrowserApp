package example.aleks.com.repobrowserapp.presentation.repository.details.di;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import example.aleks.com.repobrowserapp.di.annotation.ViewModelKey;
import example.aleks.com.repobrowserapp.presentation.repository.details.IUserRepositoryDetailsView;
import example.aleks.com.repobrowserapp.presentation.repository.details.UserRepositoryDetailsFragment;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.IUserRepositoryDetailsPresenter;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.UserRepositoryDetailsPresenter;
import example.aleks.com.repobrowserapp.presentation.repository.details.model.UserRepositoryDetailsViewModel;

/**
 * Created by aleks on 07/05/2018.
 */

@Module
public abstract class UserRepositoryDetailsModule {

    @ContributesAndroidInjector
    public abstract UserRepositoryDetailsFragment bindUserUserRepositoryDetailsFragment();

    @Binds
    public abstract IUserRepositoryDetailsPresenter bindsRepositoryDetailsPresenter(UserRepositoryDetailsPresenter repositoryDetailsPresenter);

    @Binds
    public abstract IUserRepositoryDetailsView bindsRepositoryDetailsView(UserRepositoryDetailsFragment userRepositoryDetailsFragment);

    @Binds
    @IntoMap
    @ViewModelKey(UserRepositoryDetailsViewModel.class)
    public abstract ViewModel bindsRepositoryDetailsViewModel(UserRepositoryDetailsViewModel userRepositoryDetailsViewModel);
}
