package com.jlndev.listaderepositriosgit.view.home

import androidx.lifecycle.Observer
import com.jlndev.baseservice.state.ResponseState
import com.jlndev.githubservice.data.GithubRepository
import com.jlndev.githubservice.model.GitHubSearchResponse
import com.jlndev.listaderepositriosgit.BaseViewModelTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test
import java.util.concurrent.TimeUnit

class HomeViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: GithubRepository
    private lateinit var onRepositoryFirstPageObserver: Observer<ResponseState<GitHubSearchResponse>>
    private lateinit var onRepositoryPageLoadingObserver: Observer<Boolean>

    val searchResponse = GitHubSearchResponse(false, emptyList(), 0)

    override fun setup() {
        repository = mockk()
        onRepositoryFirstPageObserver = mockk(relaxed = true)
        onRepositoryPageLoadingObserver = mockk(relaxed = true)
        viewModel = instantiateViewModel()
    }

    @Test
    fun testSearchRepositoriesSuccess() {
        // Arrange
        every { repository.searchRepositories(1) } returns Single.just(searchResponse)

        // Act
        viewModel.loadFirstPageItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchRepositories(1) }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Success(searchResponse)) }
    }

    @Test
    fun testSearchRepositoriesError() {
        // Arrange
        val error = Throwable("Erro na busca de repositórios")
        every { repository.searchRepositories(1) } returns Single.error(error)

        // Act
        viewModel.loadFirstPageItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchRepositories(1) }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Error(error)) }
    }

    @Test
    fun testSearchRepositoriesMoreSuccess() {
        // Arrange
        every { repository.searchRepositories(2) } returns Single.just(searchResponse)

        // Act
        viewModel.loadMoreItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchRepositories(2) }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Success(searchResponse)) }
    }

    @Test
    fun testSearchRepositoriesMoreError() {
        // Arrange
        val error = Throwable("Erro na busca de repositórios")
        every { repository.searchRepositories(2) } returns Single.error(error)

        // Act
        viewModel.loadMoreItems()
        testScheduler.test().advanceTimeBy(1, TimeUnit.SECONDS)

        // Assert
        verify { repository.searchRepositories(2) }
        verify { onRepositoryFirstPageObserver.onChanged(ResponseState.Error(error)) }
    }

    private fun instantiateViewModel(): HomeViewModel {
        val viewModel = HomeViewModel(testScheduler, repository)
        observeLiveData(viewModel.repositoryFirstPageLive, onRepositoryFirstPageObserver)
        observeLiveData(viewModel.repositoryMorePageLive, onRepositoryFirstPageObserver)
        return viewModel
    }

}