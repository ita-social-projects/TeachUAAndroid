package com.softserve.teachua.ui.news

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
import com.softserve.teachua.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view: View = binding.root

        initViews()
        loadData()
        updateView()


        return view
    }

    private fun updateView() {

        newsViewModel.viewModelScope.launch {
            newsViewModel.news.collectLatest { news ->
                when (news.status) {

                    Resource.Status.LOADING -> showLoading()


                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        adapter.submitList(news.data)
                    }

                    Resource.Status.FAILED -> showError()


                }
            }
        }
    }

    private fun loadData() {
        newsViewModel.load()
    }

    private fun showSuccess() {
        binding.newsList.visibility = View.VISIBLE
        binding.progressBarNews.visibility = View.GONE
        binding.connectionProblemNews.visibility = View.GONE
    }

    private fun showLoading() {
        binding.newsList.visibility = View.GONE
        binding.progressBarNews.visibility = View.VISIBLE
        binding.connectionProblemNews.visibility = View.GONE
    }

    private fun showError() {
        binding.newsList.visibility = View.GONE
        binding.progressBarNews.visibility = View.GONE
        binding.progressBarNews.visibility = View.VISIBLE
    }

    private fun initViews() {
        adapter = NewsAdapter(requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.newsList.layoutManager = layoutManager
        binding.newsList.adapter = adapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}