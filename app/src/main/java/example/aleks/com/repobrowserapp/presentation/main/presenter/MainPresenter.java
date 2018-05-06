package example.aleks.com.repobrowserapp.presentation.main.presenter;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.presentation.main.IMainNavigator;
import example.aleks.com.repobrowserapp.presentation.main.IMainView;
import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by aleks on 05/05/2018.
 */

public class MainPresenter extends BasePresenter implements IMainPresenter {

    //region properties
    private final IMainView mainView;
    private final IMainNavigator mainNavigator;
    private final IAuthenticateInteractor authenticateInteractor;
    private final ISchedulersProvider schedulersProvider;
    //endregion

    //region constructor
    @Inject
    public MainPresenter(IMainView presenterView,
                         IMainNavigator navigator,
                         IAuthenticateInteractor authenticateInteractor,
                         ISchedulersProvider schedulersProvider) {
        this.mainView = presenterView;
        this.mainNavigator = navigator;
        this.authenticateInteractor = authenticateInteractor;
        this.schedulersProvider = schedulersProvider;
    }
    //endregion

    //region IMainPresenter implementation

    @Override
    public void start(@Nullable String authCode) {

        mainView.loading(true);

        if (authCode == null || authCode.isEmpty()) {

            login();
        } else {

            validateAuthCode(authCode);
        }
    }

    //endregion

    //region private methods

    private void login() {

        final Disposable dispoable = authenticateInteractor.getUserAuthToken()
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String userAuthToken) throws Exception {

                        mainView.loading(false);
                        mainNavigator.showUserGitRepos();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mainView.loading(false);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                        mainView.loading(false);
                        mainView.login();
                    }
                });

        add(dispoable);
    }

    private void validateAuthCode(String authCode) {

        mainView.loading(true);
        final Disposable dispoable = authenticateInteractor.requestUserAuthToken(authCode)
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String userAuthToken) throws Exception {

                        mainView.loading(false);
                        mainNavigator.showUserGitRepos();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mainView.loading(false);
                    }
                });

        add(dispoable);
    }

    //endregion
}
