package com.amitverma.newsapp.utils

import com.amitverma.newsapp.data.local.entity.Country
import com.amitverma.newsapp.data.local.entity.LanguageEntity


object AppConstant {

    const val COUNTRY = "us"
    const val NEWS_BY_SOURCES = "sources"
    const val NEWS_BY_COUNTRY = "country"
    const val NEWS_BY_LANGUAGE = "language"

    val LANGUAGES = listOf(
        LanguageEntity("ar", "Arabic"),
        LanguageEntity("de", "German"),
        LanguageEntity("en", "English"),
        LanguageEntity("es", "Spanish"),
        LanguageEntity("fr", "French"),
        LanguageEntity("he", "Hebrew"),
        LanguageEntity("it", "Italian"),
        LanguageEntity("nl", "Dutch"),
        LanguageEntity("no", "Norwegian"),
        LanguageEntity("pt", "Portuguese"),
        LanguageEntity("ru", "Russian"),
        LanguageEntity("sv", "Swedish"),
        LanguageEntity("zh", "Chinese")
    )

    val COUNTRIES = listOf(
        Country("ae", "United Arab Emirates"),
        Country("ar", "Argentina"),
        Country("at", "Austria"),
        Country("be", "Belgium"),
        Country("bg", "Bulgaria"),
        Country("br", "Brazil"),
        Country("ca", "Canada"),
        Country("ch", "Switzerland"),
        Country("cn", "China"),
        Country("co", "Colombia"),
        Country("cu", "Cuba"),
        Country("cz", "Czechia"),
        Country("de", "Germany"),
        Country("eg", "Egypt"),
        Country("fr", "France"),
        Country("gb", "United Kingdom of Great Britain and Northern Ireland"),
        Country("gr", "Greece"),
        Country("hk", "Hong Kong"),
        Country("hu", "Hungary"),
        Country("id", "Indonesia"),
        Country("ie", "Ireland"),
        Country("il", "Israel"),
        Country("in", "India"),
        Country("it", "Italy"),
        Country("jp", "Japan"),
        Country("kr", "Korea"),
        Country("lt", "Lithuania"),
        Country("lv", "Latvia"),
        Country("ma", "Morocco"),
        Country("mx", "Mexico"),
        Country("my", "Malaysia"),
        Country("ng", "Nigeria"),
        Country("nl", "Netherlands"),
        Country("no", "Norway"),
        Country("nz", "New Zealand"),
        Country("ph", "Philippines"),
        Country("pl", "Poland"),
        Country("pt", "Portugal"),
        Country("ro", "Romania"),
        Country("rs", "Serbia"),
        Country("ru", "Russian Federation"),
        Country("sa", "Saudi Arabia"),
        Country("se", "Sweden"),
        Country("sg", "Singapore"),
        Country("si", "Slovenia"),
        Country("sk", "Slovakia"),
        Country("th", "Thailand"),
        Country("tr", "Turkiye"),
        Country("tw", "Taiwan, Province of China"),
        Country("ua", "Ukraine"),
        Country("us", "United States of America"),
        Country("ve", "Venezuela"),
        Country("za", "South Africa")
    )
}