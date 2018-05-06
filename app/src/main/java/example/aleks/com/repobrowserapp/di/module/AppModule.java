package example.aleks.com.repobrowserapp.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.AuthenticateInteractor;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.ILocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.LocalStorageRepository;
import example.aleks.com.repobrowserapp.presentation.model.ViewModelFactory;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public abstract class AppModule {

    @Binds
    public abstract IAuthenticateInteractor bindsAuthenticateInteractor(AuthenticateInteractor authenticateInteractor);

    @Binds
    public abstract ILocalStorageRepository bindsLocalStorageRepository(LocalStorageRepository localStorageRepository);

    @Binds
    public abstract ViewModelProvider.Factory bindsViewModelProviderFactory(ViewModelFactory viewModelFactory);
}
