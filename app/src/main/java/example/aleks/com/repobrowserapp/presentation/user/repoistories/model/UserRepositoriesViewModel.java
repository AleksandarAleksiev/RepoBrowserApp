package example.aleks.com.repobrowserapp.presentation.user.repoistories.model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepositoriesViewModel extends ViewModel {

    @NonNull
    private final List<RepositoryItem> repositoryItem = new ArrayList<>();
    private int page;
    private int total;
    private boolean isRefresh;

    @Inject
    public UserRepositoriesViewModel() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    @NonNull
    public List<RepositoryItem> getRepositoryItem() {
        return repositoryItem;
    }
}
