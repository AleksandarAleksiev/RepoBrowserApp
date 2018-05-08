package example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter;

import example.aleks.com.repobrowserapp.presentation.mvp.IBasePresenter;

/**
 * Created by aleks on 07/05/2018.
 */

public interface IUserRepositoriesPresenter extends IBasePresenter {

    void loadUserRepositories(boolean refresh);
}
