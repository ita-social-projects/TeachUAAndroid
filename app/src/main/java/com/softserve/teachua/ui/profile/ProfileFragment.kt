package com.softserve.teachua.ui.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.enums.Role
import com.softserve.teachua.app.profileBackground
import com.softserve.teachua.databinding.FragmentProfileBinding
import com.softserve.teachua.ui.clubs.ClubsAdapter
import com.softserve.teachua.ui.news.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var clubAdapter: ClubsAdapter
    private lateinit var messagesAdapter: MessagesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view: View = binding.root


        loadData()
        loadMessages()
        initViews()
        updateView()


        return view
    }

    private fun initViews() {


        Glide.with(requireContext())
            .load(baseImageUrl + profileBackground)
            .centerCrop()
            .into(object :
                CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?,
                ) {
                    binding.profileContent.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })

        clubAdapter = ClubsAdapter(requireContext(), true)
        val clubLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.profileClubsList.layoutManager = clubLayoutManager
        binding.profileClubsList.adapter = clubAdapter.withLoadStateFooter(
            footer = EditProfileClubsLoadStateAdapter { clubAdapter.retry() })
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.nav_edit_profile)
        }

        messagesAdapter = MessagesAdapter(requireContext())
        val messagesLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.profileMessagesList.layoutManager = messagesLayoutManager
        binding.profileMessagesList.adapter = messagesAdapter
    }


    private fun updateView() {
        profileViewModel.viewModelScope.launch {
            profileViewModel.currentUserProfile.collectLatest { profile ->
                when (profileViewModel.currentUserProfile.value.status) {
                    Resource.Status.SUCCESS -> {

                        profileViewModel.viewModelScope.launch {
                            profileViewModel.profileClubs.collectLatest { profileClubs ->
                                clubAdapter.submitData(profileClubs)

                            }

                        }



                        Glide.with(requireContext())
                            .load(baseImageUrl + profile.data?.urlLogo)
                            .optionalCircleCrop()
                            .into(binding.profilePhoto)

                        binding.profileName.text =
                            profile.data?.firstName?.plus(" ")?.plus(profile.data.lastName)

                        binding.profileRole.text = Role().getUaRoleName(profile.data?.roleName!!)
                        binding.profilePhoneNumber.text = profile.data.phone
                        binding.profileEmailAddress.text = profile.data.email

                        profileViewModel.viewModelScope.launch {
                            profileViewModel.messages.collectLatest { messages ->
                                when(messages.status){

                                    Resource.Status.SUCCESS -> {
                                        showSuccess()
                                        messagesAdapter.submitList(messages.data)
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
        profileViewModel.loadUser()
    }

    private fun loadMessages() {
        profileViewModel.loadMessages()
    }

    private fun showSuccess() {
        binding.profileContent.visibility = View.VISIBLE
        binding.progressBarProfile.visibility = View.GONE
        binding.connectionProblemProfile.visibility = View.GONE
    }

    private fun showLoading() {
        binding.profileContent.visibility = View.GONE
        binding.progressBarProfile.visibility = View.VISIBLE
        binding.connectionProblemProfile.visibility = View.GONE
    }

    private fun showError() {
        binding.profileContent.visibility = View.GONE
        binding.progressBarProfile.visibility = View.GONE
        binding.connectionProblemProfile.visibility = View.VISIBLE
        binding.connectionProblemProfile.text = "Problems..."
    }

    private fun whenLoadingClubs() {
        binding.progressBarProfile.visibility = View.VISIBLE
        binding.connectionProblemProfile.isVisible = false
        binding.profileClubsList.visibility = View.GONE

    }

    private fun whenErrorLoadingClubs() {

        binding.progressBarProfile.visibility = View.GONE
        binding.profileClubsList.isInvisible = true
        binding.connectionProblemProfile.isVisible = true
    }

    private fun whenDataIsClear() {
        println("Data Is Clear")
        binding.progressBarProfile.visibility = View.GONE
        binding.profileClubsList.isInvisible = true
        binding.connectionProblemProfile.isVisible = true

    }

    private fun whenDataIsLoaded() {
        binding.progressBarProfile.visibility = View.GONE
        binding.connectionProblemProfile.visibility = View.GONE
        binding.profileClubsList.visibility = View.VISIBLE

    }

    private fun initAdapterState() {
        lifecycleScope.launch {
            clubAdapter.loadStateFlow.collectLatest { loadStates ->


                when (loadStates.refresh) {

                    is LoadState.Loading -> {

                        println("Loading Case")
                        whenLoadingClubs()
                    }

                    is LoadState.Error -> {
                        println("Error Case")
                        whenErrorLoadingClubs()
                    }


                    else -> {
                        println("Else Case")
                        whenDataIsLoaded()

                        if (clubAdapter.itemCount < 1) {
                            whenDataIsClear()
                            println("clubs ada" + clubAdapter.itemCount)
                        }


                    }
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        initAdapterState()
    }
}