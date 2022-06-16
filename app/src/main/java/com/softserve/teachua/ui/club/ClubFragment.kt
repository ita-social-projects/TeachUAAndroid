package com.softserve.teachua.ui.club

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softserve.teachua.R
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.FeedbacksDto
import com.softserve.teachua.data.dto.MessageDto
import com.softserve.teachua.databinding.FragmentClubBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.post_feedback.*
import kotlinx.android.synthetic.main.register_to_club.*
import kotlinx.android.synthetic.main.register_to_club.clubName
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClubFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null

    private val binding get() = _binding!!

    private val clubViewModel: ClubViewModel by viewModels()

    private lateinit var applyToClubDialog: Dialog

    private lateinit var sendFeedbackDialog: Dialog

    private lateinit var feedbacksAdapter: FeedbacksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentClubBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initViews()
        createApplyToClubDialog()
        createSendFeedbackDialog()
        loadUser()
        loadFeedbacks()
        updateView()
        getCurrentClubInfo()



        return root

    }

    private fun loadUser() {
        clubViewModel.loadUser()
    }

    private fun loadFeedbacks() {
        clubViewModel.loadFeedbacks(arguments?.getInt("clubId")!!)
    }

    private fun initViews() {
        feedbacksAdapter = FeedbacksAdapter(requireContext())
        val feedbacksLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.feedbacksList.layoutManager = feedbacksLayoutManager
        binding.feedbacksList.adapter = feedbacksAdapter

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

    fun EditText.setupCheck() {

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                sendFeedbackDialog.sendFeedback.isEnabled = editable?.isNotEmpty() == true
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                Unit
        })

    }


    private fun updateView() {

        clubViewModel.viewModelScope.launch {
            clubViewModel.user.collectLatest { user ->
                when (user.status) {

                    Resource.Status.SUCCESS -> {

                        showSuccess()
                        updateFeedBacks()
                        binding.registerToClub.setOnClickListener {
                            applyToClubDialog.clubName.text = arguments?.getString("clubName")
                            applyToClubDialog.contactsLink.text =
                                arguments?.getString("clubLink")
                            applyToClubDialog.show()
                            applyToClubDialog.applyToClub.setOnClickListener {

                                var message = applyToClubDialog.enterMsg.text?.trim()
                                var messageDto = MessageDto(
                                    id = 0,
                                    clubId = arguments?.getInt("clubId")!!,
                                    text = message?.toString()!!, user.data?.id!!,
                                    recipientId = 1,
                                    isActive = true
                                )

                                clubViewModel.sendMessage(messageDto)
                                when( clubViewModel.sendMessage(messageDto).isCompleted){
                                   true -> {
                                       applyToClubDialog.dismiss()
                                       loadFeedbacks()
                                   }
                                }



                            }
                        }

                        sendFeedbackDialog.enterDescription.setupCheck()

                        sendFeedbackDialog.clubName.text =
                            arguments?.getString("clubName")
                        sendFeedbackDialog.enterName.setText(user.data?.firstName.plus(" ")
                            .plus(user.data?.lastName), TextView.BufferType.EDITABLE)
                        sendFeedbackDialog.enterPhone.setText(user.data?.phone,
                            TextView.BufferType.EDITABLE)
                        sendFeedbackDialog.enterEmail.setText(user.data?.email,
                            TextView.BufferType.EDITABLE)

                        binding.postFeedback.setOnClickListener {
                            sendFeedbackDialog.show()
                            sendFeedbackDialog.sendFeedback.setOnClickListener {
                                var feedback = FeedbacksDto(
                                    id = 0,
                                    rate = sendFeedbackDialog.clubRating.rating,
                                    text = sendFeedbackDialog.enterDescription.text.toString(),
                                    userId = user.data?.id!!,
                                    clubId = arguments?.getInt("clubId")!!
                                )

                                if (sendFeedbackDialog.clubRating.rating == 0f) {
                                    Toast.makeText(requireContext(),
                                        "Please enter rating",
                                        Toast.LENGTH_SHORT).show()
                                } else {
                                    clubViewModel.postFeedback(feedback)
                                    sendFeedbackDialog.dismiss()
                                    feedbacksAdapter.notifyDataSetChanged()

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

    private fun updateFeedBacks() {

        clubViewModel.viewModelScope.launch {
            clubViewModel.feedbacksList.collectLatest { feedbacks ->
                when (feedbacks.status) {

                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        feedbacksAdapter.submitList(feedbacks.data)
                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> println("Feedbacks failed")
                }

            }
        }
    }


    private fun createApplyToClubDialog() {
        applyToClubDialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        applyToClubDialog.setContentView(R.layout.register_to_club)
        applyToClubDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

    }

    private fun createSendFeedbackDialog() {
        sendFeedbackDialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        sendFeedbackDialog.setContentView(R.layout.post_feedback)
        sendFeedbackDialog.window?.setLayout(
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