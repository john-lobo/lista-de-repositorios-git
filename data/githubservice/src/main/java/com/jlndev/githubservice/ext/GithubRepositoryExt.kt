package com.jlndev.githubservice.ext

import com.jlndev.githubservice.data.api.model.GithubRepositoryResponse
import com.jlndev.githubservice.data.api.model.GithubResponse
import com.jlndev.githubservice.data.api.model.OwnerResponse
import com.jlndev.githubservice.data.db.model.GithubRepositoryEntity

fun List<GithubRepositoryEntity>.toGithubResponse(): GithubResponse {
    val responseList = this.map { entity ->
        GithubRepositoryResponse(
            id = entity.id,
            repositoryName = entity.repositoryName,
            stargazersCount = entity.stargazersCount,
            forksCount = entity.forksCount,
            owner = OwnerResponse(entity.avatarUrl, entity.login),
            description = entity.description,
            visibility = entity.visibility,
            language = entity.language
        )
    }
    return GithubResponse(responseList)
}

fun GithubRepositoryResponse.toGithubRepositoryEntity(): GithubRepositoryEntity {
    return GithubRepositoryEntity(
        id = this.id,
        repositoryName = this.repositoryName.orEmpty(),
        stargazersCount = this.stargazersCount ?: 0,
        forksCount = this.forksCount ?: 0,
        avatarUrl = this.owner?.avatarUrl.orEmpty(),
        login = this.owner?.login.orEmpty(),
        description = this.description.orEmpty(),
        visibility = this.visibility.orEmpty(),
        language = this.language.orEmpty()
    )
}