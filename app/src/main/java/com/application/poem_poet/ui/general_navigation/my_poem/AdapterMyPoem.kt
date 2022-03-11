package com.application.poem_poet.ui.general_navigation.my_poem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.poem_poet.R
import com.application.poem_poet.databinding.ItemViewMyPoemBinding
import com.application.poem_poet.model.PoemAnswer
import com.squareup.picasso.Picasso

class AdapterMyPoem(private var callback: (PoemAnswer, Int) -> Unit) :
    RecyclerView.Adapter<AdapterMyPoem.MyViewHolder>() {
    var dataTest = mutableListOf<PoemAnswer?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<PoemAnswer?>) {
        this.dataTest = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_my_poem,
                parent,
                false
            ), callback
        )
    }

    override fun getItemCount(): Int = dataTest.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dataTest[position]?.let { holder.bind(it) }
    }

    class MyViewHolder(itemView: View, private var callback: (PoemAnswer, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        var binding = ItemViewMyPoemBinding.bind(itemView)
        private var avatar: ImageView = binding.myPoemAvatarUsersImg
        private var title: TextView = binding.myPoemTitleTxt
        private var name: TextView = binding.myPoemNameUserTxt
        private var genre: TextView = binding.myPoemGenreTxt
        private var like: TextView = binding.myPoemNumberLikesTxt
        private var nameModified = ""

        fun bind(model: PoemAnswer) {
            if (model.namePoet == "") {
                nameModified = model.username.replace("|", ".", true)
                name.text = nameModified
            } else {
                nameModified = model.namePoet.replace("|", ".", true)
                name.text = nameModified
            }
            title.text = model.titlePoem
            genre.text = model.genre
            like.text = model.like.toString()
            Picasso.get()
                .load(model.avatar)
                .into(avatar)



            itemView.setOnClickListener {
                callback.invoke(model, 1)
            }
            itemView.setOnLongClickListener {
                callback.invoke(model, 2)
                return@setOnLongClickListener true
            }
        }
    }
}