package com.uroosa.dieperson.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader("Authorization", "Bearer 36b2b70e9b60f21a0130e861726c6c59f3e49df0143d12ffaaa48fe59acb1415")
        return chain.proceed(request.build())
    }
}