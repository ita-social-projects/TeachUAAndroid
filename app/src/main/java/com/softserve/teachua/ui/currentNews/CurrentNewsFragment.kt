package com.softserve.teachua.ui.currentNews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.databinding.FragmentCurrentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentNewsFragment : Fragment() {

    private var _binding: FragmentCurrentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val currentNewsViewModel: CurrentNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCurrentNewsBinding.inflate(inflater, container, false)
        val view: View = binding.root
        val newsId = arguments?.getInt("id")
        Log.e("newsId", newsId.toString())
        newsId?.let { loadCurrentNews(it) }
        initViews()
        updateView()
        return view
    }

    private fun initViews() {
        currentNewsViewModel.viewModelScope.launch {
            binding.connectionProblemCurrentNews.visibility = View.GONE
            binding.progressBarCurrentNews.visibility = View.GONE

        }
    }


    private fun updateView() {
        currentNewsViewModel.viewModelScope.launch {

            currentNewsViewModel.currentNews.collectLatest { news ->

                when (news.status) {

                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        Glide.with(requireContext())
                            .load(baseImageUrl + currentNewsViewModel.currentNews.value.data?.newsUrlTitleLogo)
                            .optionalCenterCrop()
                            .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
                            .into(binding.newsPicture)

                        binding.newsTitle.text =
                            currentNewsViewModel.currentNews.value.data?.newsTitle
                        binding.newsDescription.text =
                            CategoryToUrlTransformer().parseHtml(currentNewsViewModel.currentNews.value.data?.newsDescription!!)

                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }


            }
        }

    }

    private fun showSuccess() {
        binding.contentCurrentNews.visibility = View.VISIBLE
        binding.progressBarCurrentNews.visibility = View.GONE
        binding.connectionProblemCurrentNews.visibility = View.GONE
    }

    private fun showLoading() {
        binding.contentCurrentNews.visibility = View.GONE
        binding.progressBarCurrentNews.visibility = View.VISIBLE
        binding.connectionProblemCurrentNews.visibility = View.GONE
    }

    private fun showError() {
        binding.contentCurrentNews.visibility = View.GONE
        binding.progressBarCurrentNews.visibility = View.GONE
        binding.connectionProblemCurrentNews.visibility = View.VISIBLE
    }


    private fun loadCurrentNews(newsId: Int) {

        currentNewsViewModel.load(newsId)

    }
}