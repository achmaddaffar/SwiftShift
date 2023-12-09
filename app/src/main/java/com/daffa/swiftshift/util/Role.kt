package com.daffa.swiftshift.util

sealed class Role {
    data object GigWorker : Role()
    data object GigProvider : Role()
}
