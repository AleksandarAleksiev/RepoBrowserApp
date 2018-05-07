package example.aleks.com.repobrowserapp.domain.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleks on 07/05/2018.
 */

public class UserRepository {

    @SerializedName("id")
    private long repoId;

    @SerializedName("name")
    private String repoName;

    @SerializedName("full_name")
    private String repoFullName;

    @SerializedName("owner")
    private User user;

    public long getRepoId() {
        return repoId;
    }

    public void setRepoId(long repoId) {
        this.repoId = repoId;
    }

    @Nullable
    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(@Nullable String repoName) {
        this.repoName = repoName;
    }

    @Nullable
    public String getRepoFullName() {
        return repoFullName;
    }

    public void setRepoFullName(@Nullable String repoFullName) {
        this.repoFullName = repoFullName;
    }

    @Nullable
    public User getUser() {
        return user;
    }

    public void setUser(@Nullable User gitHubUser) {
        this.user = gitHubUser;
    }
}
