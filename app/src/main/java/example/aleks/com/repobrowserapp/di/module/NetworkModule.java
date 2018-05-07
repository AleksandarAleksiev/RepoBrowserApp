package example.aleks.com.repobrowserapp.di.module;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.aleks.com.repobrowserapp.BuildConfig;
import example.aleks.com.repobrowserapp.api.GitHubAuthController;
import example.aleks.com.repobrowserapp.api.GitHubRepositoriesController;
import example.aleks.com.repobrowserapp.api.model.AccessToken;
import example.aleks.com.repobrowserapp.domain.repository.authenticate.IAuthenticateRepository;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleks on 05/05/2018.
 */

@Module
public class NetworkModule {

    private final File cacheFile;

    public NetworkModule(@NonNull File cacheFile) {

        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(final IAuthenticateRepository authenticateRepository) {

        Cache cache = null;

        try {

            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        final AccessToken authToken = authenticateRepository.getAuthToken();


                        final Request original = chain.request();
                        final Request.Builder reqBuilder = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/vnd.github.v3+json");

                        if (authToken.getAccessToken() != null && !authToken.getAccessToken().isEmpty()) {
                            reqBuilder.header("Authorization", "token " + authToken.getAccessToken());
                        }

                        final Request request = reqBuilder.build();
                        final Response response = chain.proceed(request);

//                        String responseData = null;
//                        try {
//
//                            Map<String, String> headersMapMap = new HashMap<>();
//                            for (int index = 0; index < response.headers().size(); index++) {
//
//                                final String headerName = response.headers().name(index);
//                                final String headerValue = response.headers().value(index);
//                                headersMapMap.put(headerName, headerValue);
//                            }
//                            responseData = new String(response.body().bytes(), parseCharset(headersMapMap));
//                            String jsonResult = responseData;
//
//                        } catch (UnsupportedEncodingException e) {
//
//                            e.printStackTrace();
//                        }

                        return response;
                    }
                })
                .cache(cache)
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    GitHubAuthController providesGitHubClient(OkHttpClient okHttpClient) {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(GitHubAuthController.class);
    }

    @Provides
    @Singleton
    GitHubRepositoriesController providesGitHubRepositoriesController(OkHttpClient okHttpClient) {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(GitHubRepositoriesController.class);
    }

    public static String parseCharset(Map<String, String> headers) {
        return parseCharset(headers, "ISO_8859_1");
    }

    public static String parseCharset(Map<String, String> headers, String defaultCharset) {
        String contentType = headers.get("Content-Type");
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return defaultCharset;
    }
}
