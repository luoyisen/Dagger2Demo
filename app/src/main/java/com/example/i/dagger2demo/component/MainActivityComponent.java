package com.example.i.dagger2demo.component;

import com.example.i.dagger2demo.MainActivity;
import com.example.i.dagger2demo.module.MainActivityModule;
import com.example.i.dagger2demo.scope.MainActivityScope;

import dagger.Component;

/***
 * Created by I on 2017/9/24.
 */
@MainActivityScope
@Component(modules = MainActivityModule.class, dependencies = GithubApplicationComponent.class)
public interface MainActivityComponent {
     void inject(MainActivity mainActivity);
}
