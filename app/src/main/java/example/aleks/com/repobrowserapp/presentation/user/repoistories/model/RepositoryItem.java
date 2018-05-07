package example.aleks.com.repobrowserapp.presentation.user.repoistories.model;

/**
 * Created by aleks on 07/05/2018.
 */

public class RepositoryItem {

    private final long repositoryId;
    private final String repositoryTitle;
    private final String repositoryLanguage;
    private final String ownerAvatarUrl;

    public RepositoryItem(long repositoryId,
                          String repositoryTitle,
                          String repositoryLanguage,
                          String ownerAvatarUrl) {

        this.repositoryId = repositoryId;
        this.repositoryTitle = repositoryTitle;
        this.repositoryLanguage = repositoryLanguage;
        this.ownerAvatarUrl = ownerAvatarUrl;
    }

    public String getRepositoryTitle() {
        return repositoryTitle;
    }

    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    public String getRepositoryLanguage() {
        return repositoryLanguage;
    }

    public long getRepositoryId() {
        return repositoryId;
    }
}
