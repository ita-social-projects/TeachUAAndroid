package com.softserve.teachua.ui.club

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.databinding.FragmentClubBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClubFragment : Fragment() {

    private var _binding: FragmentClubBinding? = null

    private val binding get() = _binding!!

    private val clubViewModel: ClubViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentClubBinding.inflate(inflater, container, false)
        val root: View = binding.root


        getCurrentClubInfo()



        return root

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


    override fun onResume() {
        super.onResume()

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


}