package com.softserve.teachua.ui.aboutus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softserve.teachua.R
import com.softserve.teachua.data.model.AboutModel
import kotlinx.android.synthetic.main.about_item.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class AboutUsAdapter(context: Context) :
    ListAdapter<AboutModel, AboutUsAdapter.AboutUsViewHolder>(DiffAboutCallback()) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsViewHolder {
        return AboutUsViewHolder(layoutInflater
            .inflate(R.layout.about_item, parent, false))
    }

    override fun onBindViewHolder(holder: AboutUsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        val bundle = Bundle()
        bundle.putInt("id", getItem(position).aboutId)

        val navBuilder = NavOptions.Builder()
        navBuilder
            .setExitAnim(android.R.anim.fade_out)
            .setEnterAnim(android.R.anim.fade_in)
            .setPopExitAnim(android.R.anim.fade_in)
            .setPopEnterAnim(android.R.anim.fade_out)

        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.challengeFragment, bundle, navBuilder.build())
            println("item at pos" + getItem(position).aboutId)
        }
    }

    inner class AboutUsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: AboutModel) {
            itemView.aboutTitle.text = model.aboutText


//            if (model.picture.endsWith(".svg"))
//                GlideToVectorYou
//                    .init()
//                    .with(layoutInflater.context)
//                    .load((baseImageUrl + model.categoryUrlLogo).toUri(), itemView.categoryLogo)
//            itemView.categoryBackground.setCardBackgroundColor(Color.parseColor(model.categoryBackgroundColor))


        }
    }
}

class DiffAboutCallback : DiffUtil.ItemCallback<AboutModel>() {

    override fun areItemsTheSame(oldItem: AboutModel, newItem: AboutModel): Boolean {
        return oldItem.aboutId == newItem.aboutId
    }

    override fun areContentsTheSame(oldItem: AboutModel, newItem: AboutModel): Boolean {
        return oldItem == newItem
    }

}