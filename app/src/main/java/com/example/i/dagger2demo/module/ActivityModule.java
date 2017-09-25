package com.example.i.dagger2demo.module;

import android.app.Activity;
import android.content.Context;

import com.example.i.dagger2demo.scope.GithubApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/***
 * Created by I on 2017/9/24.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @GithubApplicationScope
    @Named("activity_context")// TODO: 2017/9/24
    public Context getContext() {
        return activity;
    }
}
