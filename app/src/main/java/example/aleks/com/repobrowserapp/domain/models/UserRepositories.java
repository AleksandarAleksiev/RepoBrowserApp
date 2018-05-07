package example.aleks.com.repobrowserapp.domain.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aleks on 06/05/2018.
 */

public class UserRepositories {

    @SerializedName("page")
    private int page;

    @SerializedName("total")
    private int totalPages;

    @SerializedName("repositories")
    private List<UserRepository> userRepositories;

    @Nullable
    public List<UserRepository> getUserRepositories() {
        return userRepositories;
    }

    public void setUserRepositories(@Nullable List<UserRepository> userRepositories) {
        this.userRepositories = userRepositories;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
