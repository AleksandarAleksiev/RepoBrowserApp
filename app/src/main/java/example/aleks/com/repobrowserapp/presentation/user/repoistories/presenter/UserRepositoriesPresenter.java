package example.aleks.com.repobrowserapp.presentation.user.repoistories.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.aleks.com.repobrowserapp.domain.interactor.user.repos.IUserRepositoriesInteractor;
import example.aleks.com.repobrowserapp.domain.models.UserRepositories;
import example.aleks.com.repobrowserapp.domain.models.UserRepository;
import example.aleks.com.repobrowserapp.presentation.mvp.BasePresenter;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.IUserRepositoriesView;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoriesViewState;
import example.aleks.com.repobrowserapp.presentation.user.repoistories.model.RepositoryItem;
import example.aleks.com.repobrowserapp.utils.ISchedulersProvider;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
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
    public void loadUserRepositories(final RepositoriesViewState viewState) {

        userRepositoriesView.loading(true);

        final Disposable disposable = requestRepositories(viewState)
                .map(new Function<UserRepositories, List<RepositoryItem>>() {
                    @Override
                    public List<RepositoryItem> apply(UserRepositories userRepositories) throws Exception {

                        final List<RepositoryItem> repositoryItems = new ArrayList<>();
                        for (final UserRepository userRepo : userRepositories.getUserRepositories()) {

                            final String avatar = userRepo.getUser() == null ? null : userRepo.getUser().getUserAvatar();
                            final RepositoryItem repositoryItem = new RepositoryItem(userRepo.getRepoId(),
                                    userRepo.getRepoName(),
                                    userRepo.getRepoFullName(),
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
                        userRepositoriesView.update(userRepositories, 1, 1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        userRepositoriesView.loading(false);
                    }
                });

        add(disposable);
    }
    //endregion

    //region private methods
    private Single<UserRepositories> loadUserRepos(final RepositoriesViewState viewState) {

        Single<UserRepositories> repositoriesSingle;

        if (viewState.getRepositoryItem().isEmpty() && !viewState.isRefresh()) {

            repositoriesSingle = loadCachedRepositories()
                    .flatMap(new Function<UserRepositories, MaybeSource<UserRepositories>>() {
                        @Override
                        public MaybeSource<UserRepositories> apply(UserRepositories userRepositories) throws Exception {

                            Maybe<UserRepositories> userRepositoriesSingle;

                            if (userRepositories.getUserRepositories() == null || userRepositories.getUserRepositories().isEmpty()) {

                                userRepositoriesSingle = requestRepositories(viewState).toMaybe();
                            } else {

                                userRepositoriesSingle = Maybe.just(userRepositories);
                            }
                            return userRepositoriesSingle;
                        }
                    }).toSingle();
        } else {

            repositoriesSingle = requestRepositories(viewState);
        }

        return repositoriesSingle;
    }

    private Single<UserRepositories> requestRepositories(RepositoriesViewState viewState) {

        return userRepositoriesInteractor.requestUserRepositories(viewState.getPage());
    }

    private Maybe<UserRepositories> loadCachedRepositories() {

        return userRepositoriesInteractor.getCachedRepositories();
    }
    //endregion
}
