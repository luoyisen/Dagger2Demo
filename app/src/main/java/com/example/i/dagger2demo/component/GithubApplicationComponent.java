package com.example.i.dagger2demo.component;

import com.example.i.dagger2demo.module.ActivityModule;
import com.example.i.dagger2demo.module.GithubServiceModule;
import com.example.i.dagger2demo.module.PicassoModule;
import com.example.i.dagger2demo.scope.GithubApplicationScope;
import com.example.i.dagger2demo.service.GithubService;
import com.squareup.picasso.Picasso;

import dagger.Component;

/***
 * Created by I on 2017/9/24.
 */
@GithubApplicationScope
@Component(modules = {GithubServiceModule.class, PicassoModule.class, ActivityModule.class})
public interface GithubApplicationComponent {
    Picasso getPicasso();

    GithubService getGithubService();
}
