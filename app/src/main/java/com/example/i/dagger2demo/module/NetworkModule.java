package com.example.i.dagger2demo.module;

import android.content.Context;

import com.example.i.dagger2demo.scope.ApplicationContext;
import com.example.i.dagger2demo.scope.GithubApplicationScope;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/***
 * Created by I on 2017/9/24.
 */
@Module(includes = ContextModule.class)
// TODO: 2017/9/24
public class NetworkModule {
    @Provides
    @GithubApplicationScope
    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {// TODO: 2017/9/24
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @GithubApplicationScope
    public Cache getCache(File cacheFile) {// TODO: 2017/9/24
        return new Cache(cacheFile, 10 * 1000 * 1000);
    }

    @Provides
    @GithubApplicationScope
    public File getCacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    @GithubApplicationScope
    public OkHttpClient getOkHttoClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

    }

}
