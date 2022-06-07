package com.softserve.teachua.ui.editProfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.enums.Role
import com.softserve.teachua.data.dto.UpdateUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.databinding.FragmentEditProfileBinding
import com.softserve.teachua.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.logged_in_user_nav_section.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null


    private val binding get() = _binding!!
    private val editProfileViewModelViewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view: View = binding.root

        loadDataFromLocal()
        updateView()
        initView()
        return view
    }

    private fun loadDataFromLocal() {
        editProfileViewModelViewModel.loadUserFromLocal()
    }

    private fun updateData(updateUserDto: UpdateUserDto) {
        editProfileViewModelViewModel.updateUser(updateUserDto)
    }


    private fun initView() {
        binding.saveChanges.setOnClickListener {
            var email = binding.enterEmail.text?.trim()
            var firstName = binding.enterName.text?.trim()
            var lastName = binding.enterSurname.text?.trim()
            var phone = binding.enterPhone.text?.trim()
            var updateUserDto =
                UpdateUserDto(id = editProfileViewModelViewModel.currentUserProfile.value.data?.id!!,
                    firstName = firstName.toString(),
                    lastName = lastName.toString(),
                    email = email.toString(),
                    phone = phone.toString(),
                    urlLogo = "/upload/user/logo/782/yellow-lamborghini-urus-suv.jpeg",
                    status = true,
                    roleName = when (binding.rolesBtn.checkedRadioButtonId) {
                        binding.userBtn.id -> Role.RoleStatus.ROLE_USER.toString()
                        binding.managerBtn.id -> Role.RoleStatus.ROLE_MANAGER.toString()
                        else -> {
                            Role.RoleStatus.ROLE_ADMIN.toString()
                        }
                    }
                )

            updateData(updateUserDto)
            updatedUser()


        }
    }


    private fun updateView() {
        editProfileViewModelViewModel.viewModelScope.launch {
            editProfileViewModelViewModel.currentUserProfile.collectLatest { profile ->
                when (editProfileViewModelViewModel.currentUserProfile.value.status) {
                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        binding.enterSurname.setText(profile.data?.lastName,
                            TextView.BufferType.EDITABLE)
                        binding.enterName.setText(profile.data?.firstName,
                            TextView.BufferType.EDITABLE)
                        binding.enterPhone.setText(profile.data?.phone,
                            TextView.BufferType.EDITABLE)
                        binding.enterEmail.setText(profile.data?.email,
                            TextView.BufferType.EDITABLE)

                        when (profile.data?.roleName) {

                            "ROLE_USER" -> binding.userBtn.isChecked = true
                            "ROLE_MANAGER" -> binding.managerBtn.isChecked = true
                        }


                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }
            }
        }


    }

    private fun updatedUser() {

        editProfileViewModelViewModel.viewModelScope.launch {
            editProfileViewModelViewModel.updatedUserProfile.collectLatest { profile ->
                when (editProfileViewModelViewModel.updatedUserProfile.value.status) {
                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        updateUserInLocal()
                        findNavController().popBackStack()
                        activity?.userName?.text =
                            profile.data?.firstName.plus(" ").plus(profile.data?.lastName)
                        activity?.userRole?.text = Role().getUaRoleName(profile.data?.roleName!!)
                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }
            }
        }
    }

    private fun updateUserInLocal() {
        editProfileViewModelViewModel.updateUserInLocal()
    }


    private fun showSuccess() {
        binding.contentEditProfile.visibility = View.VISIBLE
        binding.progressBarEditProfile.visibility = View.GONE
        binding.connectionProblemEditProfile.visibility = View.GONE
    }

    private fun showLoading() {
        binding.contentEditProfile.visibility = View.GONE
        binding.progressBarEditProfile.visibility = View.VISIBLE
        binding.connectionProblemEditProfile.visibility = View.GONE
    }

    private fun showError() {
        binding.contentEditProfile.visibility = View.GONE
        binding.progressBarEditProfile.visibility = View.GONE
        binding.connectionProblemEditProfile.visibility = View.VISIBLE
    }
}