package example.aleks.com.repobrowserapp.presentation.main;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.presentation.base.BaseViewModel;
import example.aleks.com.repobrowserapp.utils.scheduler.ISchedulersProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by Aleksandar on 7.5.2018 г..
 */

public class MainViewModel extends BaseViewModel {

    private final IMainNavigator mainNavigator;
    private final IAuthenticateInteractor authenticateInteractor;
    private final ISchedulersProvider schedulersProvider;

    private String authCode;
    private String authState;
    private boolean userIsAuthenticated;

    @Inject
    public MainViewModel(IMainNavigator navigator,
                         IAuthenticateInteractor authenticateInteractor,
                         ISchedulersProvider schedulersProvider) {

        this.mainNavigator = navigator;
        this.authenticateInteractor = authenticateInteractor;
        this.schedulersProvider = schedulersProvider;
    }

    public String getAuthCode() {

        return authCode;
    }

    public void setAuthCode( String authCode ) {

        this.authCode = authCode;
    }

    public String getAuthState() {

        return authState;
    }

    public void setAuthState( String authState ) {

        this.authState = authState;
    }

    public void authorize() {

        if ( !userIsAuthenticated ) {

            if ( authCode == null || authCode.isEmpty() ) {
                login();
            } else {

                requestAuthorizationCode(  );
            }
        }
    }

    //region private methods

    private void login() {

        final Disposable dispoable = authenticateInteractor.getUserAuthToken()
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String userAuthToken) throws Exception {

                        userIsAuthenticated = true;
                        mainNavigator.showUserGitRepos();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        throwable.printStackTrace();

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                        mainNavigator.showLoginScreen();
                    }
                });

        add(dispoable);
    }

    private void requestAuthorizationCode() {

        final Disposable dispoable = authenticateInteractor.requestUserAuthToken(authCode, authState)
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String userAuthToken) throws Exception {

                        userIsAuthenticated = true;
                        mainNavigator.showUserGitRepos();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        throwable.printStackTrace();
                    }
                });

        add(dispoable);
    }

    //endregion
}
