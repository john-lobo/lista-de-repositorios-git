package com.jlndev.githubservice.data.service

import com.jlndev.githubservice.data.api.GithubService
import com.jlndev.githubservice.data.db.dao.GithubPageDao
import com.jlndev.githubservice.data.db.dao.GithubRepositoryDao
import com.jlndev.githubservice.data.db.model.GithubPageEntity
import com.jlndev.githubservice.ext.toGithubRepositoryEntity
import com.jlndev.githubservice.ext.toGithubResponse
import com.jlndev.githubservice.model.GithubResponse
import io.reactivex.Single

class GithubRepositoryImpl(
    private val service: GithubService,
    private val repositoryDao: GithubRepositoryDao,
    private val pageDao: GithubPageDao
) : GithubRepository {

    companion object {
        private const val ONE_VALUE = 1
    }

    override fun searchFirstRepositories(): Single<GithubResponse> {
        return repositoryDao.getAllRepositories()
            .flatMap {
                if (it.isEmpty()) {
                    return@flatMap searchAndUpdateDatabase(ONE_VALUE)
                } else {
                    return@flatMap Single.just(it.toGithubResponse())
                }}
            .onErrorResumeNext { _ ->
                searchAndUpdateDatabase(ONE_VALUE)
            }
    }

    override fun searchMoreRepositories(): Single<GithubResponse> {
        return pageDao.getPage()
            .flatMap { pageData ->
                val nextPage = pageData.page?.plus(ONE_VALUE) ?: ONE_VALUE
                searchAndUpdateDatabase(nextPage)
            }
    }

    private fun searchAndUpdateDatabase(page: Int): Single<GithubResponse> {
        return service.searchRepositories(page)
            .doOnSuccess { response ->
                repositoryDao.insertRepositories(response.items.map { it.toGithubRepositoryEntity() })
                pageDao.insertOrUpdatePage(GithubPageEntity(page = page)).subscribe()
            }
    }
}