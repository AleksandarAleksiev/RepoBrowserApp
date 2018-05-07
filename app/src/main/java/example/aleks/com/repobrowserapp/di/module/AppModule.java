package example.aleks.com.repobrowserapp.di.module;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.aleks.com.repobrowserapp.RepoBrowserApp;
import example.aleks.com.repobrowserapp.di.annotation.AppContext;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.AuthenticateInteractor;
import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.domain.repository.authenticate.AuthenticateRepository;
import example.aleks.com.repobrowserapp.domain.repository.authenticate.IAuthenticateRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.ILocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.LocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.IUserGitReposRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.UserGitReposRepository;
import example.aleks.com.repobrowserapp.presentation.model.ViewModelFactory;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import example.aleks.com.repobrowserapp.utils.SchedulersProvider;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    @AppContext
    Context providesAppContext(RepoBrowserApp repoBrowserApp) {

        return repoBrowserApp;
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder().create();
    }

    @Provides
    IAuthenticateRepository providesAuthenticateRepository(@AppContext Context context) {

        final SharedPreferences sharedPreferences = context.getSharedPreferences("example.aleks.com.repobrowserapp.preferences", Context.MODE_PRIVATE);
        return new AuthenticateRepository(sharedPreferences);
    }

    @Provides
    IAuthenticateInteractor providesAuthenticateInteractor(AuthenticateInteractor authenticateInteractor) {

        return authenticateInteractor;
    }

    @Provides
    IUserGitReposRepository providesUserGitReposRepository(UserGitReposRepository userGitReposRepository) {

        return userGitReposRepository;
    }

    @Provides
    ILocalStorageRepository providesLocalStorageRepository(@AppContext Context context, Gson gson) {

        return new LocalStorageRepository(context.getCacheDir(), gson);
    }

    @Provides
    ViewModelProvider.Factory providesViewModelProviderFactory(ViewModelFactory viewModelFactory) {

        return viewModelFactory;
    }

    @Provides
    ISchedulersProvider providesSchedulersProvider(SchedulersProvider schedulersProvider) {

        return schedulersProvider;
    }
}
