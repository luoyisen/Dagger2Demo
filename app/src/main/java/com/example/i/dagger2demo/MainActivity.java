package com.example.i.dagger2demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.i.dagger2demo.component.DaggerMainActivityComponent;
import com.example.i.dagger2demo.component.MainActivityComponent;
import com.example.i.dagger2demo.model.GithubRepo;
import com.example.i.dagger2demo.model.GithubUser;
import com.example.i.dagger2demo.module.MainActivityModule;
import com.example.i.dagger2demo.service.GithubService;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {
    Call<List<GithubRepo>> reposCall;
    Call<GithubUser> userCall;
    @BindView(R.id.repo_home_list)
    ListView listView;
    @BindView(R.id.serchview_main)
    SearchView searchView;
    @BindView(R.id.user_avatar)
    ImageView avatar;
    @BindView(R.id.image_search)
    ImageView search;
    @BindView(R.id.emptyview)
    RelativeLayout emptyview;

    @Inject
    GithubService githubService;
    @Inject
    ReposAdapter reposAdapter;
    @Inject
    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        searchView.onActionViewExpanded();
        searchView.setQueryHint("search repos from github");
        LinearLayout ll_searchview = (LinearLayout) findViewById(R.id.search_edit_frame);
        ll_searchview.setBackground(getResources().getDrawable(R.drawable.shape_searchview));
        ll_searchview.setLayoutParams(new LinearLayout.LayoutParams(DisplayMetricsConvert.pxToDp(getApplicationContext(), 260), DisplayMetricsConvert.pxToDp(getApplicationContext(), 32)));
        searchView.setSubmitButtonEnabled(false);
        LinearLayout search_bar = (LinearLayout) findViewById(R.id.search_bar);
        search_bar.setGravity(Gravity.CENTER);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearch(query);
                Toast.makeText(MainActivity.this, "开始搜索", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .githubApplicationComponent(GithubApplication.get(this).component())
                .build();

        component.inject(this);
        listView.setAdapter(reposAdapter);
    }

    private void startSearch(String query) {
        reposCall = githubService.getReposForUser(query);
        reposCall.enqueue(new Callback<List<GithubRepo>>() {

            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                if (response.body() != null) {
                    if (listView.getVisibility() == View.GONE) {
                        listView.setVisibility(View.VISIBLE);
                        emptyview.setVisibility(View.GONE);

                    }
                    reposAdapter.swapData(response.body());
                } else {
                    listView.setVisibility(View.GONE);
                    emptyview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        userCall = githubService.getUser(query);
        userCall.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                if (response.body() != null) {
                    picasso.load(response.body().avatarUrl)
                            .placeholder(R.drawable.ic_person_black_24dp)
                            .into(avatar);
                }

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {

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
