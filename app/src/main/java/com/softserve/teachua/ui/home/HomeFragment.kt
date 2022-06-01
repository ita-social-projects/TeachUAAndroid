package com.softserve.teachua.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.softserve.teachua.R
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.HorizontalDecorator
import com.softserve.teachua.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: CategoriesAdapter
    private lateinit var bans: ImageSlider

    private lateinit var connectionProblemTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var content: ScrollView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()
        loadData()
        updateView()
        return root
    }

    private fun updateView() {
        homeViewModel.viewModelScope.launch {
            homeViewModel.banners.collectLatest {
                when (homeViewModel.banners.value.status) {
                    Resource.Status.SUCCESS -> {
                        updateBanners()
                        homeViewModel.viewModelScope.launch {
                            homeViewModel.categories.collectLatest { categories ->
                                when (homeViewModel.categories.value.status) {
                                    Resource.Status.SUCCESS -> {
                                        showSuccess()
                                        adapter.submitList(categories.data)
                                    }
                                    Resource.Status.LOADING -> showLoading()
                                    Resource.Status.FAILED -> showError()

                                }
                            }
                        }
                    }
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }
            }
        }

    }

    private fun loadData() {
        homeViewModel.loadData()
    }

    private fun showSuccess() {
        content.visibility = View.VISIBLE
        progressBar.visibility = GONE
        connectionProblemTextView.visibility = GONE
    }

    private fun showLoading() {
        content.visibility = GONE
        progressBar.visibility = View.VISIBLE
        connectionProblemTextView.visibility = GONE
    }

    private fun showError() {
        content.visibility = GONE
        progressBar.visibility = GONE
        connectionProblemTextView.visibility = View.VISIBLE
    }

    private fun initViews() {
        bans = binding.imageSlider
        adapter = CategoriesAdapter(requireContext())
        progressBar = binding.progressBarHome
        connectionProblemTextView = binding.connectionProblemHome
        content = binding.contentHome
        Glide.with(requireContext())
            .load(homeViewModel.staticMainImg)
            .into(binding.challengeImg)
        initCategories()
    }

    private fun initCategories() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.categoriesList.layoutManager = layoutManager
        binding.categoriesList.adapter = adapter
//        val snapHelper = SimpleSnapHelper(binding.viewOverflowPagerIndicator)
//        snapHelper.attachToRecyclerView(binding.categoriesList)
        PagerSnapHelper().attachToRecyclerView(binding.categoriesList)
        binding.categoriesList.addItemDecoration(HorizontalDecorator())

    }

    private fun updateBanners() {
        bans.setImageList(homeViewModel.bansList, ScaleTypes.CENTER_CROP)
        bans.setItemClickListener(object : ItemClickListener {

            override fun onItemSelected(position: Int) {
                val link = homeViewModel.banners.value.data!![position].bannerLink
                view?.let {
                    if (link != null) {
                        if (link.contains("/clubs")) {

                            Navigation.findNavController(it)
                                .navigate(R.id.action_nav_home_to_nav_clubs)
                        } else if (link.contains("/challenges")) {
                            val substring = link.substring("/challenges".length + 1)
                            val id = substring.toInt()
                            val bundle = bundleOf("id" to id)
                            Navigation.findNavController(it)
                                .navigate(R.id.action_nav_home_to_challengeFragment, bundle)
                        }
                    }

                }
            }

        })
    }


}