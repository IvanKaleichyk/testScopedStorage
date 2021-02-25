package com.koleychik.testscopedstorage

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MainActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy {
        findViewById(R.id.img)
    }

    companion object {
        const val TAG = "MAIN_APP_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPermission()

        test()

//        add into manifest -> android:requestLegacyExternalStorage="true"

        findViewById<Button>(R.id.btnSelectMediaType).setOnClickListener {
            selectMediaType()
        }

        findViewById<Button>(R.id.btnLoadFirstImage).setOnClickListener {
            loadFirstImage()
        }

        findViewById<Button>(R.id.btnGoToTestOpen).setOnClickListener {
            startActivity(Intent(applicationContext, TestOpenFileActivity::class.java))
        }
    }

    private fun test() {
        val readFiles = ReadFiles(this)

        val folders = readFiles.getFilesFromRootFolders()

        for (i in folders) {
            Log.d(TAG, "child of root folder path = ${i.path}")
        }

//        val list = readFiles.getAllFilesAndFolders()
//        if (list.isEmpty()) Log.d(TAG, "list is empty")
//        for (i in list) {

//        Log.d(TAG, "i.name = ${i.name}")
//            val relativePath = i.path.replace("/storage/emulated/0/", "") + "/"
//            Log.d(TAG, "relativePath = ${relativePath}")
//            val files = readFiles.getAllFileFromFolder(relativePath)
//            for (j in files) Log.d(TAG, "file.name = ${j.title}, file.uri = ${j.uri}")


//        val list = readFiles.getAllFiles()
//        if (list.isEmpty()) Log.d(TAG, "list.isEmpty()")
//        //        for (i in list) Log.d(TAG, "file = $i")
//
//
//        val listPdf = readFiles.getPdfFiles()
//        if (listPdf.isEmpty()) Log.d(TAG, "list pdf is empty")
//        for (i in listPdf) Log.d(TAG, "file = $i")

//        val listPdf = readFiles.getPdfFiles()
//        if (listPdf.isEmpty()) Log.d(TAG, "list with pdf is empty")
////        e;s
//        for (i in listPdf) Log.d(TAG, "pdf.title = ${i.title}")
    }

    private fun getPermission() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {}

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
            }
        }).check()
    }

    private fun loadFirstImage() {
        val readFiles = ReadFiles(this)
        Log.d(TAG, "start loadFirstImage")
        val list = readFiles.getImages()
        if (list.isEmpty()) {
            showToast("haven't images on device")
            Log.d(TAG, "haven't images on device")
            return
        } else {
            Log.d(TAG, "relativePath = ${list[0].relativePath}")
            showToast("images on device")
            Log.d(TAG, "images on device")
        }
        imageView.load(list[0].uri)
    }

    private fun selectMediaType() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            try {

                showToast(data?.data.toString())

            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        } else showToast("requestCode != 0")
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

}