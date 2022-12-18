package com.uroosa.dieperson.di

import com.uroosa.dieperson.api.AuthInterceptor
import com.uroosa.dieperson.api.UserApi
import com.uroosa.dieperson.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*jitnay bhi application wale component hotae hai wo hum singleton mai call krtay hai
* singleton ka mtlb yehi hai k puri app mai srif ak hi object ho*/
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    /*it create restrofit instance*/


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {

        /*create retrofit object */
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    /*Create user api object*/
    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): UserApi {
        return retrofitBuilder.client(okHttpClient).build().create(UserApi::class.java)
    }


}