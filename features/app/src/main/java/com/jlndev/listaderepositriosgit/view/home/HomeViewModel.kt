package com.jlndev.listaderepositriosgit.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlndev.baseservice.ext.BaseSchedulerProvider
import com.jlndev.baseservice.ext.disposedBy
import com.jlndev.baseservice.ext.processSingle
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.githubservice.data.GithubRepository
import com.jlndev.githubservice.model.GitHubSearchResponse
import com.jlndev.listaderepositriosgit.bases.BaseViewModel

class HomeViewModel(
    private val schedulerProvider: BaseSchedulerProvider,
    private val repository: GithubRepository
) : BaseViewModel() {

    private var itemCount = 1

    private val _repositoryFirstPageLive = MutableLiveData<ResponseState<GitHubSearchResponse>>()
    val repositoryFirstPageLive : LiveData<ResponseState<GitHubSearchResponse>>
        get() = _repositoryFirstPageLive

    private val _repositoryMorePageLive = MutableLiveData<ResponseState<GitHubSearchResponse>>()
    val repositoryMorePageLive : LiveData<ResponseState<GitHubSearchResponse>>
        get() = _repositoryMorePageLive

    init {
        loadFirstPageItems()
    }

    internal fun loadFirstPageItems() {
        repository.searchRepositories(itemCount)
            .processSingle(schedulerProvider)
            .doOnSubscribe { _repositoryFirstPageLive.value = ResponseState.Loading(true) }
            .doFinally { _repositoryFirstPageLive.value = ResponseState.Loading(false) }
            .doOnSuccess {
                _repositoryFirstPageLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryFirstPageLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }

    fun loadMoreItems() {
        incrementCount()
        repository.searchRepositories(itemCount)
            .processSingle(schedulerProvider)
            .doOnSubscribe { _repositoryMorePageLive.value = ResponseState.Loading(true) }
            .doFinally { _repositoryMorePageLive.value = ResponseState.Loading(false) }
            .doOnSuccess {
                _repositoryMorePageLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryMorePageLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }

    // Função para incrementar a contagem
    fun incrementCount() {
        itemCount++
    }
}