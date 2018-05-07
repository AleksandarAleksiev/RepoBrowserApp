package example.aleks.com.repobrowserapp.api;

import java.util.List;

import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by aleks on 07/05/2018.
 */

public interface GitHubRepositoriesController {

    @GET("user/repos")
    Single<List<GitHubUserRepo>> getUserRepositories();
}
