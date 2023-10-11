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

    private val _repositoryFirstPageLoading = MutableLiveData<Boolean>()
    val repositoryFirstPageLoading: LiveData<Boolean>
        get() = _repositoryFirstPageLoading

    private val _repositoryMorePageLoading = MutableLiveData<Boolean>()
    val repositoryMorePageLoading: LiveData<Boolean>
        get() = _repositoryMorePageLoading

    internal fun loadFirstPageItems() {
        _repositoryFirstPageLoading.value = true

        repository.searchRepositories(itemCount)
            .processSingle(schedulerProvider)
            .doFinally { _repositoryFirstPageLoading.value = false }
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
        _repositoryMorePageLoading.value = true

        repository.searchRepositories(itemCount)
            .processSingle(schedulerProvider)
            .doFinally { _repositoryMorePageLoading.value = false }
            .doOnSuccess {
                _repositoryMorePageLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryMorePageLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }

    fun incrementCount() {
        itemCount++
    }
}