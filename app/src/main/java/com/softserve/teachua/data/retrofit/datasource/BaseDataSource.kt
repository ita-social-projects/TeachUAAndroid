package com.softserve.teachua.data.retrofit.datasource

import com.softserve.teachua.app.enums.Resource
import retrofit2.Response

//
//
//ALWAYS USE THIS
//
//
@Suppress("UNREACHABLE_CODE")
abstract class BaseDataSource{

    //Function that handles exceptions and return data wrapped in resource
    protected suspend fun <T> getResult(call: suspend () -> Response<T>) : Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful){
                val body = response.body()
                if(body!=null) return Resource.success(body)
            }

            return error("${response.code()} ${response.message()}")
        } catch (e: Exception){
            return Resource.error(data = null, message = e.message)
        }
    }
}