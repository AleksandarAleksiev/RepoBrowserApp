package example.aleks.com.repobrowserapp.api;

import example.aleks.com.repobrowserapp.api.model.AccessToken;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by aleks on 07/05/2018.
 */

public interface GitHubAuthController {

    @Headers("Accept:application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Single<AccessToken> getAccessToken(@Field("client_id") String clientId,
                                       @Field("client_secret") String clientSecret,
                                       @Field("code") String authCode,
                                       @Field("state") String state);
}
