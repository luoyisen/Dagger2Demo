package com.example.i.dagger2demo.module;

import android.content.Context;

import com.example.i.dagger2demo.scope.ApplicationContext;
import com.example.i.dagger2demo.scope.GithubApplicationScope;

import dagger.Module;
import dagger.Provides;

/***
 * Created by I on 2017/9/24.
 */
@Module
public class ContextModule {
    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @GithubApplicationScope
    @ApplicationContext
    public Context getContext() {// TODO: 2017/9/24
        return context;
    }

}
