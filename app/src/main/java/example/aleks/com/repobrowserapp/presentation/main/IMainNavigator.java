package example.aleks.com.repobrowserapp.presentation.main;

/**
 * Created by aleks on 06/05/2018.
 */

public interface IMainNavigator {

    void showUserGitRepos();

    void showRepositoryDetails(String ownerName, String repositoryName);

    void showLoginScreen();
}
