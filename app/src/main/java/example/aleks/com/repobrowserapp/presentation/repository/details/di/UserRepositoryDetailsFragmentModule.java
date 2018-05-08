package example.aleks.com.repobrowserapp.presentation.repository.details.di;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import example.aleks.com.repobrowserapp.presentation.repository.details.IUserRepositoryDetailsView;
import example.aleks.com.repobrowserapp.presentation.repository.details.UserRepositoryDetailsFragment;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.IUserRepositoryDetailsPresenter;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.UserRepositoryDetailsPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

@Module
public abstract class UserRepositoryDetailsFragmentModule {

    @ContributesAndroidInjector
    public abstract UserRepositoryDetailsFragment bindsUserRepositoryDetailsFragment();

    @Binds
    public abstract IUserRepositoryDetailsView bindsUserRepositoryDetailsView(UserRepositoryDetailsFragment detailsFragment);

    @Binds
    public abstract IUserRepositoryDetailsPresenter bindsRepositoryDetailsPresenter(UserRepositoryDetailsPresenter repositoryDetailsPresenter);
}
