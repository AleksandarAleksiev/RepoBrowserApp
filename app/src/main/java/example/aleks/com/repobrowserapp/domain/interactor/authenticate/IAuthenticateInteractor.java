package example.aleks.com.repobrowserapp.domain.interactor.authenticate;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IAuthenticateInteractor {

    Maybe<String> getUserAuthToken();

    Single<String> requestUserAuthToken(String authCode);
}
