package example.aleks.com.repobrowserapp.domain.interactor.user.repos;

import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IUserRepositoriesInteractor {
    Single<UserRepositories> requestUserRepositories(int page);

    Maybe<UserRepositories> getCachedRepositories();
}
