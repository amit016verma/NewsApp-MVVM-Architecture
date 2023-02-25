package com.amitverma.newsapp.ui.topheadline

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.local.entity.Source
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.model.topheadlines.APISource
import com.amitverma.newsapp.data.model.topheadlines.asEntity
import com.amitverma.newsapp.data.repository.TopHeadlineRepository
import com.amitverma.newsapp.utils.AppConstant
import com.amitverma.newsapp.utils.DispatcherProvider
import com.amitverma.newsapp.utils.Resource
import com.amitverma.newsapp.utils.TestDispatcherProvider
import com.amitverma.newsapp.utils.network.NetworkHelperImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Mock
    private lateinit var networkConnection: NetworkHelperImpl

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun givenServer200_whenFetchNews_thenShouldReturnSuccess() = runTest {
        val country = AppConstant.COUNTRY
        val source = APISource(
            id = "sourceId", name = "sourceName"
        )
        val articleApi = APIArticle(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticleAPI = mutableListOf<APIArticle>()
        listOfArticleAPI.add(articleApi)

        val article = articleApi.asEntity(country)
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        doReturn(true).`when`(networkConnection).isNetworkConnected()
        doReturn(flowOf(listOfArticleAPI)).`when`(topHeadlineRepository).getTopHeadlines(country)
        doReturn(flowOf(listOfArticle)).`when`(topHeadlineRepository)
            .saveTopHeadlinesArticles(listOfArticle, country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        verify(topHeadlineRepository).getTopHeadlines(country)
        verify(topHeadlineRepository).saveTopHeadlinesArticles(listOfArticle, AppConstant.COUNTRY)
    }

    @Test
    fun givenServer200_whenFetchNewsSavingInDB_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        val errorMsg = "An error occurred"
        val source = APISource(
            id = "sourceId", name = "sourceName"
        )
        val articleApi = APIArticle(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticleAPI = mutableListOf<APIArticle>()
        listOfArticleAPI.add(articleApi)

        val article = articleApi.asEntity(country)
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)

        doReturn(true).`when`(networkConnection).isNetworkConnected()
        doReturn(flowOf(listOfArticleAPI)).`when`(topHeadlineRepository).getTopHeadlines(country)
        doReturn(flow<List<Article>> { throw RuntimeException(errorMsg) }).`when`(
            topHeadlineRepository
        )
            .saveTopHeadlinesArticles(listOfArticle, country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        topHeadlineViewModel.data.test {
            assertEquals(
                Resource.error<List<Article>>(RuntimeException(errorMsg).toString()),
                awaitItem()
            )

        }

        verify(topHeadlineRepository).getTopHeadlines(country)
        verify(topHeadlineRepository).saveTopHeadlinesArticles(listOfArticle, AppConstant.COUNTRY)
    }

    @Test
    fun givenServerError_whenFetchNews_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        doReturn(true).`when`(networkConnection).isNetworkConnected()
        doReturn(flow<List<APIArticle>> { throw RuntimeException("broken") }).`when`(
            topHeadlineRepository
        ).getTopHeadlines(country)
        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)
        topHeadlineViewModel.data.test {
            assertEquals(
                Resource.error<List<APIArticle>>(RuntimeException("broken").toString()),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
        verify(topHeadlineRepository).getTopHeadlines(country)
    }

    @Test
    fun givenNoInternet_whenFetchNewsFromDB_thenReturnSuccess() = runTest {
        val country = AppConstant.COUNTRY
        val source = Source(
            sourceId = "sourceId", name = "sourceName"
        )
        val article = Article(
            title = "title",
            description = "description",
            url = "url",
            imageUrl = "urlToImage",
            source = source
        )
        val listOfArticle = mutableListOf<Article>()
        listOfArticle.add(article)
        doReturn(false).`when`(networkConnection).isNetworkConnected()
        doReturn(flowOf(listOfArticle)).`when`(topHeadlineRepository)
            .getAllTopHeadlinesArticles(country)
        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)
        topHeadlineViewModel.data.test {
            assertEquals(Resource.success(listOfArticle), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(topHeadlineRepository).getAllTopHeadlinesArticles(country)


    }


    @Test
    fun givenNoInternet_whenFetchNewsFromDBEmpty_thenReturnError() = runTest {
        val country = AppConstant.COUNTRY
        doReturn(false).`when`(networkConnection).isNetworkConnected()
        doReturn(flowOf(emptyList<Article>())).`when`(topHeadlineRepository)
            .getAllTopHeadlinesArticles(country)

        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)

        topHeadlineViewModel.data.test {
            assertEquals(Resource.error<List<Article>>("Data Not found."), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(topHeadlineRepository).getAllTopHeadlinesArticles(country)
    }

    @Test
    fun givenNoInternetAndDBError_whenFetchNewsFromDB_shouldExecuteErrorFlow() = runTest {
        val country = AppConstant.COUNTRY
        doReturn(false).`when`(networkConnection).isNetworkConnected()
        doReturn(flow<List<Article>> { throw IllegalStateException("Error") }).`when`(
            topHeadlineRepository
        ).getAllTopHeadlinesArticles(country)
        topHeadlineViewModel =
            TopHeadlineViewModel(topHeadlineRepository, networkConnection, dispatcherProvider)
        topHeadlineViewModel.data.test {
            assertEquals(
                Resource.error<List<Article>>(IllegalStateException("Error").toString()),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
        verify(topHeadlineRepository).getAllTopHeadlinesArticles(country)


    }


    @After
    fun tearDown() {

    }

}