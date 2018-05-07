package example.aleks.com.repobrowserapp.presentation.user.repoistories.di;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import example.aleks.com.repobrowserapp.presentation.model.ViewModelFactory;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.UserRepositoriesFragment;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.adapter.UserRepositoriesAdapter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.UserRepositoriesViewModel;

/**
 * Created by aleks on 07/05/2018.
 */

@Module
public class UserRepositoriesProviderModule {

    @Provides
    UserRepositoriesAdapter providesUserRepositoriesAdapter(UserRepositoriesFragment userRepositoriesFragment,
                                                            ViewModelFactory viewModelFactory) {

        final UserRepositoriesViewModel userRepositoriesViewModel = ViewModelProviders.of(userRepositoriesFragment, viewModelFactory).get(UserRepositoriesViewModel.class);
        return new UserRepositoriesAdapter(userRepositoriesViewModel);
    }
}
