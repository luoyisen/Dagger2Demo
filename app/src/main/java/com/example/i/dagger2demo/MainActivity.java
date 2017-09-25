package com.example.i.dagger2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i.dagger2demo.component.DaggerMainActivityComponent;
import com.example.i.dagger2demo.component.MainActivityComponent;
import com.example.i.dagger2demo.model.GithubRepo;
import com.example.i.dagger2demo.module.MainActivityModule;
import com.example.i.dagger2demo.service.GithubService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.repo_home_list)
    ListView listView;

    Call<List<GithubRepo>> reposCall;

    @Inject
    GithubService githubService;
    @Inject
    ReposAdapter reposAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .githubApplicationComponent(GithubApplication.get(this).component())
                .build();

        component.inject(this);
        listView.setAdapter(reposAdapter);
        reposCall = githubService.getReposForUser("ReactiveX");
        reposCall.enqueue(new Callback<List<GithubRepo>>() {

            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                reposAdapter.swapData(response.body());
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error getting repos " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reposCall != null) {
            reposCall.cancel();
        }
    }
}
