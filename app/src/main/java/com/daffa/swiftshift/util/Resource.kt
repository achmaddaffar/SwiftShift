package com.daffa.swiftshift.util

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Loading<T>(data: T? = null) : Resource<T>()
    class Error<T>(uiText: UiText, data: T? = null) : Resource<T>(data, uiText)
    class Success<T>(data: T?) : Resource<T>(data)
}
