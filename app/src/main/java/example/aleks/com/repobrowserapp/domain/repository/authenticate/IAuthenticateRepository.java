package example.aleks.com.repobrowserapp.domain.repository.authenticate;

import example.aleks.com.repobrowserapp.api.model.AccessToken;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IAuthenticateRepository {

    AccessToken getAuthToken();

    void setAuthToken(AccessToken token);
}
