package com.application.poem_poet.ui.general_navigation.poets.navigation.poets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.ItemViewListPoetsBinding
import com.application.poem_poet.model.PoemAnswer
import com.squareup.picasso.Picasso
import java.util.*

class AdapterListPoets(private var callback: (PoemAnswer) -> Unit) :
    RecyclerView.Adapter<AdapterListPoets.MyViewHolder>() {

    var dataTest = mutableListOf<PoemAnswer?>()
    var initialData = mutableListOf<PoemAnswer?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<PoemAnswer?>) {
        dataTest = data
        initialData = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_list_poets,
                parent,
                false
            ), callback
        )
    }

    override fun getItemCount(): Int = dataTest.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataTest[position]?.let { holder.bind(it) }

    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                dataTest = if (charSearch.isEmpty()) {
                    initialData
                } else {
                    val resultList: MutableList<PoemAnswer?> = mutableListOf()
                    for (row in initialData) {
                        if (row!!.titlePoem.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.namePoet.toLowerCase(
                                Locale.ROOT
                            )
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.genre.toLowerCase(
                                Locale.ROOT
                            ).contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataTest
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataTest = results?.values as MutableList<PoemAnswer?>
                notifyDataSetChanged()
            }

        }
    }

    class MyViewHolder(itemView: View, private var callback: (PoemAnswer) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding = ItemViewListPoetsBinding.bind(itemView)
        private var avatar: ImageView = binding.listPoemAvatarPoetsImg
        private var title: TextView = binding.listPoemTitleTxt
        private var name: TextView = binding.listPoemNamePoetTxt
        private var genre: TextView = binding.listPoemGenreTxt
        private var like: TextView = binding.listPoemNumberLikesTxt
        var namePoetModified = ""

        fun bind(model: PoemAnswer) {
            namePoetModified = model.namePoet.replace("|", ".", true)
            title.text = model.titlePoem
            like.text = model.like.toString()
            name.text = namePoetModified
            genre.text = model.genre
            Picasso.get()
                .load(model.avatar)
                .into(avatar)

            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}