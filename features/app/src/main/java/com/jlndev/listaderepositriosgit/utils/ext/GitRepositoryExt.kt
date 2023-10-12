package com.jlndev.listaderepositriosgit.utils.ext

import com.jlndev.githubservice.data.api.model.GithubRepositoryResponse
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem

fun GithubRepositoryResponse.toGitRepositoryItem() : GitRepositoryItem {
    return GitRepositoryItem(
        id = this.id.toString(),
        repositoryName = this.repositoryName.orEmpty(),
        stargazersCount = this.stargazersCount ?: 0,
        forksCount = this.forksCount ?: 0,
        avatarUrl = this.owner?.avatarUrl.orEmpty(),
        ownerName = this.owner?.login.orEmpty(),
        description = this.description.orEmpty(),
        visibility = this.visibility.orEmpty(),
        language = this.language.orEmpty()
    )
}