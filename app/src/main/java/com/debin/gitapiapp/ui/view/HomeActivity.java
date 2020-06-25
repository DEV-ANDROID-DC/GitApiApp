package com.debin.gitapiapp.ui.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.debin.gitapiapp.R;
import com.debin.gitapiapp.model.data.GitRepo;
import com.debin.gitapiapp.ui.adapter.GitAdapter;
import com.debin.gitapiapp.viewmodel.GitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private GitViewModel gitViewModel;
    private GitAdapter gitAdapter = new GitAdapter(new ArrayList<>());
    private static final String TAG = "HomeActivity";

    @BindView(R.id.git_recycleview)
    RecyclerView gitRepoRecyclerView;

    @BindView(R.id.git_repo_edittext)
    EditText gitRepoEditText;

    @BindView(R.id.search_imageview)
    ImageView searchImageView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.error_textview)
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        gitRepoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gitRepoRecyclerView.setAdapter(gitAdapter);

        gitViewModel = new ViewModelProvider(this).get(GitViewModel.class);
        gitViewModel.getRepoList().observe(this, this::updateGitRepositories);
        gitViewModel.getLoading().observe(this, aBoolean -> {
            Log.d(TAG, "Loading == " +aBoolean);
            if(aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
                errorTextView.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        gitViewModel.getLoadingError().observe(this,
                aBoolean -> {
                    Log.d(TAG, "Loading Error == " +aBoolean);
                    if(aBoolean) {
                        gitAdapter.clearData();
                        errorTextView.setVisibility(View.VISIBLE);
                    } else {
                        errorTextView.setVisibility(View.GONE);
                    }
                });

        gitViewModel.noRepo().observe(this, aBoolean -> {
            if(aBoolean) {
                Log.d(TAG, "No Repo == " +aBoolean);
                gitAdapter.clearData();
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(R.string.no_repo);
            }
        });
        searchImageView.setOnClickListener(this);
    }

    private void updateGitRepositories(List<GitRepo> gitReposList) {
        gitAdapter.setGitRepo(gitReposList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_imageview:
                gitViewModel.getSearchRepoList(gitRepoEditText.getText().toString()).observe(HomeActivity.this,
                        this::updateGitRepositories);
        }
    }
}
