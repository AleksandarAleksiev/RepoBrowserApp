package example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.user.repos.IUserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.IUserRepositoriesView;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoriesPresenter extends BasePresenter implements IUserRepositoriesPresenter {

    private final IUserRepositoriesView userRepositoriesView;
    private final ISchedulersProvider schedulersProvider;
    private final IUserRepositoriesInteractor userRepositoriesInteractor;

    @Inject
    public UserRepositoriesPresenter(IUserRepositoriesView userRepositoriesView,
                                     ISchedulersProvider schedulersProvider,
                                     IUserRepositoriesInteractor userRepositoriesInteractor) {

        this.userRepositoriesView = userRepositoriesView;
        this.schedulersProvider = schedulersProvider;
        this.userRepositoriesInteractor = userRepositoriesInteractor;
    }

    //region IPhotosPresenter photos presenter
    @Override
    public void loadUserRepositories(boolean refresh) {

        userRepositoriesView.loading(true);

        final Disposable disposable;
        if (refresh) {
            disposable = userRepositoriesInteractor.purgeCache()
                    .subscribeOn(schedulersProvider.ioScheduler())
                    .observeOn(schedulersProvider.uiScheduler())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {

                            add(getUserRepositories());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            userRepositoriesView.loading(false);
                        }
                    });
        } else {

            disposable = getUserRepositories();
        }

        add(disposable);
    }
    //endregion

    //region private methods
    private Disposable getUserRepositories() {

        return userRepositoriesInteractor.getUserRepositories()
                .map(new Function<UserRepositories, List<RepositoryItem>>() {
                    @Override
                    public List<RepositoryItem> apply(UserRepositories userRepositories) throws Exception {

                        final List<RepositoryItem> repositoryItems = new ArrayList<>();
                        for (final UserRepository userRepo : userRepositories.getUserRepositories()) {

                            final String avatar;
                            final String userName;
                            if (userRepo.getUser() != null) {

                                avatar = userRepo.getUser().getUserAvatar();
                                userName = userRepo.getUser().getUserName();
                            } else {

                                avatar = null;
                                userName = null;
                            }
                            final RepositoryItem repositoryItem = new RepositoryItem(userRepo.getRepoId(),
                                    userRepo.getRepoName(),
                                    userRepo.getRepoFullName(),
                                    userName,
                                    avatar);
                            repositoryItems.add(repositoryItem);
                        }

                        return repositoryItems;
                    }
                })
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.uiScheduler())
                .subscribe(new Consumer<List<RepositoryItem>>() {
                    @Override
                    public void accept(List<RepositoryItem> userRepositories) throws Exception {

                        userRepositoriesView.loading(false);
                        userRepositoriesView.update(userRepositories);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        userRepositoriesView.loading(false);
                    }
                });
    }
    //endregion
}
