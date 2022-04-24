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

class AdapterListPoets(private var openFragment: (PoemAnswer) -> Unit) :
    RecyclerView.Adapter<AdapterListPoets.MyViewHolder>() {

    var listPoem = mutableListOf<PoemAnswer?>()
    var initialPoem = mutableListOf<PoemAnswer?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<PoemAnswer?>) {
        listPoem = data
        initialPoem = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_list_poets,
                parent,
                false
            ), openFragment
        )
    }

    override fun getItemCount(): Int = listPoem.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        listPoem[position]?.let { holder.bind(it) }
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listPoem = if (charSearch.isEmpty()) {
                    initialPoem
                } else {
                    val resultList: MutableList<PoemAnswer?> = mutableListOf()
                    for (row in initialPoem) {
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
                filterResults.values = listPoem
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listPoem = results?.values as MutableList<PoemAnswer?>
                notifyDataSetChanged()
            }
        }
    }

    class MyViewHolder(itemView: View, private var openNewActivity: (PoemAnswer) -> Unit) :
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
                openNewActivity.invoke(model)
            }
        }
    }
}