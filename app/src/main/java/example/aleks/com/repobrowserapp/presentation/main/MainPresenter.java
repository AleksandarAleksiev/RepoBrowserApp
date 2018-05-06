package example.aleks.com.repobrowserapp.presentation.main;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;

/**
 * Created by aleks on 05/05/2018.
 */

public class MainPresenter extends BasePresenter implements IMainPresenter{

    //region properties
    private final IMainView mainView;
    private final IMainNavigator mainNavigator;
    private final IAuthenticateInteractor authenticateInteractor;
    //endregion

    //region constructor
    @Inject
    public MainPresenter(IMainView presenterView,
                         IMainNavigator navigator,
                         IAuthenticateInteractor authenticateInteractor) {
        this.mainView = presenterView;
        this.mainNavigator = navigator;
        this.authenticateInteractor = authenticateInteractor;
    }
    //endregion

    //region IMainPresenter implementation

    @Override
    public void start() {

        final String userAuthToken = authenticateInteractor.getUserAuthToken();
        if (userAuthToken == null || userAuthToken.isEmpty()) {

            mainView.login();
        } else {

            mainNavigator.showUserGitRepos();
        }
    }

    //endregion
}
