package com.example.zhuji.network

import com.example.zhuji.utils.CookieUtil.encodeCookie
import com.example.zhuji.utils.CookieUtil.saveCookie
import com.example.zhuji.utils.DataStoreUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /**
     * 请求超时时间
     */
    private const val DEFAULT_TIMEOUT = 30000
    private lateinit var SERVICE: ApiService

    //手动创建一个OkHttpClient并设置超时时间
    val retrofit: ApiService
        get() {
            if (!RetrofitClient::SERVICE.isInitialized) {
                SERVICE = Retrofit.Builder()
                    .client(okHttp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ApiService.Base_Url)
                    .build()
                    .create(ApiService::class.java)
            }
            return SERVICE
        }

    //手动创建一个OkHttpClient并设置超时时间
    val okHttp: OkHttpClient
        get() {
            return OkHttpClient.Builder().run {
                connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                addInterceptor(LoggingInterceptor())
                addInterceptor {
                    val request = it.request()
                    val response = it.proceed(request)
                    val requestUrl = request.url.toString()
                    val domain = request.url.host
                    //cookie可能有多个，都保存下来
                    if ((requestUrl.contains("/user/login") || requestUrl.contains("/user/register"))) {
                        val cookies = response.headers("set-cookie")
                        val cookie = encodeCookie(cookies)
                        saveCookie(requestUrl, domain, cookie)
                    }
                    if (requestUrl.contains("/user/logout/json")) {
                        DataStoreUtils.clearSync()
                    }
                    response
                }
                addInterceptor {
                    val request = it.request()
                    val builder = request.newBuilder()
                    val domain = request.url.host
                    //获取domain内的cookie
                    if (domain.isNotEmpty()) {
                        val sqDomain: String = DataStoreUtils.readStringData(domain, "")
                        val cookie: String = if (sqDomain.isNotEmpty()) sqDomain else ""
                        if (cookie.isNotEmpty()) {
                            builder.addHeader("Cookie", cookie)
                        }
                    }
                    it.proceed(builder.build())
                }
//                //不验证证书
////                sslSocketFactory(createSSLSocketFactory())
////                hostnameVerifier(TrustAllNameVerifier())
                build()
            }
        }
}
//    private fun createSSLSocketFactory(): SSLSocketFactory {
//        lateinit var ssfFactory: SSLSocketFactory
//        try {
//            val sslFactory = SSLContext.getInstance("TLS")
//            sslFactory.init(null,  arrayOf(TrustAllCerts()), SecureRandom());
//            ssfFactory = sslFactory.socketFactory
//        } catch (e: Exception) {
//            print("SSL错误：${e.message}")
//        }
//        return ssfFactory
//    }


