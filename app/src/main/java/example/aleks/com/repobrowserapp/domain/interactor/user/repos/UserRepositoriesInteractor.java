package example.aleks.com.repobrowserapp.domain.interactor.user.repos;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import example.aleks.com.repobrowserapp.domain.models.User;
import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.ILocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.IUserGitReposRepository;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by aleks on 06/05/2018.
 */

public class UserRepositoriesInteractor implements IUserRepositoriesInteractor {

    private final IUserGitReposRepository userGitReposRepository;
    private final ILocalStorageRepository localStorageRepository;

    @Inject
    public UserRepositoriesInteractor(IUserGitReposRepository userGitReposRepository,
                                      ILocalStorageRepository localStorageRepository) {

        this.userGitReposRepository = userGitReposRepository;
        this.localStorageRepository = localStorageRepository;
    }

    @Override
    public Single<UserRepositories> getUserRepositories() {
        return loadUserRepos().first(UserRepositories.empty());
    }

    @Override
    public Single<UserRepository> getUserRepositoryDetails(String userName, String repositoryName) {
        return userGitReposRepository.getUserRepoDetails(userName, repositoryName)
                .map(new Function<GitHubUserRepo, UserRepository>() {
                    @Override
                    public UserRepository apply(GitHubUserRepo gitHubUserRepo) throws Exception {

                        return createFrom(gitHubUserRepo);
                    }
                });
    }

    @Override
    public Completable purgeCache() {

        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {

                if (!emitter.isDisposed()) {

                    localStorageRepository.dropUserRepositoriesCache();
                    emitter.onComplete();
                }
            }
        });
    }

    //region private methods
    private Flowable<UserRepositories> loadUserRepos() {

        return localStorageRepository.getUserRepositoriesFromCache()
                .concatWith(getUserRepositoriesFromServer())
                .filter(new Predicate<UserRepositories>() {
                    @Override
                    public boolean test(UserRepositories userRepositories) throws Exception {
                        return userRepositories != null
                                && userRepositories.getUserRepositories() != null
                                && !userRepositories.getUserRepositories().isEmpty();
                    }
                });
    }

    private Single<UserRepositories> getUserRepositoriesFromServer() {

        return userGitReposRepository.getUserRepositories()
                .flatMap(new Function<List<GitHubUserRepo>, SingleSource<UserRepositories>>() {
                    @Override
                    public SingleSource<UserRepositories> apply(List<GitHubUserRepo> gitHubUserRepos) throws Exception {

                        final List<UserRepository> repositories = new ArrayList<>();
                        for (final GitHubUserRepo gitHubUserRepo : gitHubUserRepos) {

                            if (gitHubUserRepo != null) {

                                repositories.add(createFrom(gitHubUserRepo));
                            }
                        }
                        final UserRepositories userRepositories = new UserRepositories();
                        userRepositories.setUserRepositories(repositories);

                        return localStorageRepository.addUserRepositoriesToCache(userRepositories);
                    }
                });
    }

    @NonNull
    private UserRepository createFrom(@NonNull GitHubUserRepo gitHubUserRepo) {

        final User user = new User();
        if (gitHubUserRepo.getGitHubUser() != null) {

            user.setUserId(gitHubUserRepo.getGitHubUser().getUserId());
            user.setUserName(gitHubUserRepo.getGitHubUser().getUserName());
            user.setUserAvatar(gitHubUserRepo.getGitHubUser().getUserAvatar());
        }

        final UserRepository userRepository = new UserRepository();
        userRepository.setRepoId(gitHubUserRepo.getRepoId());
        userRepository.setRepoName(gitHubUserRepo.getRepoName());
        userRepository.setRepoFullName(gitHubUserRepo.getRepoFullName());
        userRepository.setLanguage(gitHubUserRepo.getLanguage());
        userRepository.setUser(user);

        return userRepository;
    }
    //endregion
}
