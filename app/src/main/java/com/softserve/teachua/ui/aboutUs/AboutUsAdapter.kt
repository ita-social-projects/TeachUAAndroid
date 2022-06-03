package com.softserve.teachua.ui.aboutUs

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
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.data.model.AboutModel
import kotlinx.android.synthetic.main.about_item.view.*

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

            if (!model.aboutPicture.isNullOrEmpty()) {
                itemView.aboutPerPhoto.visibility = View.VISIBLE
                Glide.with(layoutInflater.context)
                    .load(baseImageUrl + model.aboutPicture)
                    .fitCenter()
                    .into(itemView.aboutPerPhoto)
            }
            else
                itemView.aboutPerPhoto.visibility = View.GONE

            if (!model.aboutText.isNullOrEmpty()) {

                itemView.aboutTitle.text = CategoryToUrlTransformer().parseHtml(model.aboutText!!)

            }
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