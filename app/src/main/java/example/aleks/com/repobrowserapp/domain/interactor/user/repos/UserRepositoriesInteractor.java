package example.aleks.com.repobrowserapp.domain.interactor.user.repos;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import example.aleks.com.repobrowserapp.domain.models.User;
import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.domain.repository.local.storage.ILocalStorageRepository;
import example.aleks.com.repobrowserapp.domain.repository.user.git.IUserGitReposRepository;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

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
    public Single<UserRepositories> requestUserRepositories(final int page) {
        return userGitReposRepository.getUserRepositories()
                .map(new Function<List<GitHubUserRepo>, UserRepositories>() {
                    @Override
                    public UserRepositories apply(List<GitHubUserRepo> gitHubUserRepos) throws Exception {

                        final List<UserRepository> repositories = new ArrayList<>();
                        for (final GitHubUserRepo gitHubUserRepo : gitHubUserRepos) {

                            if (gitHubUserRepo != null) {

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
                                userRepository.setUser(user);
                                repositories.add(userRepository);
                            }
                        }
                        final UserRepositories userRepositories = new UserRepositories();
                        userRepositories.setPage(page);
                        userRepositories.setTotalPages(1);
                        userRepositories.setUserRepositories(repositories);

                        return userRepositories;
                    }
                });
    }

    @Override
    public Single<UserRepository> getUserRepositoryDetails(String userName, String repositoryName) {
        return userGitReposRepository.getUserRepoDetails(userName, repositoryName)
                .map(new Function<GitHubUserRepo, UserRepository>() {
                    @Override
                    public UserRepository apply(GitHubUserRepo gitHubUserRepo) throws Exception {

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
                        userRepository.setUser(user);

                        return userRepository;
                    }
                });
    }

    @Override
    public Maybe<UserRepositories> getCachedRepositories() {
        return localStorageRepository.getUserRepositoriesFromCache();
    }
}
