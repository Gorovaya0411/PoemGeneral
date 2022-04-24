package com.application.poem_poet.ui.general_navigation.poets.navigation.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.ItemViewListUsersBinding
import com.application.poem_poet.model.PoemAnswer
import com.squareup.picasso.Picasso
import java.util.*

class AdapterListUser(private var openFragment: (PoemAnswer) -> Unit) :
    RecyclerView.Adapter<AdapterListUser.MyViewHolder>() {
    var listUser = mutableListOf<PoemAnswer?>()
    var initialUser = mutableListOf<PoemAnswer?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<PoemAnswer?>) {
        this.listUser = data
        initialUser = data
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listUser = if (charSearch.isEmpty()) {
                    initialUser
                } else {
                    val resultList: MutableList<PoemAnswer?> = mutableListOf()
                    for (row in initialUser) {
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
                filterResults.values = listUser
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listUser = results?.values as MutableList<PoemAnswer?>
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_list_users,
                parent,
                false
            ), openFragment
        )
    }

    override fun getItemCount(): Int = listUser.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        listUser[position]?.let { holder.bind(it) }
    }

    class MyViewHolder(itemView: View, private var openNewActivity: (PoemAnswer) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding = ItemViewListUsersBinding.bind(itemView)
        private var avatar: ImageView = binding.listUsersAvatarUsersImg
        private var title: TextView = binding.listUsersTitleTxt
        private var name: TextView = binding.listUsersNameUserTxt
        private var genre: TextView = binding.listUsersGenreTxt
        private var nameUserModified = ""
        private var like: TextView = binding.listUsersNumberLikesTxt

        fun bind(model: PoemAnswer) {
            nameUserModified = model.username.replace("|", ".", true)
            title.text = model.titlePoem
            name.text = nameUserModified
            genre.text = model.genre
            like.text = model.like.toString()
            Picasso.get()
                .load(model.avatar)
                .into(avatar)

            itemView.setOnClickListener {
                openNewActivity.invoke(model)
            }
        }
    }
}