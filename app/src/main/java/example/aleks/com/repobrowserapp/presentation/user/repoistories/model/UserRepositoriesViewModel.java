package example.aleks.com.repobrowserapp.presentation.user.repoistories.model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.presentation.main.IMainNavigator;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter.IUserRepositoriesPresenter;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoriesViewModel extends ViewModel {

    @NonNull
    private final List<RepositoryItem> repositoryItem = new ArrayList<>();
    private final IUserRepositoriesPresenter userRepositoriesPresenter;
    private final IMainNavigator mainNavigator;

    @Inject
    public UserRepositoriesViewModel(IUserRepositoriesPresenter userRepositoriesPresenter,
                                     IMainNavigator mainNavigator) {

        this.userRepositoriesPresenter = userRepositoriesPresenter;
        this.mainNavigator = mainNavigator;
    }

    @NonNull
    public List<RepositoryItem> getRepositoryItem() {
        return repositoryItem;
    }

    public void loadUserRepositories(boolean refresh) {

        userRepositoriesPresenter.loadUserRepositories(refresh);
    }

    public void viewRepoDetails(RepositoryItem repositoryItem) {

        mainNavigator.showRepositoryDetails(repositoryItem.getOwnerName(), repositoryItem.getRepositoryTitle());
    }

    public void dispose() {
        userRepositoriesPresenter.dispose();
    }
}
