package com.jlndev.githubservice.data.service

import com.jlndev.githubservice.model.GithubResponse
import io.reactivex.Single

interface GithubRepository {
    fun searchFirstRepositories(): Single<GithubResponse>
    fun searchMoreRepositories(): Single<GithubResponse>
}