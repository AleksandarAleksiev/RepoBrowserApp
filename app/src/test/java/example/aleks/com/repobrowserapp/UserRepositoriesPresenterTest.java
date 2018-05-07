package example.aleks.com.repobrowserapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import example.aleks.com.repobrowserapp.api.model.GitHubUser;
import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import example.aleks.com.repobrowserapp.domain.interactor.user.repos.UserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.models.User;
import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.ILocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.IUserGitReposRepository;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.IUserRepositoriesView;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.UserRepositoriesViewModel;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.UserRepositoriesPresenter;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoriesPresenterTest {

    @Mock
    private IUserRepositoriesView repositoriesView;

    @Mock
    private ILocalStorageRepository localStorageRepository;

    @Mock
    private IUserGitReposRepository userGitReposRepository;

    @Mock
    private UserRepositoriesInteractor userRepositoriesInteractorMock;

    @Mock
    private ISchedulersProvider schedulersProvider;

    @InjectMocks
    private UserRepositoriesPresenter userRepositoriesPresenter;

    @InjectMocks
    private UserRepositoriesInteractor userRepositoriesInteractor;

    @InjectMocks
    private UserRepositoriesViewModel userRepositoriesViewModel;

    private final TestScheduler testScheduler = new TestScheduler();

    private final UserRepositories cachedItems = new UserRepositories();
    private final List<GitHubUserRepo> serverItems = new ArrayList<>();

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        Mockito.doReturn(testScheduler).when(schedulersProvider).ioScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).uiScheduler();
        Mockito.doReturn(testScheduler).when(schedulersProvider).computationScheduler();

        final GitHubUserRepo gitHubUserRepo = new GitHubUserRepo();
        final GitHubUser gitHubUser = new GitHubUser();

        gitHubUser.setUserName("OWN");
        gitHubUser.setUserId(1);
        gitHubUser.setUserAvatar("URL");

        gitHubUserRepo.setRepoId(2);
        gitHubUserRepo.setRepoName("SERVER");
        gitHubUserRepo.setLanguage("JAVA");
        gitHubUserRepo.setGitHubUser(gitHubUser);

        final UserRepository userRepository = new UserRepository();
        final User user = new User();

        user.setUserName("OWN");
        user.setUserId(1);
        user.setUserAvatar("URL");

        userRepository.setRepoId(1);
        userRepository.setRepoName("CACHE");
        userRepository.setLanguage("JAVA");
        userRepository.setUser(user);

        final List<UserRepository> cachedRepos = new ArrayList<>();
        cachedRepos.add(userRepository);
        cachedItems.setUserRepositories(cachedRepos);
        serverItems.add(gitHubUserRepo);
    }

    @Test
    public void Should_Load_User_Repositories_Into_View() {

        Mockito.doReturn(Single.just(cachedItems)).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(false);
        testScheduler.triggerActions();

        final RepositoryItem repositoryItem = userRepositoriesViewModel.getRepositoryItem().get(0);

        Assert.assertNotNull(repositoryItem);
    }

    @Test
    public void Should_Load_User_Repositories_From_Cache() {

        Mockito.doReturn(Single.just(cachedItems)).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(false);
        testScheduler.triggerActions();

        final RepositoryItem repositoryItem = userRepositoriesViewModel.getRepositoryItem().get(0);

        Assert.assertEquals("Item not loaded from cache", "CACHE",repositoryItem.getRepositoryTitle());
    }

    @Test
    public void Should_Load_User_Repositories_From_Server_Refresh_True() {

        Mockito.doReturn(Single.just(cachedItems)).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                cachedItems.getUserRepositories().clear();
                return null;
            }
        }).when(localStorageRepository).dropUserRepositoriesCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.purgeCache();
            }
        }).when(userRepositoriesInteractorMock).purgeCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final UserRepositories userRepositories = invocation.getArgument(0);
                return Single.just(userRepositories);
            }
        }).when(localStorageRepository).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(true);
        testScheduler.triggerActions();

        final RepositoryItem repositoryItem = userRepositoriesViewModel.getRepositoryItem().get(0);

        Assert.assertEquals("Item not loaded from cache", "SERVER",repositoryItem.getRepositoryTitle());
    }

    @Test
    public void Should_Load_User_Repositories_From_Server_Refresh_False_No_Cached_Items() {

        Mockito.doReturn(Single.just(UserRepositories.empty())).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                cachedItems.getUserRepositories().clear();
                return null;
            }
        }).when(localStorageRepository).dropUserRepositoriesCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.purgeCache();
            }
        }).when(userRepositoriesInteractorMock).purgeCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final UserRepositories userRepositories = invocation.getArgument(0);
                return Single.just(userRepositories);
            }
        }).when(localStorageRepository).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(false);
        testScheduler.triggerActions();

        final RepositoryItem repositoryItem = userRepositoriesViewModel.getRepositoryItem().get(0);

        Assert.assertEquals("Item not loaded from cache", "SERVER",repositoryItem.getRepositoryTitle());
    }

    @Test
    public void Verify_Method_Calls_When_Loading_From_Cache() {

        Mockito.doReturn(Single.just(cachedItems)).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(false);
        testScheduler.triggerActions();

        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(1)).getUserRepositories();
        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(0)).purgeCache();
        Mockito.verify(localStorageRepository, Mockito.times(1)).getUserRepositoriesFromCache();
        Mockito.verify(repositoriesView, Mockito.times(2)).loading(Mockito.anyBoolean());
        Mockito.verify(repositoriesView, Mockito.times(1)).update(Mockito.<RepositoryItem>anyList());
    }

    @Test
    public void Verify_Method_Calls_When_Loading_From_Server_Refresh_True() {

        Mockito.doReturn(Single.just(cachedItems)).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                cachedItems.getUserRepositories().clear();
                return null;
            }
        }).when(localStorageRepository).dropUserRepositoriesCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.purgeCache();
            }
        }).when(userRepositoriesInteractorMock).purgeCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final UserRepositories userRepositories = invocation.getArgument(0);
                return Single.just(userRepositories);
            }
        }).when(localStorageRepository).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(true);
        testScheduler.triggerActions();

        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(1)).getUserRepositories();
        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(1)).purgeCache();
        Mockito.verify(localStorageRepository, Mockito.times(1)).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));
        Mockito.verify(userGitReposRepository, Mockito.times(1)).getUserRepositories();
        Mockito.verify(repositoriesView, Mockito.times(2)).loading(Mockito.anyBoolean());
        Mockito.verify(repositoriesView, Mockito.times(1)).update(Mockito.<RepositoryItem>anyList());
    }

    @Test
    public void Verify_Method_Calls_When_Loading_From_Server_Refresh_False() {

        Mockito.doReturn(Single.just(UserRepositories.empty())).when(localStorageRepository).getUserRepositoriesFromCache();
        Mockito.doReturn(Single.just(serverItems)).when(userGitReposRepository).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                cachedItems.getUserRepositories().clear();
                return null;
            }
        }).when(localStorageRepository).dropUserRepositoriesCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.getUserRepositories();
            }
        }).when(userRepositoriesInteractorMock).getUserRepositories();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return userRepositoriesInteractor.purgeCache();
            }
        }).when(userRepositoriesInteractorMock).purgeCache();

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final UserRepositories userRepositories = invocation.getArgument(0);
                return Single.just(userRepositories);
            }
        }).when(localStorageRepository).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                final ArrayList<RepositoryItem> arrayList = invocation.getArgument(0);
                userRepositoriesViewModel.getRepositoryItem().clear();
                userRepositoriesViewModel.getRepositoryItem().addAll(arrayList);
                return null;
            }
        }).when(repositoriesView).update(Mockito.<RepositoryItem>anyList());

        userRepositoriesPresenter.loadUserRepositories(false);
        testScheduler.triggerActions();

        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(1)).getUserRepositories();
        Mockito.verify(userRepositoriesInteractorMock, Mockito.times(0)).purgeCache();
        Mockito.verify(localStorageRepository, Mockito.times(1)).getUserRepositoriesFromCache();
        Mockito.verify(localStorageRepository, Mockito.times(1)).addUserRepositoriesToCache(Mockito.any(UserRepositories.class));
        Mockito.verify(userGitReposRepository, Mockito.times(1)).getUserRepositories();
        Mockito.verify(repositoriesView, Mockito.times(2)).loading(Mockito.anyBoolean());
        Mockito.verify(repositoriesView, Mockito.times(1)).update(Mockito.<RepositoryItem>anyList());
    }
}
