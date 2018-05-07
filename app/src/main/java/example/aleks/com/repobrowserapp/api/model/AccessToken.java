package example.aleks.com.repobrowserapp.api.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aleks on 07/05/2018.
 */

public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;

    public AccessToken() {
        this(null, null);
    }

    public AccessToken(@Nullable String token, @Nullable String tokenType) {

        this.accessToken = token;
        this.tokenType = tokenType;
    }

    @Nullable
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@Nullable String accessToken) {
        this.accessToken = accessToken;
    }

    @Nullable
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(@Nullable String tokenType) {
        this.tokenType = tokenType;
    }
}
