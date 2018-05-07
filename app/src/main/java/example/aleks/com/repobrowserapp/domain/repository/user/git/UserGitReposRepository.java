package example.aleks.com.repobrowserapp.domain.repository.user.git;

import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.api.GitHubRepositoriesController;
import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public class UserGitReposRepository implements IUserGitReposRepository {

    private final GitHubRepositoriesController gitHubRepositoriesController;

    @Inject
    public UserGitReposRepository(GitHubRepositoriesController gitHubRepositoriesController) {

        this.gitHubRepositoriesController = gitHubRepositoriesController;
    }

    @Override
    public Single<List<GitHubUserRepo>> getUserRepositories() {
        return gitHubRepositoriesController.getUserRepositories();
    }

    @Override
    public Single<GitHubUserRepo> getUserRepoDetails(String userName, String repoName) {
        return gitHubRepositoriesController.getRepo(userName, repoName);
    }
}
