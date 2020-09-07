package com.example.baselibrary.di.modules

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.data.api.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * Retrofit管理类
 */
@Module
class ApiServiceModule {

    /**
     * 初始化 OkHttpClient
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .writeTimeout(ConfigConstants.OKHTTP_WRITE_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(ConfigConstants.OKHTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ConfigConstants.OKHTTP_READ_TIME_OUT, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(3, 1, TimeUnit.SECONDS))//设置okhttp的连接池保活时间
        val sClient = lgnoreHttps(httpClientBuilder)
        return sClient
    }

    /**
     * 忽略https验证
     */
    private fun lgnoreHttps(httpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        val sClient = httpClientBuilder.build()
        var sc: SSLContext? = null
        try {
            sc = SSLContext.getInstance("SSL")
            sc.init(null, arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(java.security.cert.CertificateException::class)
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                @Throws(java.security.cert.CertificateException::class)
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }
            }), SecureRandom())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val hv1 = HostnameVerifier { _, _ -> true }

        val workerClassName = "okhttp3.OkHttpClient"

        try {
            val workerClass = Class.forName(workerClassName)
            val hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier")
            hostnameVerifier.isAccessible = true
            hostnameVerifier.set(sClient, hv1)

            val sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory")
            sslSocketFactory.isAccessible = true;
            sslSocketFactory.set(sClient, sc?.socketFactory)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sClient
    }

    /**
     * 获取 Retrofit实例
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
        return Retrofit.Builder()
            .baseUrl(ConfigConstants.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * 获取对应的ApiService
     */
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}