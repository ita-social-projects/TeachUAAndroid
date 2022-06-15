package com.softserve.teachua.ui.news

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
import com.softserve.teachua.data.model.ChallengeModel
import com.softserve.teachua.data.model.NewsModel
import com.softserve.teachua.ui.challenges.ChallengesAdapter
import com.softserve.teachua.ui.challenges.DiffChallengesCallback
import kotlinx.android.synthetic.main.challenge_item.view.*
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(context: Context) :
    ListAdapter<NewsModel, NewsAdapter.NewsViewHolder>(DiffNewsCallback()) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(layoutInflater
            .inflate(R.layout.news_item, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        val bundle = Bundle()
        bundle.putInt("id", getItem(position).newsId)

        val navBuilder = NavOptions.Builder()
        navBuilder
            .setExitAnim(android.R.anim.fade_out)
            .setEnterAnim(android.R.anim.fade_in)
            .setPopExitAnim(android.R.anim.fade_in)
            .setPopEnterAnim(android.R.anim.fade_out)

        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.nav_current_news, bundle, navBuilder.build())
            println("item at pos" + getItem(position).newsId)
        }
    }

    inner class NewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: NewsModel) {
            itemView.newsTitle.text = model.newsTitle


            Glide.with(layoutInflater.context)
                .load(baseImageUrl + model.newsUrlTitleLogo)
                .optionalCircleCrop()
                .into(itemView.newsLogo)

        }
    }
}

class DiffNewsCallback : DiffUtil.ItemCallback<NewsModel>() {

    override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem == newItem
    }

}