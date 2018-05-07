package example.aleks.com.repobrowserapp.presentation.repository.details.presenter;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.user.repos.IUserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;
import example.aleks.com.repobrowserapp.presentation.repository.details.IUserRepositoryDetailsView;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoryDetailsPresenter extends BasePresenter implements IUserRepositoryDetailsPresenter {

    private final IUserRepositoryDetailsView repositoryDetailsView;
    private final IUserRepositoriesInteractor repositoriesInteractor;
    private final ISchedulersProvider schedulersProvider;

    @Inject
    public UserRepositoryDetailsPresenter(IUserRepositoryDetailsView detailsView,
                                          IUserRepositoriesInteractor repositoriesInteractor,
                                          ISchedulersProvider schedulersProvider) {

        this.repositoryDetailsView = detailsView;
        this.repositoriesInteractor = repositoriesInteractor;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public void getDetails(String ownerName, String repositoryName) {

        repositoryDetailsView.loading(true);
        final Disposable disposable = repositoriesInteractor.getUserRepositoryDetails(ownerName, repositoryName)
                 .subscribeOn(schedulersProvider.ioScheduler())
                 .observeOn(schedulersProvider.uiScheduler())
                 .subscribe(new Consumer<UserRepository>() {
                     @Override
                     public void accept(UserRepository userRepository) throws Exception {

                         repositoryDetailsView.loading(false);
                         repositoryDetailsView.update(userRepository.getRepoName(), userRepository.getAvatar(), userRepository.getLanguage());
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {

                         repositoryDetailsView.loading(false);
                     }
                 });

         add(disposable);

    }
}
