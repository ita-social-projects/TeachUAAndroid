package com.softserve.teachua.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.databinding.TaskFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private var _binding: TaskFragmentBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by viewModels()
    private var taskId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = TaskFragmentBinding.inflate(inflater, container, false)
        val view: View = binding.root
        taskId = arguments?.getInt("taskId")!!
        loadData()
        initViews()
        return view
    }

    private fun loadData() {
        taskViewModel.load(taskId)
    }

    private fun initViews() {

        taskViewModel.viewModelScope.launch {

            taskViewModel.task.collectLatest { task ->

                when (task.status) {

                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        Glide.with(requireContext())
                            .load(baseImageUrl + task.data?.picture)
                            .optionalCenterCrop()
                            .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
                            .into(binding.taskPicture)

                        binding.taskTitle.text = task.data?.name
                        binding.taskHeader.text = CategoryToUrlTransformer().parseHtml(task.data?.headerText!!)
                        binding.taskDescription.text = CategoryToUrlTransformer().parseHtml(task.data.description)
                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }

            }

        }

    }

    private fun showSuccess() {
        binding.contentTask.visibility = View.VISIBLE
        binding.progressBarTask.visibility = View.GONE
        binding.connectionProblemTask.visibility = View.GONE
    }

    private fun showLoading() {
        binding.contentTask.visibility = View.GONE
        binding.progressBarTask.visibility = View.VISIBLE
        binding.connectionProblemTask.visibility = View.GONE
    }

    private fun showError() {
        binding.contentTask.visibility = View.GONE
        binding.progressBarTask.visibility = View.GONE
        binding.connectionProblemTask.visibility = View.VISIBLE
    }


}