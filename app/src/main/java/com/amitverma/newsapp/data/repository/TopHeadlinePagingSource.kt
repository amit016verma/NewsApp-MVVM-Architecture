package com.amitverma.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amitverma.newsapp.data.api.NetworkService
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.repository.PagingTopHeadlineRepository.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val STARTING_KEY = 1

class TopHeadlinePagingSource(
    private val networkService: NetworkService, private val country: String
) : PagingSource<Int, APIArticle>() {

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, APIArticle>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, APIArticle> {
        // Start paging with the STARTING_KEY if this is the first load
        val position = params.key ?: STARTING_KEY
        // Load as many items as hinted by params.loadSize
//        val range = start.until(start + params.loadSize)
        return try {
            val response = networkService.getTopHeadlines(
                country = country, page = position, pageSize = params.loadSize
            )
            val articles = response.articles
            val nextKey = if (articles.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (position == STARTING_KEY) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}