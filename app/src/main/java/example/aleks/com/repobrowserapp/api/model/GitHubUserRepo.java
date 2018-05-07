package example.aleks.com.repobrowserapp.api.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleks on 07/05/2018.
 */

public class GitHubUserRepo {

    @SerializedName("id")
    private long repoId;

    @SerializedName("name")
    private String repoName;

    @SerializedName("full_name")
    private String repoFullName;

    @SerializedName("owner")
    private GitHubUser gitHubUser;

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
    public GitHubUser getGitHubUser() {
        return gitHubUser;
    }

    public void setGitHubUser(@Nullable GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
}
