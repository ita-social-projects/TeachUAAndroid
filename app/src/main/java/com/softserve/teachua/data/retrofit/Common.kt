package com.softserve.teachua.data.retrofit

object Common {

    val retrofitService: RetrofitApi
        get() = RetrofitClient().getClient()



}
