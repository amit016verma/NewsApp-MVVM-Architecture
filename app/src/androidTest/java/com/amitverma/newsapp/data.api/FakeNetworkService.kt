package com.amitverma.newsapp.data.api

import com.amitverma.newsapp.data.model.newssources.NewsSourcesResponse
import com.amitverma.newsapp.data.model.topheadlines.APIArticle
import com.amitverma.newsapp.data.model.topheadlines.APISource
import com.amitverma.newsapp.data.model.topheadlines.TopHeadlinesResponse


class FakeNetworkService : NetworkService {

    override suspend fun getTopHeadlines(
        country: String,
        page: Int ,
        pageSize: Int
    ): TopHeadlinesResponse {

        val listOfArticle = listOf(
            APIArticle(
                title = "title1",
                description = "description1",
                url = "https://www.cnn.com/2022/12/23/weather/christmas-arctic-winter-storm-poweroutages-friday/index.html",
                imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/221223020829-18-winter-weather-1222-illinois.jpg?c=16x9&q=w_800,c_fill",
                source = APISource("id1", "name1")
            ),
            APIArticle(
                title = "title2",
                description = "description2",
                url = "https://www.cnn.com/2022/12/23/weather/christmas-arctic-winter-storm-poweroutages-friday/index.html",
                imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/221223020829-18-winter-weather-1222-illinois.jpg?c=16x9&q=w_800,c_fill",
                source = APISource("id2", "name2")
            ),
            APIArticle(
                title = "title3",
                description = "description3",
                url = "https://www.cnn.com/2022/12/23/weather/christmas-arctic-winter-storm-poweroutages-friday/index.html",
                imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/221223020829-18-winter-weather-1222-illinois.jpg?c=16x9&q=w_800,c_fill",
                source = APISource("id3", "name3")
            ),
            APIArticle(
                title = "title4",
                description = "description4",
                url = "https://www.cnn.com/2022/12/23/weather/christmas-arctic-winter-storm-poweroutages-friday/index.html",
                imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/221223020829-18-winter-weather-1222-illinois.jpg?c=16x9&q=w_800,c_fill",
                source = APISource("id4", "name4")
            ),
            APIArticle(
                title = "title5",
                description = "description5",
                url = "https://www.cnn.com/2022/12/23/weather/christmas-arctic-winter-storm-poweroutages-friday/index.html",
                imageUrl = "https://media.cnn.com/api/v1/images/stellar/prod/221223020829-18-winter-weather-1222-illinois.jpg?c=16x9&q=w_800,c_fill",
                source = APISource("id5", "name5")
            )
        )
        return TopHeadlinesResponse("ok", 5, listOfArticle)

    }

    override suspend fun getNewsSources(): NewsSourcesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsBySources(sources: String): TopHeadlinesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsByLanguage(languageCode: String): TopHeadlinesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getNewsByQueries(queries: String): TopHeadlinesResponse {
        TODO("Not yet implemented")
    }
}