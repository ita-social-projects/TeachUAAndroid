package com.softserve.teachua.ui.challenges

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.databinding.FragmentChallengesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChallengesFragment : Fragment() {

    private var _binding: FragmentChallengesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: ChallengesAdapter
    private val challengesViewModel: ChallengesViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChallengesBinding.inflate(inflater, container, false)
        val view: View = binding.root

        initViews()
        loadData()
        updateView()


        return view
    }

    private fun updateView() {

        challengesViewModel.viewModelScope.launch {
            challengesViewModel.challenges.collectLatest { challenges ->
                when (challenges.status) {

                    Resource.Status.LOADING -> showLoading()


                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        adapter.submitList(challenges.data)
                    }

                    Resource.Status.FAILED -> showError()


                }
            }
        }
    }

    private fun loadData() {
        challengesViewModel.load()
    }

    private fun showSuccess() {
        binding.challengesList.visibility = View.VISIBLE
        binding.progressBarChallenges.visibility = View.GONE
        binding.connectionProblemChallenges.visibility = View.GONE
    }

    private fun showLoading() {
        binding.challengesList.visibility = View.GONE
        binding.progressBarChallenges.visibility = View.VISIBLE
        binding.connectionProblemChallenges.visibility = View.GONE
    }

    private fun showError() {
        binding.challengesList.visibility = View.GONE
        binding.progressBarChallenges.visibility = View.GONE
        binding.progressBarChallenges.visibility = View.VISIBLE
    }

    private fun initViews() {
        adapter = ChallengesAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.challengesList.layoutManager = layoutManager
        binding.challengesList.adapter = adapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




