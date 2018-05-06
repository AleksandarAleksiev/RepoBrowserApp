package example.aleks.com.repobrowserapp.domain.interactor.authenticate;

import javax.inject.Inject;

/**
 * Created by aleks on 06/05/2018.
 */

public class AuthenticateInteractor implements IAuthenticateInteractor {

    //region constructor
    @Inject
    public AuthenticateInteractor() {

    }
    //endregion

    //region IAuthenticateInteractor implementation
    @Override
    public String getUserAuthToken() {
        return null;
    }
    //endregion
}
