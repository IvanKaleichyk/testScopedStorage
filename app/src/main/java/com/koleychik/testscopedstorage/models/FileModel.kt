package com.koleychik.testscopedstorage.models

import android.net.Uri

data class FileModel(
    val id: Long,
    val title: String,
    val mediaType: String? = null,
    val uri: Uri? = null,
    val relativePath : String = ""
)