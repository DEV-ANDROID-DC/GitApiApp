package com.debin.gitapiapp.model.network;
import com.debin.gitapiapp.model.data.GitRepo;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.debin.gitapiapp.util.Constants.BASE_URL;

public class GitRepositoryInstance {

    private static GitRepositoryInstance gitRepositoryInstance = null;

    private GitService gitService;

    private GitRepositoryInstance() {
        gitService = createGitService(createRetrofitInstance());
    }

    public static GitRepositoryInstance gitRepositoryInstance() {
      if(gitRepositoryInstance == null) {
          gitRepositoryInstance = new GitRepositoryInstance();
      }
        return gitRepositoryInstance;
    }

    private Retrofit createRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private GitService createGitService(Retrofit retrofitInstance) {
        return retrofitInstance.create(GitService.class);
    }

    public Observable<List<GitRepo>> getGitRepositories(String userName) {
        return gitService.getUserRepositories(userName);
    }
}
