package example.aleks.com.repobrowserapp.domain.interactor.authenticate;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.BuildConfig;
import example.aleks.com.repobrowserapp.api.GitHubAuthController;
import example.aleks.com.repobrowserapp.api.model.AccessToken;
import example.aleks.com.repobrowserapp.domain.repository.authenticate.IAuthenticateRepository;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by aleks on 06/05/2018.
 */

public class AuthenticateInteractor implements IAuthenticateInteractor {

    //region properties
    private final IAuthenticateRepository authenticateRepository;
    private final GitHubAuthController gitHubAuthController;
    //endregion

    //region constructor
    @Inject
    public AuthenticateInteractor(IAuthenticateRepository authRepo, GitHubAuthController restApi) {

        authenticateRepository = authRepo;
        gitHubAuthController = restApi;
    }
    //endregion

    //region IAuthenticateInteractor implementation
    @Override
    public Maybe<String> getUserAuthToken() {

        return Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {

                final AccessToken authToken = authenticateRepository.getAuthToken();
                if (!emitter.isDisposed()) {

                    if (authToken.getAccessToken() == null || authToken.getAccessToken().isEmpty()) {

                        emitter.onComplete();
                    } else {

                        emitter.onSuccess(authToken.getAccessToken());
                    }
                }
            }
        });
    }

    @Override
    public Single<String> requestUserAuthToken(@NonNull String authCode, @NonNull String state) {
        return gitHubAuthController.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, authCode, state)
                .map(new Function<AccessToken, String>() {
                    @Override
                    public String apply(AccessToken accessToken) throws Exception {

                        authenticateRepository.setAuthToken(accessToken);
                        return accessToken.getAccessToken();
                    }
                });
    }

    //endregion
}
