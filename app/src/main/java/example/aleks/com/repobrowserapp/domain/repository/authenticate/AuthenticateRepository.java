package example.aleks.com.repobrowserapp.domain.repository.authenticate;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by aleks on 06/05/2018.
 */

public class AuthenticateRepository implements IAuthenticateRepository {

    //region properties
    private static final String AUTH_TOKEN_KEY = "auth.token.key";
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
    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null);
    }

    @Override
    public void setAuthToken(String token) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN_KEY, token);
        editor.apply();
    }

    //endregion
}
