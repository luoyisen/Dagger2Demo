package com.example.i.dagger2demo.module;

import com.example.i.dagger2demo.MainActivity;
import com.example.i.dagger2demo.scope.MainActivityScope;

import dagger.Module;
import dagger.Provides;

/***
 * Created by I on 2017/9/24.
 */
@Module
public class MainActivityModule {
    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
