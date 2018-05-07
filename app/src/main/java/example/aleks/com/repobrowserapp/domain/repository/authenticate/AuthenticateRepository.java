package example.aleks.com.repobrowserapp.domain.repository.authenticate;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.api.model.AccessToken;

/**
 * Created by aleks on 06/05/2018.
 */

public class AuthenticateRepository implements IAuthenticateRepository {

    //region properties
    private static final String AUTH_TOKEN_KEY = "auth.token.key";
    private static final String AUTH_TOKEN_TYPE_KEY = "auth.token.type.key";
    private final SharedPreferences sharedPreferences;
    //endregion

    //region constructor
    @Inject
    public AuthenticateRepository(SharedPreferences preferences) {

        sharedPreferences = preferences;
    }
    //endregion

    //region IAuthenticateRepository implementation

    @Override
    @NonNull
    public AccessToken getAuthToken() {
        final String token = sharedPreferences.getString(AUTH_TOKEN_KEY, null);
        final String tokenType = sharedPreferences.getString(AUTH_TOKEN_TYPE_KEY, null);
        return new AccessToken(token, tokenType);
    }

    @Override
    public void setAuthToken(@NonNull AccessToken token) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, token.getAccessToken());
        editor.putString(AUTH_TOKEN_TYPE_KEY, token.getTokenType());
        editor.apply();
    }

    //endregion
}
