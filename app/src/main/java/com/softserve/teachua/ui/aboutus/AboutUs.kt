package com.softserve.teachua.ui.aboutus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseAboutUsImg
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.databinding.AboutUsFragmentBinding
import com.softserve.teachua.databinding.FragmentChallengesBinding
import com.softserve.teachua.ui.challenges.ChallengesAdapter
import com.softserve.teachua.ui.challenges.ChallengesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AboutUs : Fragment() {

    private var _binding: AboutUsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: AboutUsAdapter
    private val aboutUsViewModel: AboutUsViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = AboutUsFragmentBinding.inflate(inflater, container, false)
        val view: View = binding.root

        initViews()
        loadData()
        updateView()


        return view
    }

    private fun updateView() {

        aboutUsViewModel.viewModelScope.launch {
            aboutUsViewModel.about.collectLatest { abouts ->
                when (abouts.status) {

                    Resource.Status.LOADING -> showLoading()


                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        adapter.submitList(abouts.data)
                    }

                    Resource.Status.FAILED -> showError()


                }
            }
        }
    }

    private fun loadData() {
        aboutUsViewModel.load()
    }

    private fun showSuccess() {
        binding.aboutContainer.visibility = View.VISIBLE
        binding.progressBarAbout.visibility = View.GONE
        binding.connectionProblemAbout.visibility = View.GONE
    }

    private fun showLoading() {
        binding.aboutContainer.visibility = View.GONE
        binding.progressBarAbout.visibility = View.VISIBLE
        binding.connectionProblemAbout.visibility = View.GONE
    }

    private fun showError() {
        binding.aboutContainer.visibility = View.GONE
        binding.progressBarAbout.visibility = View.GONE
        binding.progressBarAbout.visibility = View.VISIBLE
    }

    private fun initViews() {
        adapter = AboutUsAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.aboutList.layoutManager = layoutManager
        binding.aboutList.adapter = adapter
        Glide.with(requireContext())
            .load(baseImageUrl + baseAboutUsImg)
            .optionalCenterCrop()
            .into(binding.aboutPicture)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}