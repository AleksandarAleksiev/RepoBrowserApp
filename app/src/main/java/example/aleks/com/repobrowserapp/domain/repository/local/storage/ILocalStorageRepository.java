package example.aleks.com.repobrowserapp.domain.repository.local.storage;

import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public interface ILocalStorageRepository {

    void dropUserRepositoriesCache();
    Single<UserRepositories> getUserRepositoriesFromCache();
    Single<UserRepositories> addUserRepositoriesToCache(UserRepositories userRepositories);
}
