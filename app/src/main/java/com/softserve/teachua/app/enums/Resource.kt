package com.softserve.teachua.app.enums

/*
Data class made to deliver information about response and data itself
 */
data class Resource<out T>(
    val status: Status,
    val data : T?,
    val message : String?
) {

    enum class Status{
        SUCCESS, LOADING, FAILED
    }

    companion object {
        fun <T> success(data : T) : Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> loading(data : T? = null) : Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
        fun <T> error(data : T? = null, message: String? = null) : Resource<T> {
            return Resource(Status.FAILED, data, message )
        }
    }
}