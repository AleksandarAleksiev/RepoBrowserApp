package example.aleks.com.repobrowserapp.presentation.user.repoistories.model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by aleks on 07/05/2018.
 */

public class RepositoriesViewState {

    @NonNull
    private final List<RepositoryItem> repositoryItem;
    private final int page;
    private final boolean isRefresh;

    public RepositoriesViewState(@NonNull
                                 List<RepositoryItem> repositoryItem,
                                 int page,
                                 boolean isRefresh) {

        this.repositoryItem = repositoryItem;
        this.page = page;
        this.isRefresh = isRefresh;
    }

    @NonNull
    public List<RepositoryItem> getRepositoryItem() {
        return repositoryItem;
    }

    public int getPage() {
        return page;
    }

    public boolean isRefresh() {
        return isRefresh;
    }
}