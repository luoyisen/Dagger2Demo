package com.example.i.dagger2demo.module;

import com.example.i.dagger2demo.scope.GithubApplicationScope;
import com.example.i.dagger2demo.service.GithubService;
import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * Created by I on 2017/9/24.
 */
@Module(includes = NetworkModule.class)
public class GithubServiceModule {
    @Provides
    @GithubApplicationScope
    public GithubService getGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    @Provides
    @GithubApplicationScope
    public Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());// TODO: 2017/9/24
        return gsonBuilder.create();
    }

    @Provides
    @GithubApplicationScope
    public Retrofit getRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.github.com/")
                .build();
    }
}
