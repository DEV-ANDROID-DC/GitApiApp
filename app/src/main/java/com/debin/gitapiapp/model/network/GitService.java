package com.debin.gitapiapp.model.network;

import com.debin.gitapiapp.model.data.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.debin.gitapiapp.util.Constants.GET_REPOSITORIES;
import static com.debin.gitapiapp.util.Constants.USER_NAME;

public interface GitService {

    @GET(GET_REPOSITORIES)
    public Observable<List<GitRepo>> getUserRepositories(
            @Path(USER_NAME) String userName
    );
}
