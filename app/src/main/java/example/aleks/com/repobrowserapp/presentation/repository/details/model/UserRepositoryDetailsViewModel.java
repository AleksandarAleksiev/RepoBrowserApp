package example.aleks.com.repobrowserapp.presentation.repository.details.model;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.IUserRepositoryDetailsPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoryDetailsViewModel extends ViewModel {

    private final IUserRepositoryDetailsPresenter repositoryDetailsPresenter;

    @Inject
    public UserRepositoryDetailsViewModel(IUserRepositoryDetailsPresenter repositoryDetailsPresenter) {

        this.repositoryDetailsPresenter = repositoryDetailsPresenter;
    }
}
