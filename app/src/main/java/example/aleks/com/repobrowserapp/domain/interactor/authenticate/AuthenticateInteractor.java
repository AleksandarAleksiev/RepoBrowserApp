package example.aleks.com.repobrowserapp.domain.interactor.authenticate;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.repository.authenticate.IAuthenticateRepository;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;

/**
 * Created by aleks on 06/05/2018.
 */

public class AuthenticateInteractor implements IAuthenticateInteractor {

    //region properties
    private final IAuthenticateRepository authenticateRepository;
    //endregion

    //region constructor
    @Inject
    public AuthenticateInteractor(IAuthenticateRepository authRepo) {

        authenticateRepository = authRepo;
    }
    //endregion

    //region IAuthenticateInteractor implementation
    @Override
    public Maybe<String> getUserAuthToken() {

        return Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {

                final String authToken = authenticateRepository.getAuthToken();
                if (!emitter.isDisposed()) {

                    if (authToken == null || authToken.isEmpty()) {

                        emitter.onComplete();
                    } else {

                        emitter.onSuccess(authToken);
                    }
                }
            }
        });
    }

    @Override
    public Single<String> requestUserAuthToken(String authCode) {
        return Single.just("NOT IMPLEMENTED");
    }

    //endregion
}
