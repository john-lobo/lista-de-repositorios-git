package com.jlndev.githubservice.data

import com.jlndev.githubservice.model.GitHubSearchResponse
import io.reactivex.Single

interface GithubRepository {
    fun searchRepositories(page: Int): Single<GitHubSearchResponse>
}