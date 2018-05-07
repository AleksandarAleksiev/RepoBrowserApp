package example.aleks.com.repobrowserapp.presentation.repository.details.model;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.presentation.repository.details.presenter.IUserRepositoryDetailsPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoryDetailsViewModel extends ViewModel {

    private final IUserRepositoryDetailsPresenter repositoryDetailsPresenter;

    private String ownerName;
    private String repositoryName;

    @Inject
    public UserRepositoryDetailsViewModel(IUserRepositoryDetailsPresenter repositoryDetailsPresenter) {

        this.repositoryDetailsPresenter = repositoryDetailsPresenter;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void dispose() {
        repositoryDetailsPresenter.dispose();
    }

    public void getRepositoryDetails() {

    }
}
