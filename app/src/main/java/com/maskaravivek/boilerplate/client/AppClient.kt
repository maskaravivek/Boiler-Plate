package com.maskaravivek.boilerplate.client

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface AppClient {
    @GET("getValue")
    fun getValue(@Query("query") query: String): Observable<String>
}