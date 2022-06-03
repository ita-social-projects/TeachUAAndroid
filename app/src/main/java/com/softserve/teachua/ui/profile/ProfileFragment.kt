package com.softserve.teachua.ui.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.enums.Role
import com.softserve.teachua.app.profileBackground
import com.softserve.teachua.databinding.FragmentProfileBinding
import com.softserve.teachua.ui.clubs.ClubsAdapter
import com.softserve.teachua.ui.home.CategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.logged_in_user_nav_section.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var adapter: ClubsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view: View = binding.root


        loadData()
        initViews()
        updateView()


        return view
    }

    private fun initViews(){

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

        adapter = ClubsAdapter(requireContext(), true)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.profileClubsList.layoutManager = layoutManager
        binding.profileClubsList.adapter = adapter
    }


    private fun updateView() {
        profileViewModel.viewModelScope.launch {
            profileViewModel.currentUserProfile.collectLatest { profile ->
                when (profileViewModel.currentUserProfile.value.status) {
                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        profileViewModel.viewModelScope.launch {
                            profileViewModel.profileClubs.collectLatest { profileClubs ->
                                adapter.submitData(profileClubs)

                            }
                        }



                        Glide.with(requireContext())
                            .load(baseImageUrl + profile.data?.urlLogo)
                            .optionalCircleCrop()
                            .into(binding.profilePhoto)

                        binding.profileName.text =
                            profile.data?.firstName?.plus(" ")?.plus(profile.data.lastName)
                        when (profile.data?.roleName) {

                            "ROLE_USER" -> also { binding.profileRole.text = Role.user().uaName }
                            "ROLE_ADMIN" -> also { binding.profileRole.text = Role.admin().uaName }
                            "ROLE_MANAGER" -> also {
                                binding.profileRole.text = Role.manager().uaName
                            }
                        }
                        binding.profilePhoneNumber.text = profile.data?.phone
                        binding.profileEmailAddress.text = profile.data?.email
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
    }
}