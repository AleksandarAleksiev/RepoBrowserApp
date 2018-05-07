package example.aleks.com.repobrowserapp.presentation.user.repoistories;

import java.util.List;

import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;

/**
 * Created by aleks on 07/05/2018.
 */

public interface IUserRepositoriesView {

    void loading(boolean isLoading);

    void update(List<RepositoryItem> userRepositories);
}
