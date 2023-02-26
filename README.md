# NewsApp-MVVM-Architecture
This is a News application with MVVM Architecture and Jetpack Components with unit and instrumentation testing.

### Major Highlights
- MVVM Architecture
- Offline First architecture with a single source of truth
- Kotlin
- Dagger
- Room Database
- Retrofit
- Coroutines
- Flow
- StateFlow
- ViewBinding
- Pagination
- Unit Test
- UI Test

<p align="center">
<img alt="mvvm-architecture"  src="https://user-images.githubusercontent.com/15169743/219938204-45d0f055-602f-4f80-9bd4-1d614b8d6497.png">
</p>

## Feature implemented:
- NewsApp
- Instant search using Flow operators
  - Debounce
  - Filter
  - DistinctUntilChanged
  - FlatMapLatest
- Offline news
- Pagination
- Unit Test
  - Mockito
  - Turbine https://github.com/cashapp/turbine
  - Espresso
- TopHeadlines of the news
- Country-wise news
- Multiple Languages selection-wise news
- Multiple sources wise news 

## Dependency Used:
- Recycler View for listing
```
implementation "androidx.recyclerview:recyclerview:1.2.1"
implementation 'androidx.recyclerview:recyclerview-selection:1.1.0' //multi item selection
```
- Glide for image loading
```
implementation 'com.github.bumptech.glide:glide:4.11.0'
```
- Retrofit for networking
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.8.0'
```
- Android Lifecycle aware component 
```
implementation 'android.arch.lifecycle:extensions:1.1.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
```
- Dagger for dependency Injection 
```
implementation "com.google.dagger:dagger:2.42"
kapt "com.google.dagger:dagger-compiler:2.42"
```
- For WebView browser 
```
implementation 'androidx.browser:browser:1.4.0'
```
- Room Database 
```
implementation "androidx.room:room-runtime:2.4.2"
kapt "androidx.room:room-compiler:2.4.2"
// optional - Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:2.4.2"
```

- Paging library 
```
implementation "androidx.paging:paging-runtime:3.1.1"
```
- Local Unit test 
```
testImplementation 'junit:junit:4.13.2'
testImplementation "org.mockito:mockito-core:4.9.0"
kaptTest "com.google.dagger:dagger-compiler:2.42"
testImplementation "android.arch.core:core-testing:1.1.1"
testImplementation "org.hamcrest:hamcrest-library:2.1"
testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1'
testImplementation 'app.cash.turbine:turbine:0.12.1'
testImplementation 'androidx.test.espresso:espresso-core:3.5.0'
```

- UI Test
```
androidTestImplementation 'androidx.test.ext:junit:1.1.3'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.0'
androidTestImplementation "androidx.test.espresso:espresso-contrib:3.4.0"
androidTestImplementation "androidx.test.espresso:espresso-intents:3.4.0"
androidTestImplementation "org.mockito:mockito-core:4.9.0"
androidTestImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1'
androidTestImplementation "org.mockito:mockito-core:4.9.0"
kaptAndroidTest "com.google.dagger:dagger-compiler:2.42"
```
## The Complete Project Folder Structure
```
├── NewsApplication.kt
├── data
│   ├── api
│   ├── local
│   ├── model
│   └── repository
├── di
│   ├── component
│   ├── module
│   ├── qualifiers.kt
│   └── scopes.kt
├── ui
│   ├── MainActivity.kt
│   ├── base
│   ├── country
│   ├── language
│   ├── newsListScreen
│   ├── pagination
│   ├── search
│   ├── sources
│   └── topheadline
└── utils
    ├── AppConstant.kt
    ├── DispatcherProvider.kt
    ├── Extentions.kt
    ├── NetworkHelper.kt
    ├── Resource.kt
    ├── Status.kt
    ├── TypeAliases.kt
    └── network

```

<p align="center">
<img alt="main_screen" src="https://user-images.githubusercontent.com/15169743/221393544-6343e574-c0de-4bab-b421-2ab97202fdba.png" width="360"  height="640"> &nbsp;&nbsp;&nbsp;&nbsp;
<img alt="search_screen" src="https://user-images.githubusercontent.com/15169743/219934168-3a3c7f6e-bd3f-429f-acd3-e6f946454f01.png" width="360"  height="640" marginLeft="20">
</p>

<p align="center">
<img alt="top_headline" src="https://user-images.githubusercontent.com/15169743/219934213-64e7efd2-efd2-4a5f-a4f6-178218007e2e.png" width="360"  height="640">
&nbsp;&nbsp;&nbsp;&nbsp;
<img alt="news_source" src="https://user-images.githubusercontent.com/15169743/221393557-c2a42c70-d350-4fcb-8a21-f4276cbf62eb.png" width="360"  height="640"  marginLeft="20">
</p>


