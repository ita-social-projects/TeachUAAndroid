package com.softserve.teachua.ui.club

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.data.dto.MessageDto
import com.softserve.teachua.databinding.FragmentClubBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.register_to_club.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClubFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null

    private val binding get() = _binding!!

    private val clubViewModel: ClubViewModel by viewModels()

    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentClubBinding.inflate(inflater, container, false)
        val root: View = binding.root


        updateView()
        getCurrentClubInfo()
        createApplyToClubDialog()
        loadUser()




        return root

    }

    private fun loadUser() {
        clubViewModel.loadUser()
    }

    private fun getCurrentClubInfo() {

        binding.clubNameText.text = arguments?.getString("clubName")
        binding.clubDescriptionText.text = arguments?.getString("clubDescription")
        Glide.with(requireContext())
            .load(baseImageUrl + arguments?.getString("clubPicture"))
            .optionalCenterCrop()
            .placeholder(com.denzcoskun.imageslider.R.drawable.placeholder)
            .into(binding.clubPicture)


    }


    private fun updateView() {

        clubViewModel.viewModelScope.launch {
            clubViewModel.user.collectLatest { user ->
                when (user.status) {

                    Resource.Status.SUCCESS -> {

                        showSuccess()
                        binding.registerToClub.setOnClickListener {
                            dialog.clubName.text = arguments?.getString("clubName")
                            dialog.contactsLink.text =
                                arguments?.getString("clubLink")
                            dialog.show()
                            dialog.applyToClub.setOnClickListener {

                                var message = dialog.enterMsg.text?.trim()
                                var messageDto = MessageDto(
                                    id = 0,
                                    clubId = arguments?.getInt("clubId")!!,
                                    text = message?.toString()!!, user.data?.id!!,
                                    recipientId = 1,
                                    isActive = true
                                )

                                clubViewModel.sendMessage(messageDto)
                                dialog.dismiss()
                            }
                        }
                    }
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()
                }
            }

        }

    }


    private fun createApplyToClubDialog() {
        dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        dialog.setContentView(R.layout.register_to_club)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

    }


    override fun onStop() {
        super.onStop()
        binding.clubNameText.visibility = View.GONE
        binding.clubDescriptionText.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModelStore.clear()
    }

    private fun showSuccess() {
        binding.registerToClub.visibility = View.VISIBLE
        binding.contentClub.visibility = View.VISIBLE
        binding.progressBarClub.visibility = View.GONE
        binding.connectionProblemClub.visibility = View.GONE
    }

    private fun showLoading() {
        binding.registerToClub.visibility = View.GONE
        binding.contentClub.visibility = View.GONE
        binding.progressBarClub.visibility = View.VISIBLE
        binding.connectionProblemClub.visibility = View.GONE
    }

    private fun showError() {
        binding.registerToClub.visibility = View.GONE
        binding.contentClub.visibility = View.GONE
        binding.progressBarClub.visibility = View.GONE
        binding.connectionProblemClub.visibility = View.VISIBLE
    }


}