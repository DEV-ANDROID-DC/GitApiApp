package com.debin.gitapiapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.debin.gitapiapp.model.data.GitRepo;
import com.debin.gitapiapp.repository.GitRepository;

import java.util.List;

public class GitViewModel extends ViewModel {

    private GitRepository gitRepository = new GitRepository();

    public LiveData<List<GitRepo>> getRepoList() {
        return gitRepository.getRepoList();
    }

    public LiveData<List<GitRepo>> getSearchRepoList(String repoName) {
        return gitRepository.getSearchRepoList(repoName);
    }


    public LiveData<Boolean> getLoading() {
        return gitRepository.loading;
    }

    public LiveData<Boolean> getLoadingError() {
        return gitRepository.loadError;
    }

    public LiveData<Boolean> noRepo() {
        return gitRepository.noRepo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        gitRepository.clearDisposible();
    }
}
