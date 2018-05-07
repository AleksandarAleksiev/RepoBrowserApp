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
import example.aleks.com.repobrowserapp.presentation.main.presenter.MainPresenter;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by aleks on 06/05/2018.
 */

public class MainPresenterTest {

    @Mock
    private IMainView mainView;

    @Mock
    private IAuthenticateInteractor authenticateInteractor;

    @Mock
    private IMainNavigator mainNavigator;

    @Mock
    private ISchedulersProvider schedulersProvider;

    @InjectMocks
    private MainPresenter mainPresenter;

    private final TestScheduler testScheduler = new TestScheduler();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(testScheduler).when(schedulersProvider).ioScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).uiScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).computationScheduler();
    }

    @Test
    public void should_Call_View_Login_If_User_Not_Authenticated() {

        Mockito.doReturn(Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                emitter.onComplete();
            }
        })).when(authenticateInteractor).getUserAuthToken();
        mainPresenter.start(null, null);
        testScheduler.triggerActions();

        Mockito.verify(mainView, Mockito.times(1)).login();
        Mockito.verify(mainNavigator, Mockito.times(0)).showUserGitRepos();
    }

    @Test
    public void should_Call_Navigator_Show_User_Repos_If_User_Is_Authenticated() {

        Mockito.doReturn(Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("FAKE");
            }
        })).when(authenticateInteractor).requestUserAuthToken(Mockito.anyString(), Mockito.anyString());
        mainPresenter.start("FAKE", "CODE");
        testScheduler.triggerActions();

        Mockito.verify(mainView, Mockito.times(0)).login();
        Mockito.verify(mainNavigator, Mockito.times(1)).showUserGitRepos();
    }
}
