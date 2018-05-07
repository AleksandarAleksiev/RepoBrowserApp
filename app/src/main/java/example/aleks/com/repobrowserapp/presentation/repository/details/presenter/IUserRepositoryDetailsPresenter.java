package example.aleks.com.repobrowserapp.presentation.repository.details.presenter;

import example.aleks.com.repobrowserapp.presentation.mvp.IBasePresenter;

/**
 * Created by aleks on 07/05/2018.
 */

public interface IUserRepositoryDetailsPresenter extends IBasePresenter {
    void getDetails(String ownerName, String repositoryName);
}
