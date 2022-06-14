package com.softserve.teachua.ui.clubs

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.tools.GsonDeserializer
import com.softserve.teachua.data.dto.ClubDescriptionText
import com.softserve.teachua.data.model.ClubModel
import kotlinx.android.synthetic.main.category_logo_item.view.*
import kotlinx.android.synthetic.main.club_item.view.*
import kotlin.math.roundToInt

class ClubsAdapter(context: Context, var isMyProfile: Boolean) :
    PagingDataAdapter<ClubModel, ClubsAdapter.ClubsViewHolder>(ClubDiffItemCallback),
    View.OnClickListener {


    private lateinit var con: Context

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubsViewHolder {

        con = parent.context
        return if (isMyProfile){
            ClubsViewHolder(layoutInflater.inflate(R.layout.profile_item, parent, false))
        } else
            ClubsViewHolder(layoutInflater.inflate(R.layout.club_item, parent, false))

    }

    override fun onBindViewHolder(holder: ClubsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
        val desc = GsonDeserializer().deserialize(
            getItem(position)?.clubDescription,
            ClubDescriptionText::class.java
        )

        val bundle = Bundle()
        bundle.putInt("clubId", getItem(position)?.clubId!!)
        bundle.putString("clubName", getItem(position)?.clubName)
        for(contact in getItem(position)!!.clubContacts){
            if (contact.contactType.name != "Телефон")
                bundle.putString("clubLink", contact.contactData)
        }

        if(!isMyProfile)
            bundle.putString("clubLink", getItem(position)!!.clubContacts[0].contactData)

        when (desc.blocks.size) {

            4 -> {
                bundle.putString("clubDescription", desc.blocks[3].text)
            }
            else -> {
                bundle.putString("clubDescription", desc.blocks[0].text)
            }
        }
        bundle.putString("clubPicture", getItem(position)?.clubBanner)
        val navBuilder = NavOptions.Builder()
        navBuilder
            .setExitAnim(android.R.anim.fade_out)
            .setEnterAnim(android.R.anim.fade_in)
            .setPopExitAnim(android.R.anim.fade_in)
            .setPopEnterAnim(android.R.anim.fade_out)

        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(
                R.id.nav_club,
                bundle,
                navBuilder.build())
        }
    }


    lateinit var mClickListener: ClickListener

    fun setOnClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }


    inner class ClubsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(model: ClubModel) {
            itemView.clubTitle.text = model.clubName

            val desc = GsonDeserializer().deserialize(model.clubDescription,
                ClubDescriptionText::class.java)
            when (desc.blocks.size) {
                4 -> {

                    itemView.clubDescription.text = desc.blocks[3].text
                }
                3 -> {

                    itemView.clubDescription.text = desc.blocks[0].text
                }
                else -> itemView.clubDescription.text = desc.blocks[0].text
            }

            if (model.clubImage.endsWith(".png")) {
                //println(model!!.clubImage.endsWith(".png")
                //picasso.load(base + model.clubImage).fit().centerCrop().into(itemView.clubLogo)
            } else if (model.clubImage.endsWith(".svg"))
                GlideToVectorYou
                    .init()
                    .with(layoutInflater.context)
                    .load((baseImageUrl + model.clubImage).toUri(), itemView.categoryLogo)


            itemView.clubCategory.text = model.clubCategoryName
            itemView.clubCategory.setBackgroundColor(Color.parseColor(model.clubBackgroundColor))
            itemView.categoryBackground.background.setTint(Color.parseColor(model.clubBackgroundColor))
            println("rate" + model.clubRating)
            itemView.clubRating.rating = model.clubRating.roundToInt().toFloat()

        }


        override fun onClick(p0: View?) {
            mClickListener.onClick(absoluteAdapterPosition, itemView)
        }

    }

    override fun onClick(p0: View?) {
    }


}

private object ClubDiffItemCallback : DiffUtil.ItemCallback<ClubModel>() {
    override fun areItemsTheSame(oldItem: ClubModel, newItem: ClubModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ClubModel, newItem: ClubModel): Boolean {
        return oldItem.clubId == newItem.clubId
    }


}
