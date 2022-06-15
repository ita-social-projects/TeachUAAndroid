package com.softserve.teachua.ui.profile

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
import com.softserve.teachua.data.model.MessageModel
import kotlinx.android.synthetic.main.category_logo_item.view.*
import kotlinx.android.synthetic.main.message_item.view.*
import kotlinx.android.synthetic.main.message_logo_item.view.*

class MessagesAdapter(context: Context) :
    ListAdapter<MessageModel, MessagesAdapter.MessagesViewHolder>(DiffMessagesCallback()) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return MessagesViewHolder(layoutInflater
            .inflate(R.layout.message_item, parent, false))
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
//        notifyItemChanged(position)
        holder.itemView.setOnClickListener {

            when (holder.itemView.messageText.visibility){
                View.GONE -> {
                    holder.itemView.messageText.visibility = View.VISIBLE
                    holder.itemView.openMsgBtn.animate().apply {
                        duration = 200
                        rotationBy(90f)
                    }.start()

                }
                View.VISIBLE -> {
                    holder.itemView.messageText.visibility = View.GONE
                    holder.itemView.openMsgBtn.animate().apply {
                        duration = 200
                        rotationBy(-90f)
                    }.start()
                }
            }
            getItem(position).messageIsActive = false


        }
    }

    inner class MessagesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: MessageModel) {
            itemView.messageTitle.text = model.messageClub.name
            itemView.messageSender.text =
                model.messageSender.firstName.plus(" ").plus(model.messageSender.lastName)
            itemView.messageText.text = model.messageText


            Glide.with(layoutInflater.context)
                .load(baseImageUrl + model.messageSender.urlLogo)
                .optionalCircleCrop()
                .into(itemView.messageLogo)

        }
    }
}

class DiffMessagesCallback : DiffUtil.ItemCallback<MessageModel>() {

    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem == newItem
    }

}