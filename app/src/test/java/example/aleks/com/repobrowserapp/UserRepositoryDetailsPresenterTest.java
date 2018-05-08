package example.aleks.com.repobrowserapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import example.aleks.com.repobrowserapp.domain.interactor.user.repos.IUserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.models.User;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.presentation.repository.details.IUserRepositoryDetailsView;
import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.UserRepositoryDetailsPresenter;
import example.aleks.com.repobrowserapp.utils.scheduler.ISchedulersProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoryDetailsPresenterTest {

    @Mock
    private IUserRepositoryDetailsView repositoryDetailsView;
    @Mock
    private IUserRepositoriesInteractor repositoriesInteractor;
    @Mock
    private ISchedulersProvider schedulersProvider;
    @InjectMocks
    private UserRepositoryDetailsPresenter userRepositoryDetailsPresenter;

    private final TestScheduler testScheduler = new TestScheduler();

    @Before
    public void start() {

        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(testScheduler).when(schedulersProvider).ioScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).uiScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).computationScheduler();

    }

    @Test
    public void Verify_Method_Call() {

        final User user = new User();
        user.setUserAvatar("AVATAR");
        final UserRepository userRepository = new UserRepository();
        userRepository.setRepoName("FAKE");
        userRepository.setLanguage("FAKE");
        userRepository.setUser(user);
        Mockito.doReturn(Single.just(userRepository)).when(repositoriesInteractor).getUserRepositoryDetails(Mockito.anyString(), Mockito.anyString());

        userRepositoryDetailsPresenter.getDetails("FAKE", "FAKE");
        testScheduler.triggerActions();

        Mockito.verify(repositoriesInteractor, Mockito.times(1)).getUserRepositoryDetails(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(repositoryDetailsView, Mockito.times(2)).loading(Mockito.anyBoolean());
        Mockito.verify(repositoryDetailsView, Mockito.times(1)).update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
