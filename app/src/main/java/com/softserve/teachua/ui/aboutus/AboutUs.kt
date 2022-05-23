package com.softserve.teachua.ui.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.softserve.teachua.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUs : Fragment() {

    private val viewModel : AboutUsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_us_fragment, container, false)
    }


}