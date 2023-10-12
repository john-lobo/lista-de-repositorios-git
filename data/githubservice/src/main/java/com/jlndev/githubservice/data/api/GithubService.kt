package com.jlndev.githubservice.data.api

import com.jlndev.githubservice.data.api.model.GithubResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/repositories?q=language:kotlin&sort=stars")
    fun searchRepositories(
        @Query("page") page: Int
    ): Single<GithubResponse>
}