package example.aleks.com.repobrowserapp.presentation.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import example.aleks.com.repobrowserapp.BuildConfig;
import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseActivity;
import example.aleks.com.repobrowserapp.presentation.repository.details.UserRepositoryDetailsFragment;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.UserRepositoriesFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, IMainNavigator {

    //region properties
    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private MainViewModel mainViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of( this, viewModelFactory ).get( MainViewModel.class );
    }

    @Override
    protected void onResume() {

        super.onResume();

        userAuthorize();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mainViewModel.dispose();
    }

    //endregion

    //region HasSupportFragmentInjector implementation
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    //endregion

    //region IMainNavigator

    @Override
    public void showUserGitRepos() {

        Fragment userRepositories = getSupportFragmentManager().findFragmentByTag(UserRepositoriesFragment.TAG);
        if (userRepositories == null) {

            userRepositories = UserRepositoriesFragment.newInstance();
        }

        showScreen(userRepositories, UserRepositoriesFragment.TAG, false, false);
    }

    @Override
    public void showRepositoryDetails(String ownerName, String repositoryName) {

        Fragment userRepositoryDetails = getSupportFragmentManager().findFragmentByTag(UserRepositoryDetailsFragment.TAG);
        if (userRepositoryDetails == null) {

            userRepositoryDetails = UserRepositoryDetailsFragment.newInstance(ownerName, repositoryName);
        }

        showScreen(userRepositoryDetails, UserRepositoryDetailsFragment.TAG, true, true);
    }

    @Override
    public void showLoginScreen() {

        final String loginUrl = String.format("%slogin/oauth/authorize?client_id=%s&scope=repo&redirect_uri=%s&state=%s",
                BuildConfig.AUTH_BASEURL, BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URI, UUID.randomUUID().toString());
        final Intent loginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
        startActivity(loginIntent);
    }

    //endregion

    //region private methods
    private void userAuthorize() {

        final Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {

            final Uri callbackUri = intent.getData();
            if (callbackUri.toString().startsWith(BuildConfig.REDIRECT_URI)) {

                mainViewModel.setAuthCode( callbackUri.getQueryParameter("code"));
                mainViewModel.setAuthState( callbackUri.getQueryParameter("state"));
            }
        }

        mainViewModel.authorize();
    }
    //endregion
}
