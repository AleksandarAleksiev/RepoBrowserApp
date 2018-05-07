package example.aleks.com.repobrowserapp.domain.interactor.user.repos;

import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IUserRepositoriesInteractor {

    Single<UserRepositories> getUserRepositories();

    Single<UserRepository> getUserRepositoryDetails(String userName, String repositoryName);

    Completable purgeCache();
}
