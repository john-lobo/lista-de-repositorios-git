package com.jlndev.githubservice.ext

import com.jlndev.githubservice.data.db.model.GithubRepositoryEntity
import com.jlndev.githubservice.model.GithubRepositoryResponse
import com.jlndev.githubservice.model.GithubResponse
import com.jlndev.githubservice.model.OwnerResponse

fun List<GithubRepositoryEntity>.toGithubResponse(): GithubResponse {
    val responseList = this.map { entity ->
        GithubRepositoryResponse(
            id = entity.id,
            repositoryName = entity.repositoryName,
            stargazersCount = entity.stargazersCount,
            forksCount = entity.forksCount,
            owner = OwnerResponse(entity.avatarUrl, entity.login)
        )
    }
    return GithubResponse(responseList)
}

fun GithubRepositoryResponse.toGithubRepositoryEntity(): GithubRepositoryEntity {
    return GithubRepositoryEntity(
        id = this.id,
        repositoryName = this.repositoryName,
        stargazersCount = this.stargazersCount,
        forksCount = this.forksCount,
        avatarUrl = this.owner.avatarUrl,
        login = this.owner.login
    )
}