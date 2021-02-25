package com.koleychik.testscopedstorage

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.koleychik.testscopedstorage.MainActivity.Companion.TAG
import com.koleychik.testscopedstorage.models.FileModel
import com.koleychik.testscopedstorage.models.Image
import java.io.File

typealias storeFile = MediaStore.Files.FileColumns

class ReadFiles(private val context: Context) {

    private val contentUri = "external"

    fun getFilesFromRootFolders(): List<File> {
        val file = File("storage/emulated/0")
        Log.d(TAG, "file root path = ${file.path}")
        return file.listFiles()!!.toList()
    }

    @SuppressLint("Recycle")
    fun getImages(): List<Image> {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val listRes = mutableListOf<Image>()
        val projections =
            arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.RELATIVE_PATH)
        val sorterOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        var imageId: Long

        val cursor = context.contentResolver.query(
            uriExternal,
            projections,
            null,
            null,
            sorterOrder
        ) ?: return listRes

        Log.d(MainActivity.TAG, "cursor != null")

        while (cursor.moveToNext()) {
            imageId = cursor.getLong(0)
            val uri = Uri.withAppendedPath(uriExternal, "" + imageId)
            listRes.add(Image(imageId, uri, relativePath = cursor.getString(1)))
        }
        cursor.close()
        return listRes
    }

    @SuppressLint("Recycle")
    fun getPdfFiles(): List<FileModel> {
        val selection = "_data LIKE '%.pdf' OR _data LIKE '%.jpg' AND "
//        val selection = "_data LIKE '%.jpg'"
        val projections =
            arrayOf(storeFile._ID, storeFile.DISPLAY_NAME)
        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri(contentUri),
            projections,
            selection,
            null,
            null
        ) ?: return listOf()

        val listRes = mutableListOf<FileModel>()
        while (cursor.moveToNext()) listRes.add(FileModel(cursor.getLong(0), cursor.getString(1)))
        cursor.close()
        return listRes
    }

    @SuppressLint("Recycle")
    fun getAllFiles(): List<FileModel> {
        val projections =
            arrayOf(storeFile._ID, storeFile.DISPLAY_NAME)
        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri(contentUri),
            projections,
            null,
            null,
            null
        ) ?: return listOf()

        val listRes = mutableListOf<FileModel>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val uri = Uri.withAppendedPath(MediaStore.Files.getContentUri(contentUri), "" + id)
            listRes.add(FileModel(id, cursor.getString(1), null, uri))
        }
        cursor.close()
        return listRes
    }

    fun getAllFilesAndFolders(path: String = ""): List<File> {

        val file = File("/storage/emulated/0$path")

        val listRes = mutableListOf<File>()
        for (i in file.listFiles()!!) {
            Log.d(MainActivity.TAG, "test file = $i, is file.directory - ${i.isDirectory}")
            listRes.add(i)
        }
        return listRes
    }

    fun getAllFileFromFolder(path: String): List<FileModel> {

        val selection = "relative_path = '$path'"
//        val selection = "_data LIKE '%.jpg'"
        val projections =
            arrayOf(storeFile._ID, storeFile.DISPLAY_NAME, storeFile.RELATIVE_PATH)

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri(contentUri),
            projections,
            selection,
            null,
            null
        ) ?: return listOf()

        val listRes = mutableListOf<FileModel>()
        while (cursor.moveToNext()) listRes.add(
            FileModel(
                cursor.getLong(0),
                cursor.getString(1),
                relativePath = cursor.getString(2)
            )
        )
        cursor.close()
        return listRes
    }

}