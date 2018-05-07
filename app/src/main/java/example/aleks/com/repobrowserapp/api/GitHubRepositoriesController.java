package example.aleks.com.repobrowserapp.api;

import java.util.List;

import example.aleks.com.repobrowserapp.api.model.GitHubUserRepo;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by aleks on 07/05/2018.
 */

public interface GitHubRepositoriesController {

    @GET("user/repos")
    Single<List<GitHubUserRepo>> getUserRepositories();

    @Headers("Cache-Control: max-age=300")
    @GET("repos/{owner}/{name}")
    Single<GitHubUserRepo> getRepo(@Path("owner") String owner, @Path("name") String name);
}
