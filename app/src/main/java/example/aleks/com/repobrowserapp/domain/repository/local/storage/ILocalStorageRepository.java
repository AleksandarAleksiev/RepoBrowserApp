package example.aleks.com.repobrowserapp.domain.repository.local.storage;

import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import io.reactivex.Maybe;

/**
 * Created by aleks on 06/05/2018.
 */

public interface ILocalStorageRepository {

    void addUserRepositoriesToCache(UserRepositories userRepositories);
    void dropUserRepositoriesCache();
    Maybe<UserRepositories> getUserRepositoriesFromCache();
}
