package com.application.poem_poet.ui.auxiliary_fragment.full_information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.ItemViewFullInformationBinding
import com.application.poem_poet.databinding.ItemViewJobUserBinding
import com.application.poem_poet.model.PoemAnswer

class AdapterFullInformation(private var callback: (PoemAnswer) -> Unit) :
    RecyclerView.Adapter<AdapterFullInformation.MyViewHolder>() {
    private var dataTest = mutableListOf<PoemAnswer?>()
    fun setData(data: MutableList<PoemAnswer?>) {
        this.dataTest = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_full_information,
                parent,
                false
            ), callback
        )
    }


    override fun getItemCount(): Int = dataTest.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataTest[position]?.let { holder.bind(it) }
    }

    class MyViewHolder(itemView: View, private var callback: (PoemAnswer) -> Unit) :
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
                callback.invoke(model)
            }

        }
    }
}

