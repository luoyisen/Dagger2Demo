package com.example.i.dagger2demo;

import android.app.Activity;
import android.app.Application;

import com.example.i.dagger2demo.component.DaggerGithubApplicationComponent;
import com.example.i.dagger2demo.component.GithubApplicationComponent;
import com.example.i.dagger2demo.module.ContextModule;
import com.example.i.dagger2demo.service.GithubService;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

/***
 * Created by I on 2017/9/25.
 */

public class GithubApplication extends Application {
    private GithubApplicationComponent component;

    public static GithubApplication get(Activity activity) {
        return (GithubApplication) activity.getApplication();
    }

    public GithubService githubService;

    public Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        component = DaggerGithubApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        githubService = component.getGithubService();
        picasso = component.getPicasso();
    }

    public GithubApplicationComponent component() {
        return component;
    }
}