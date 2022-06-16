package com.softserve.teachua.ui.club

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.data.model.FeedbackModel
import kotlinx.android.synthetic.main.feedback_item.view.*
import kotlinx.android.synthetic.main.message_logo_item.view.*

class FeedbacksAdapter(context: Context) :
    ListAdapter<FeedbackModel, FeedbacksAdapter.FeedbacksViewHolder>(DiffFeedbacksCallback()) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbacksViewHolder {
        return FeedbacksViewHolder(layoutInflater
            .inflate(R.layout.feedback_item, parent, false))
    }

    override fun onBindViewHolder(holder: FeedbacksViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class FeedbacksViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: FeedbackModel) {
            itemView.feedbackSender.text =
                model.feedBackUser.firstName.plus(" ").plus(model.feedBackUser.lastName)
            itemView.feedbackText.text = model.feedBackText

            Glide.with(layoutInflater.context)
                .load(baseImageUrl + model.feedBackUser.urlLogo)
                .optionalCircleCrop()
                .into(itemView.messageLogo)

        }
    }
}

class DiffFeedbacksCallback : DiffUtil.ItemCallback<FeedbackModel>() {

    override fun areItemsTheSame(oldItem: FeedbackModel, newItem: FeedbackModel): Boolean {
        return oldItem.feedBackId == newItem.feedBackId
    }

    override fun areContentsTheSame(oldItem: FeedbackModel, newItem: FeedbackModel): Boolean {
        return oldItem == newItem
    }

}