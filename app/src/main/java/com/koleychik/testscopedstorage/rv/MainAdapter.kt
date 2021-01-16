package com.koleychik.testscopedstorage.rv

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.testscopedstorage.R
import com.koleychik.testscopedstorage.models.FileModel


class MainAdapter(private val list: List<FileModel>, private val onCLick : (uri: Uri?) -> Unit) :
RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: FileModel) {
            itemView.findViewById<TextView>(R.id.mainText).apply {
                text = model.title
                setOnClickListener {
                    onCLick(model.uri)
                }
            }
        }
    }

}