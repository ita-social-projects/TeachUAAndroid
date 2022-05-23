package com.softserve.teachua.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.softserve.teachua.R
import com.softserve.teachua.app.tools.validators.isValidEmail
import com.softserve.teachua.app.tools.validators.isValidPassword
import com.softserve.teachua.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var enterPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view: View = binding.root
        initViewsAndButtons()

        return view
    }

    private fun initViewsAndButtons() {
        enterPassword = binding.enterPassword
        binding.signUpBtn.setOnClickListener(this)
    }

    private fun inputError(string: String){
        view?.let {
            Snackbar.make(it, string, 1000).apply {
                show()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.signUpBtn -> {
                val validPassword = enterPassword.text.toString().trim().isValidPassword()
                if (!validPassword){
                    inputError(validPassword.getErrors().toString())
                    return
                }
                val validEmail = enterEmail.text.toString().isValidEmail()
                if(!validEmail){
                    inputError(validEmail.getErrors().toString())
                    return
                }
            }
        }
    }


}