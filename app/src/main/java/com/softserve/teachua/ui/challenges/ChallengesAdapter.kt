package com.softserve.teachua.ui.challenges

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
import com.softserve.teachua.data.model.ChallengeModel
import kotlinx.android.synthetic.main.challenge_item.view.*

class ChallengesAdapter(context: Context) :
    ListAdapter<ChallengeModel, ChallengesAdapter.ChallengeViewHolder>(DiffChallengesCallback()) {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder(layoutInflater
            .inflate(R.layout.challenge_item, parent, false))
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        val bundle = Bundle()
        bundle.putInt("id", getItem(position).id)

        val navBuilder = NavOptions.Builder()
        navBuilder
            .setExitAnim(android.R.anim.fade_out)
            .setEnterAnim(android.R.anim.fade_in)
            .setPopExitAnim(android.R.anim.fade_in)
            .setPopEnterAnim(android.R.anim.fade_out)

        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.challengeFragment, bundle, navBuilder.build())
            println("item at pos" + getItem(position).id)
        }
    }

    inner class ChallengeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(model: ChallengeModel) {
            itemView.challengeTitle.text = model.name


//            if (model.picture.endsWith(".svg"))
//                GlideToVectorYou
//                    .init()
//                    .with(layoutInflater.context)
//                    .load((baseImageUrl + model.categoryUrlLogo).toUri(), itemView.categoryLogo)
//            itemView.categoryBackground.setCardBackgroundColor(Color.parseColor(model.categoryBackgroundColor))


        }
    }
}

class DiffChallengesCallback : DiffUtil.ItemCallback<ChallengeModel>() {

    override fun areItemsTheSame(oldItem: ChallengeModel, newItem: ChallengeModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChallengeModel, newItem: ChallengeModel): Boolean {
        return oldItem == newItem
    }

}