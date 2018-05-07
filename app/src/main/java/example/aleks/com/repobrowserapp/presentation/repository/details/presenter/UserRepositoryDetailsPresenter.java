package example.aleks.com.repobrowserapp.presentation.repository.details.presenter;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;
import example.aleks.com.repobrowserapp.presentation.repository.details.IUserRepositoryDetailsView;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoryDetailsPresenter extends BasePresenter implements IUserRepositoryDetailsPresenter {

    private final IUserRepositoryDetailsView repositoryDetailsView;

    @Inject
    public UserRepositoryDetailsPresenter(IUserRepositoryDetailsView repositoryDetailsView) {

        this.repositoryDetailsView = repositoryDetailsView;
    }
}
