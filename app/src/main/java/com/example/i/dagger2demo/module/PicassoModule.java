package com.example.i.dagger2demo.module;

import android.content.Context;

import com.example.i.dagger2demo.scope.ApplicationContext;
import com.example.i.dagger2demo.scope.GithubApplicationScope;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/***
 * Created by I on 2017/9/24.
 */
@Module(includes = {ContextModule.class,NetworkModule.class})
public class PicassoModule {
    @Provides
    @GithubApplicationScope
    public Picasso getPicasso(@ApplicationContext Context context, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
    }

    @Provides
    @GithubApplicationScope
    public OkHttp3Downloader getOkHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }
}
