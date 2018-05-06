package example.aleks.com.repobrowserapp.presentation.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import example.aleks.com.repobrowserapp.BuildConfig;
import example.aleks.com.repobrowserapp.R;
import example.aleks.com.repobrowserapp.presentation.base.BaseActivity;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, IMainView, IMainNavigator {

    //region properties
    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    public IMainPresenter mainPresenter;
    //endregion

    //region activity methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.dispose();
    }

    //endregion

    //region HasSupportFragmentInjector implementation
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    //endregion

    //region IMainView implementation
    @Override
    public void login() {

        final String loginUrl = String.format("%s?client_id=%s&scope=repo&redirect_uri=",
                BuildConfig.LOGIN_URL, BuildConfig.CLIENT_ID, BuildConfig.REDIRECT_URI);
        final Intent loginIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl));
        startActivity(loginIntent);
    }
    //endregion

    //region IMainNavigator

    @Override
    public void showUserGitRepos() {

    }

    //endregion
}
