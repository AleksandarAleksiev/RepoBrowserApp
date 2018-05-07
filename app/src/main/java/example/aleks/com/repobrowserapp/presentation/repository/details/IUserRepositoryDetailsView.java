package example.aleks.com.repobrowserapp.presentation.repository.details;

/**
 * Created by aleks on 07/05/2018.
 */

public interface IUserRepositoryDetailsView {

    void loading(boolean loading);
    void update(String repositoryTitle, String ownerAvatar, String repositoryLanguage);
}
