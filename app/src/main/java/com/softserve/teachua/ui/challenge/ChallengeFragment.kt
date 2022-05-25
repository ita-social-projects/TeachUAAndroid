package com.softserve.teachua.ui.challenge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.softserve.teachua.R
import com.softserve.teachua.ui.task.TasksAdapter
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.baseMailImage
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.databinding.ChallengeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChallengeFragment : Fragment() {

    private var _binding: ChallengeFragmentBinding? = null
    private val binding get() = _binding!!
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private lateinit var adapter: TasksAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = ChallengeFragmentBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val clubId = arguments?.getInt("id")
        Log.e("clubId", clubId.toString())
        clubId?.let { loadChallenge(it) }
        initViews()
        updateView()
        return view
    }

    private fun initViews() {
        challengeViewModel.viewModelScope.launch {
            adapter = TasksAdapter(requireContext())
            binding.connectionProblemChallenge.visibility = View.GONE
            binding.progressBarChallenge.visibility = View.GONE
            val layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.taskList.layoutManager = layoutManager
            binding.taskList.adapter = adapter
            initContacts()


        }
    }


    private fun updateView() {
        challengeViewModel.viewModelScope.launch {

            challengeViewModel.challenge.collectLatest { challenge ->

                when (challenge.status) {

                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        Glide.with(requireContext())
                            .load(baseImageUrl + challengeViewModel.challenge.value.data?.picture)
                            .optionalCenterCrop()
                            .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
                            .into(binding.challengePicture)

                        binding.challengeName.text = challengeViewModel.challenge.value.data?.title
                        binding.challengeDescription.text =
                            CategoryToUrlTransformer().parseHtml(challengeViewModel.challenge.value.data?.description!!)

                        adapter.submitList(challenge.data?.tasks)
                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }


            }
        }

    }

    private fun showSuccess() {
        binding.contentChallenge.visibility = View.VISIBLE
        binding.progressBarChallenge.visibility = View.GONE
        binding.connectionProblemChallenge.visibility = View.GONE
    }

    private fun showLoading() {
        binding.contentChallenge.visibility = View.GONE
        binding.progressBarChallenge.visibility = View.VISIBLE
        binding.connectionProblemChallenge.visibility = View.GONE
    }

    private fun showError() {
        binding.contentChallenge.visibility = View.GONE
        binding.progressBarChallenge.visibility = View.GONE
        binding.connectionProblemChallenge.visibility = View.VISIBLE
    }

    private fun initContacts() {


        GlideToVectorYou
            .init()
            .with(layoutInflater.context)
            .setPlaceHolder(
                R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background)

            .load((baseImageUrl + baseMailImage).toUri(), binding.mailBtn)
    }


    private fun loadChallenge(clubId: Int) {

        challengeViewModel.load(clubId)

    }
}


