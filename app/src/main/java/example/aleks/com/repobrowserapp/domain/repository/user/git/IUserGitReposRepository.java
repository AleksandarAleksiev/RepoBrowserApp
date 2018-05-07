package example.aleks.com.repobrowserapp.domain.repository.user.git;

import java.util.List;

import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IUserGitReposRepository {

    Single<List<GitHubUserRepo>> getUserRepositories();

    Single<GitHubUserRepo> getUserRepoDetails(String userName, String repoName);
}
