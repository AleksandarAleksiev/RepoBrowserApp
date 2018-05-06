package example.aleks.com.repobrowserapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import example.aleks.com.repobrowserapp.domain.interactor.authenticate.IAuthenticateInteractor;
import example.aleks.com.repobrowserapp.presentation.main.IMainNavigator;
import example.aleks.com.repobrowserapp.presentation.main.IMainView;
import example.aleks.com.repobrowserapp.presentation.main.MainPresenter;

/**
 * Created by aleks on 06/05/2018.
 */

public class MainPresenterTest {

    @Mock
    public IMainView mainView;

    @Mock
    public IAuthenticateInteractor authenticateInteractor;

    @Mock
    public IMainNavigator mainNavigator;

    @InjectMocks
    public MainPresenter mainPresenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_Call_View_Login_If_User_Not_Authenticated() {

        Mockito.doReturn(null).when(authenticateInteractor).getUserAuthToken();
        mainPresenter.start();

        Mockito.verify(mainView, Mockito.times(1)).login();
        Mockito.verify(mainNavigator, Mockito.times(0)).showUserGitRepos();
    }

    @Test
    public void should_Call_Navigator_Show_User_Repos_If_User_Is_Authenticated() {

        Mockito.doReturn("FAKE").when(authenticateInteractor).getUserAuthToken();
        mainPresenter.start();

        Mockito.verify(mainView, Mockito.times(0)).login();
        Mockito.verify(mainNavigator, Mockito.times(1)).showUserGitRepos();
    }
}
