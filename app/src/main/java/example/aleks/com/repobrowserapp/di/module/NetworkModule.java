package example.aleks.com.repobrowserapp.di.module;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.aleks.com.repobrowserapp.BuildConfig;
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
    Retrofit providesRetrofit() {

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

                        final Request original = chain.request();
                        final Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        return chain.proceed(request);
                    }
                })
                .cache(cache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
