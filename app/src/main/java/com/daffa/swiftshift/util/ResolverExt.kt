package com.daffa.swiftshift.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import com.daffa.swiftshift.util.Constants.Empty

fun ContentResolver.getFileName(uri: Uri): String {
    val returnCursor = query(uri, null, null, null) ?: return  String.Empty
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}