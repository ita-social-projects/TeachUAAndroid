package com.softserve.teachua.ui.clubs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softserve.teachua.databinding.ItemLoadingClubBinding

class ClubsLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<ClubsLoadStateAdapter.ClubLoadStateViewHolder>() {

    class ClubLoadStateViewHolder(
        private val binding: ItemLoadingClubBinding,
        private val retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }

            binding.progressbar.isVisible = (loadState is LoadState.Error)
            binding.buttonRetry.isVisible = (loadState is LoadState.Error)
            binding.textViewError.isVisible = (loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }

            binding.progressbar.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: ClubLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ) = ClubLoadStateViewHolder(
        ItemLoadingClubBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}