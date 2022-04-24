package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.ItemViewFullInformationBinding
import com.application.poem_poet.model.PoemAnswer

class AdapterFullInformation(private var openNewActivity: (PoemAnswer) -> Unit) :
    RecyclerView.Adapter<AdapterFullInformation.MyViewHolder>() {
    private var listPoem = mutableListOf<PoemAnswer?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<PoemAnswer?>) {
        this.listPoem = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_full_information,
                parent,
                false
            ), openNewActivity
        )
    }

    override fun getItemCount(): Int = listPoem.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        listPoem[position]?.let { holder.bind(it) }
    }

    class MyViewHolder(itemView: View, private var openNewActivity: (PoemAnswer) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding = ItemViewFullInformationBinding.bind(itemView)
        private var name: TextView = binding.fullInformationTitleTxt
        private var genre: TextView = binding.fullInformationGenreTxt
        private var like: TextView = binding.fullInformationNumberLikesTxt

        fun bind(model: PoemAnswer) {
            name.text = model.titlePoem
            genre.text = model.genre
            like.text = model.like.toString()
            itemView.setOnClickListener {
                openNewActivity.invoke(model)
            }
        }
    }
}

