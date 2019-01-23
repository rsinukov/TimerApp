object Versions {
    const val kotlin = "1.3.11"

    // AndroidX
    const val appCompat = "1.0.0"
    const val design = "1.0.0"
    const val lifecycle = "2.0.0"
    const val annotations = "1.0.0"

    // Networking, RxJava
    const val retrofit = "2.5.0"
    const val gson = "2.7"
    const val okHttp = "3.12.1"
    const val rxJava = "2.1.0"
    const val rxBinding = "3.0.0-alpha2"

    // Injection
    const val dagger = "2.20"

    // Logging
    const val timber = "4.7.1"

    // Testing
    const val mockito = "2.10.0"
    const val mockitoKotlin = "2.1.0"
    const val testRunner = "1.1.0"
}

object BuildPlugins {
    const val kotlinJvmTarget = "1.7"
    val android_plugin = "com.android.tools.build:gradle:3.2.1"
    val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Android {
    const val applicationId = "com.adambennett.jsonplaceholderapp"
    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = 28
    const val releaseCode = 1
    const val releaseName = "1.0.0"
}

object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // AndroidX
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val design = "com.google.android.material:material:${Versions.design}"
    const val androidAnnotations = "androidx.annotation:annotation:${Versions.annotations}"

    // Networking, RxJava
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val rxJava = "io.reactivex.rxjava2:rxandroid:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJava}"
    const val rxBindingV4 = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"

    // Injection
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerKapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    // Logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Other
    const val koptional = "com.gojuno.koptional:koptional:1.2.0"

    // Testing
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
}