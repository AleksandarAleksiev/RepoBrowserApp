package example.aleks.com.repobrowserapp.domain.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by aleks on 06/05/2018.
 */

public class UserRepositories {

    @SerializedName("repositories")
    private List<UserRepository> userRepositories;

    @Nullable
    public List<UserRepository> getUserRepositories() {
        return userRepositories;
    }

    public void setUserRepositories(@Nullable List<UserRepository> userRepositories) {
        this.userRepositories = userRepositories;
    }

    public static UserRepositories empty() {

        final UserRepositories emptyData = new UserRepositories();
        emptyData.setUserRepositories(Collections.<UserRepository>emptyList());
        return emptyData;
    }
}
