package com.softserve.teachua.ui.task

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
import com.softserve.teachua.data.dto.TaskDto
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(context: Context) :
    ListAdapter<TaskDto, TasksAdapter.TasksViewHolder>(DiffTasksCallback()) {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(layoutInflater
            .inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.setOnClickListener {

        }
            val bundle = Bundle()
            bundle.putInt("taskId", getItem(position).id)

            val navBuilder = NavOptions.Builder()
            navBuilder
                .setExitAnim(android.R.anim.fade_out)
                .setEnterAnim(android.R.anim.fade_in)
                .setPopExitAnim(android.R.anim.fade_in)
                .setPopEnterAnim(android.R.anim.fade_out)

            holder.itemView.setOnClickListener { view ->
                view.findNavController().navigate(R.id.taskFragment, bundle, navBuilder.build())
                println("item at pos" + getItem(position).id)
        }
    }

    inner class TasksViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: TaskDto) {
            itemView.taskTitle.text = model.name

            Glide.with(layoutInflater.context)
                .load(baseImageUrl + model.picture)
                .optionalCenterCrop()
                .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
                .into(itemView.taskLogo)


        }
    }
}

class DiffTasksCallback() : DiffUtil.ItemCallback<TaskDto>() {

    override fun areItemsTheSame(oldItem: TaskDto, newItem: TaskDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskDto, newItem: TaskDto): Boolean {
        return oldItem == newItem
    }

}
