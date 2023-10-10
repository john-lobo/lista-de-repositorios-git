package com.jlndev.githubservice.data

import com.jlndev.githubservice.data.api.GithubService
import com.jlndev.githubservice.model.GitHubSearchResponse
import io.reactivex.Single


class GithubRepositoryImpl(private val service: GithubService) : GithubRepository {
    override fun searchRepositories(page: Int): Single<GitHubSearchResponse> {
        return service.searchRepositories(page = page)
    }
}