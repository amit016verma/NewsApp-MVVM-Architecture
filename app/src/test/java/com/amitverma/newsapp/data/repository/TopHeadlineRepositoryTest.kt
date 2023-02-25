package com.amitverma.newsapp.data.repository

import app.cash.turbine.test
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.local.dao.TopHeadlinesDao
import com.amitverma.newsapp.data.local.entity.Article
import com.amitverma.newsapp.data.local.entity.Source
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.model.topheadlines.APISource
import com.amitverma.newsapp.data.model.topheadlines.TopHeadlinesResponse
import com.amitverma.newsapp.utils.AppConstant
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var topHeadlinesDao: TopHeadlinesDao

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService, topHeadlinesDao)
    }

    @Test
    fun whenGetTopHeadlines_thenReturnListOfArticleAPI() = runTest {
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

        val topHeadlinesResponse = TopHeadlinesResponse(
            status = "ok", count = 1, articles = listOfArticleAPI
        )
        doReturn(topHeadlinesResponse).`when`(networkService).getTopHeadlines(country)

        topHeadlineRepository.getTopHeadlines(country).test {
            assertEquals(topHeadlinesResponse.articles, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

//        verify(networkService).getTopHeadlines(country)
        verify(networkService, times(1)).getTopHeadlines(country)
    }

    @Test
    fun whenSaveTopHeadlinesArticles_thenReturnListOfInsertedRowId() = runTest {
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

        val savedArticleRowId = listOf<Long>(1)

        doReturn(savedArticleRowId).`when`(topHeadlinesDao)
            .insertAndDeleteTopHeadlineArticles(country = country, articles = listOfArticle)

        topHeadlineRepository.saveTopHeadlinesArticles(articles = listOfArticle, country = country)
            .test {
                assertEquals(savedArticleRowId, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        verify(topHeadlinesDao).insertAndDeleteTopHeadlineArticles(
            country = country, articles = listOfArticle
        )
    }

    @Test
    fun whenGetAllTopHeadlinesArticlesFromDB_thenShouldReturnArticleEntity() = runTest {
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

        doReturn(flowOf(listOfArticle)).`when`(topHeadlinesDao).getAllTopHeadlinesArticle(country)
        topHeadlineRepository.getAllTopHeadlinesArticles(country)

        verify(topHeadlinesDao).getAllTopHeadlinesArticle(country)

    }
}