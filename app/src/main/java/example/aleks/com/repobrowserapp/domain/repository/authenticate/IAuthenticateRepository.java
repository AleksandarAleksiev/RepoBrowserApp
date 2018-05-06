package example.aleks.com.repobrowserapp.domain.repository.authenticate;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IAuthenticateRepository {

    String getAuthToken();

    void setAuthToken(String token);
}
