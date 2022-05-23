package com.softserve.teachua.app.tools

import com.softserve.teachua.app.enums.Resource

suspend fun <T> performGetFromRemote(
    networkCall: suspend () -> Resource<T>
) : Resource<T> {
    return networkCall.invoke()
}

suspend fun <T, V> performGetFromRemoteAndMapData(
    networkCall: suspend () -> Resource<T>,
    map: suspend (T) -> V
) : Resource<V> {
    val call = networkCall.invoke()
    if (call.status == Resource.Status.SUCCESS){
        val let = call.data?.let { map.invoke(it) }
        if (let != null){
            return Resource.success(let)
        }
    }
    return Resource.error()
}