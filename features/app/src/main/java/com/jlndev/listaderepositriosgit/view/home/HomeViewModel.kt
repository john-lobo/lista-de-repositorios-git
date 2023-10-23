package com.jlndev.listaderepositriosgit.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jlndev.baseservice.ext.BaseSchedulerProvider
import com.jlndev.baseservice.ext.disposedBy
import com.jlndev.baseservice.ext.processSingle
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.githubservice.data.api.model.GithubResponse
import com.jlndev.githubservice.data.service.GithubRepository
import com.jlndev.coreandroid.bases.viewModel.BaseViewModel

class HomeViewModel(
    private val schedulerProvider: BaseSchedulerProvider,
    private val repository: GithubRepository
) : BaseViewModel() {

    private val _repositoryFirstPageLive = MutableLiveData<ResponseState<GithubResponse>>()
    val repositoryFirstPageLive : LiveData<ResponseState<GithubResponse>>
        get() = _repositoryFirstPageLive

    private val _repositoryMorePageLive = MutableLiveData<ResponseState<GithubResponse>>()
    val repositoryMorePageLive : LiveData<ResponseState<GithubResponse>>
        get() = _repositoryMorePageLive

    private val _updateRepositoryLive = MutableLiveData<ResponseState<GithubResponse>>()
    val updateRepositoryLive : LiveData<ResponseState<GithubResponse>>
        get() = _updateRepositoryLive

    internal fun loadFirstPageItems() {
        repository.searchFirstRepositories()
            .processSingle(schedulerProvider)
            .doOnSubscribe{
                _repositoryFirstPageLive.value = ResponseState.Loading(true)
            }
            .doOnSuccess {
                _repositoryFirstPageLive.value = ResponseState.Loading(false)
                _repositoryFirstPageLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryFirstPageLive.value = ResponseState.Loading(false)
                _repositoryFirstPageLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }

    fun loadMoreItems() {
        repository.searchMoreRepositories()
            .processSingle(schedulerProvider)
            .doOnSubscribe{
                _repositoryMorePageLive.value = ResponseState.Loading(true)
            }
            .doOnSuccess {
                _repositoryMorePageLive.value = ResponseState.Loading(false)
                _repositoryMorePageLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _repositoryMorePageLive.value = ResponseState.Loading(false)
                _repositoryMorePageLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }

    fun clearAndSearchFirstRepositories() {
        repository.clearAndSearchFirstRepositories()
            .processSingle(schedulerProvider)
            .doOnSubscribe { _updateRepositoryLive.value = ResponseState.Loading(true) }
            .doOnSuccess {
                _updateRepositoryLive.value = ResponseState.Loading(false)
                _updateRepositoryLive.value = ResponseState.Success(it)
            }
            .doOnError {
                _updateRepositoryLive.value = ResponseState.Loading(false)
                _updateRepositoryLive.value = ResponseState.Error(it)
            }
            .disposedBy(disposables)
    }
}