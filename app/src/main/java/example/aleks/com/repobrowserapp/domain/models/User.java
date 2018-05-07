package example.aleks.com.repobrowserapp.domain.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleks on 07/05/2018.
 */

public class User {

    @SerializedName("id")
    private long userId;

    @SerializedName("login")
    private String userName;

    @SerializedName("avatar_url")
    private String userAvatar;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    @Nullable
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(@Nullable String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
