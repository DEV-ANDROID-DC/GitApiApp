package com.debin.gitapiapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.debin.gitapiapp.model.data.GitRepo;
import com.debin.gitapiapp.model.network.GitRepositoryInstance;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GitRepository {
    private static final String TAG = "GitRepository";
    private GitRepositoryInstance gitRepositoryInstance;

    public GitRepository()  {
        gitRepositoryInstance = GitRepositoryInstance.gitRepositoryInstance();
    }
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<GitRepo>> repoLiveData = new MutableLiveData<>();
    private MutableLiveData<List<GitRepo>> searchRepoLiveDate = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<>();
    public MutableLiveData<Boolean> noRepo = new MutableLiveData<>();

    public MutableLiveData<List<GitRepo>> getRepoList() {
        loading.setValue(true);
        compositeDisposable.add(
                gitRepositoryInstance.getGitRepositories("Debintom9633524015")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(repoList -> {
                            loading.setValue(false);
                            loadError.setValue(false);
                            repoLiveData.setValue(repoList);
                        },
                        throwable -> {
                            loading.setValue(false);
                            loadError.setValue(true);
                            Log.e(TAG, throwable.getLocalizedMessage());
                        }
                )
        );
        return repoLiveData;
    }

   public MutableLiveData<List<GitRepo>> getSearchRepoList(String repoName) {
        loading.setValue(true);
        compositeDisposable.add(
                gitRepositoryInstance.getGitRepositories(repoName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe( repoList -> {
                    loading.setValue(false);
                    loadError.setValue(false);
                    Log.d(TAG, "Repo List"+repoList);
                    if(!repoList.isEmpty()) {
                        searchRepoLiveDate.setValue(repoList);
                    } else {
                        noRepo.setValue(true);
                    }
                }, throwable -> {
                    loading.setValue(false);
                    loadError.setValue(true);
                            Log.e(TAG, throwable.getLocalizedMessage());
                        })
        );
       return searchRepoLiveDate;
   }

    public void clearDisposible() {
        compositeDisposable.clear();
    }
}
