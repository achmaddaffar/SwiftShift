package com.daffa.swiftshift.presentation.features.search

sealed class SearchEvent {
    data class EnteredSearchQuery(val value: String) : SearchEvent()
}
