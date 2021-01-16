package com.koleychik.testscopedstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.testscopedstorage.rv.MainAdapter

class TestOpenFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_open_file)

        val readFiles = ReadFiles(this)

        val adapter = MainAdapter(readFiles.getAllFiles()){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = it
            val j = Intent.createChooser(intent, "Choose an application to open with:")
            startActivity(j)
        }

        findViewById<RecyclerView>(R.id.rv).adapter = adapter
    }
}